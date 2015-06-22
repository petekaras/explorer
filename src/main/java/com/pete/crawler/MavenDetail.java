package com.pete.crawler;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;

import org.codehaus.jackson.map.ObjectMapper;
import org.xml.sax.SAXException;

import com.google.inject.Inject;
import com.pete.ProjectParser;
import com.pete.pom.Project;
import com.pete.pom.POMResponse;
import com.pete.pom.ProjectSummary;

import dao.POMDAO;

public class MavenDetail implements ProjectBrowser {





  private static final Logger LOGGER = Logger.getLogger(MavenDetail.class.getName());
  private POMDAO pomdao;


  @Inject
  public MavenDetail(POMDAO pomDao) {
    this.pomdao = pomDao;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.pete.crawler.Crawler#getDependenciesFromPOM(java.lang.String,
   * java.lang.String, java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public Project getDependencyTree(final String groupID, final String artifactId, final String version, final String parentArtifactId) {
    Project rootProject = new Project();
    rootProject.setArtifactId(artifactId);
    rootProject.setGroupId(groupID);
    rootProject.setVersion(version);
    return initializeProject(rootProject, parentArtifactId);

  }

  /**
   * Loads project data from the data source including all dependencies.
   * Recursively call on all dependencies.
   * @param rootProject
   * @param parentArtifactId
   * @return
   */
  protected Project initializeProject(final Project rootProject, final String parentArtifactId) {

    StringBuilder urlStrBuilder = new StringBuilder(MavenHelper.MAVEN_ROOT);
    urlStrBuilder.append("/" + rootProject.getGroupId().replace(".", "/"));
    urlStrBuilder.append("/" + rootProject.getArtifactId());
    urlStrBuilder.append("/" + rootProject.getVersion());
    urlStrBuilder.append("/" + rootProject.getArtifactId() + "-" + rootProject.getVersion() + ".pom");
    LOGGER.log(Level.INFO, "GETTING: " + urlStrBuilder.toString());
    /*
     * Get XML data from Maven repo
     */
    String rootProjectDataFromMaven = null;
    try {
      rootProjectDataFromMaven = (pomdao.getData(urlStrBuilder.toString()));
    } catch (IOException ioex) {
      LOGGER.log(
          Level.WARNING,
          "Error getting data for : " + rootProject.getGroupId() + ":" + rootProject.getArtifactId() + ":" + rootProject.getVersion() + "  "
              + ioex.getMessage());
      return null;
    }
    if (rootProjectDataFromMaven == null) {
      LOGGER
          .log(Level.WARNING, "No data found for: " + rootProject.getGroupId() + ":" + rootProject.getArtifactId() + ":" + rootProject.getVersion());
      return null;
    }
    /*
     * Parse XML data
     */
    ProjectParser projectParser;
    
    try {
      projectParser = new ProjectParser(rootProjectDataFromMaven);
    } catch (ParserConfigurationException | SAXException | IOException e) {
      LOGGER.log(
          Level.WARNING,
          "Error parsing data: " + rootProject.getGroupId() + ":" + rootProject.getArtifactId() + ":" + rootProject.getVersion() + " : "
              + e.getMessage());
      return null;
    }

    /*
     * Recursively call this method to resolve all dependencies
     */
    List<Project> projects = projectParser.getProject().getDependencies();

    for (Project project : projects) {
      rootProject.addDependency(project);
      if (project != null && !project.getArtifactId().equals(parentArtifactId)) {
        if (project.isRootInformationResolved()) {
          initializeProject(project, parentArtifactId);
        }
      }
    }

    return rootProject;
  }




}