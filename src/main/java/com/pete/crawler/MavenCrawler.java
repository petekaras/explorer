package com.pete.crawler;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;

import org.codehaus.jackson.map.ObjectMapper;
import org.xml.sax.SAXException;

import com.google.inject.Inject;
import com.pete.POMParser;
import com.pete.pom.POM;
import com.pete.pom.POMResponse;
import com.pete.pom.POMSummary;

import dao.POMDAO;

public class MavenCrawler implements Crawler {
  // TODO: externalise into a properties file.
  private static final String MAVEN_ROOT = "https://repo1.maven.org/maven2";
  private static final String ARTIFACT_ID_PLACEHOLDER = "<artifactId>";
  private static final int FIND_COUNT = 20;
  private static final String MAVEN_API_ROOT = "http://search.maven.org/solrsearch/select?q=a:" + ARTIFACT_ID_PLACEHOLDER + "&rows=" + FIND_COUNT
      + "&wt=json";
  private static final Logger LOGGER = Logger.getLogger(MavenCrawler.class.getName());
  private POMDAO pomdao;

  @Inject
  public MavenCrawler(POMDAO pomDao) {
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
      LOGGER.log(Level.WARNING, "Error getting data for : " + rootPOM.getGroupId() + ":" + rootPOM.getArtifactId() + ":" + rootPOM.getVersion()
          + "  " + ioex.getMessage());
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

  @Override
  public List<POMSummary> getPOMsByArtifactId(String artifactId) {
    String mavenList;
    try {
      String searchURL = MAVEN_API_ROOT.replace(ARTIFACT_ID_PLACEHOLDER, artifactId);
      mavenList = pomdao.getData(searchURL);
    } catch (IOException ioex) {
      LOGGER.log(Level.WARNING, "Error getting data for : " + artifactId + " : " + ioex.getMessage());
      return null;
    }
    
    ObjectMapper objectMapper = new ObjectMapper();
    POMResponse response = null;
    try {
      response = objectMapper.readValue(mavenList, POMResponse.class);
    } catch (IOException e) {
      LOGGER.log(Level.WARNING, "Error reading JSON data for : " + artifactId + " : " + e.getMessage());
      return null;
    }

    return response.listOfPoms;
  }



}