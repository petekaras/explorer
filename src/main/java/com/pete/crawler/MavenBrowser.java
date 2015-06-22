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
import com.pete.pom.Project;
import com.pete.pom.POMResponse;
import com.pete.pom.ProjectSummary;

import dao.POMDAO;

public class MavenBrowser implements ProjectBrowser {
  // TODO: externalise into a properties file.
  private static final String MAVEN_ROOT = "https://repo1.maven.org/maven2";
  private static final String ARTIFACT_ID_PLACEHOLDER = "<artifactId>";
  private static final int FIND_COUNT = 20;
  private static final String MAVEN_API_ROOT = "http://search.maven.org/solrsearch/select?q=a:" + ARTIFACT_ID_PLACEHOLDER + "&rows=" + FIND_COUNT
      + "&wt=json";
  private static final Logger LOGGER = Logger.getLogger(MavenBrowser.class.getName());
  private POMDAO pomdao;

  @Inject
  public MavenBrowser(POMDAO pomDao) {
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
 * @param rootProject
 * @param parentArtifactId
 * @return
 */
  protected Project initializeProject(final Project rootProject, final String parentArtifactId) {

    StringBuilder urlStrBuilder = new StringBuilder(MAVEN_ROOT);
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
      LOGGER.log(Level.WARNING, "Error getting data for : " + rootProject.getGroupId() + ":" + rootProject.getArtifactId() + ":" + rootProject.getVersion()
          + "  " + ioex.getMessage());
      return null;
    }
    if (rootProjectDataFromMaven == null) {
      LOGGER.log(Level.WARNING, "No data found for: " + rootProject.getGroupId() + ":" + rootProject.getArtifactId() + ":" + rootProject.getVersion());
      return null;
    }
    /*
     * Parse XML data
     */
    POMParser pomParser;
    try {
      pomParser = new POMParser(rootProjectDataFromMaven);
    } catch (ParserConfigurationException | SAXException | IOException e) {
      LOGGER.log(Level.WARNING, "Error parsing data: " + rootProject.getGroupId() + ":" + rootProject.getArtifactId() + ":" + rootProject.getVersion() + " : "
          + e.getMessage());
      return null;
    }

    /*
     * Recursively call this method to resolve all dependencies
     */
    List<Project> projects = pomParser.getPomObject().getDependencies();

    for (Project project : projects) {
      rootProject.addDependency(project);
      if (project != null && !project.getArtifactId().equals(parentArtifactId)) {
        if(project.isRootInformationResolved()){
          initializeProject(project, parentArtifactId);
        }
      }
    }

    return rootProject;
  }

  @Override
  public List<ProjectSummary> getArtifactsById(String artifactId) {
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