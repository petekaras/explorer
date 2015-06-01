package com.pete.crawler;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.google.inject.Inject;
import com.pete.POMParser;
import com.pete.pom.POM;

import dao.POMDAO;

public class POMCrawler implements Crawler {
  // TODO: externalise into a properties file.
  private static final String MAVEN_ROOT = "https://repo1.maven.org/maven2";
  private static final Logger LOGGER = Logger.getLogger(POMCrawler.class.getName());
  private POMDAO pomdao;

  @Inject
  public POMCrawler(POMDAO pomDao) {
    this.pomdao = pomDao;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.pete.crawler.Crawler#getDependenciesFromPOM(java.lang.String,
   * java.lang.String, java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public POM getPOMWithAllDependencies(final String groupID, final String artifactId, final String version, final String indent,
      final String parentArtifactId) {
    POM rootPOM = new POM();
    rootPOM.setArtifactId(artifactId);
    rootPOM.setGroupId(groupID);
    rootPOM.setVersion(version);
    return getDependenciesFromPOM(rootPOM, indent, parentArtifactId);

  }

  protected POM getDependenciesFromPOM(final POM rootPOM, final String indent, final String parentArtifactId) {

    StringBuilder urlStrBuilder = new StringBuilder(MAVEN_ROOT);
    urlStrBuilder.append("/" + rootPOM.getGroupId().replace(".", "/"));
    urlStrBuilder.append("/" + rootPOM.getArtifactId());
    urlStrBuilder.append("/" + rootPOM.getVersion());
    urlStrBuilder.append("/" + rootPOM.getArtifactId() + "-" + rootPOM.getVersion() + ".pom");
    LOGGER.log(Level.INFO, "GETTING: " + urlStrBuilder.toString());
    /*
     * Get XML data from Maven repo
     */
    String dataFromMaven = null;
    try {
      dataFromMaven = (pomdao.getData(urlStrBuilder.toString()));
    } catch (IOException ioex) {
      LOGGER.log(Level.WARNING, "Error getting data for : " + rootPOM.getGroupId() + ":" + rootPOM.getArtifactId() + ":" + rootPOM.getVersion() + "  " + ioex.getMessage());
      return null;
    }
    if (dataFromMaven == null) {
      LOGGER.log(Level.WARNING, "No data found for: " + rootPOM.getGroupId() + ":" + rootPOM.getArtifactId() + ":" + rootPOM.getVersion());
      return null;
    }
    /*
     * Parse XML data
     */
    POMParser pp;
    try {
      pp = new POMParser(dataFromMaven);
    } catch (ParserConfigurationException | SAXException | IOException e) {
      LOGGER.log(Level.WARNING, "Error parsing data: " + rootPOM.getGroupId() + ":" + rootPOM.getArtifactId() + ":" + rootPOM.getVersion() + " : "
          + e.getMessage());
      return null;
    }

    /*
     * Recursively call this method to resolve all dependencies
     */
    List<POM> poms = pp.getPomObject().getDependencies();

    for (POM pom : poms) {
      rootPOM.addDependency(pom);
      if (pom != null && !rootPOM.getVersion().contains("$") && !pom.getArtifactId().equals(parentArtifactId)) {
        getDependenciesFromPOM(pom, indent + "-", parentArtifactId);
      }
    }

    return rootPOM;
  }

}