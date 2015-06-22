package com.pete.crawler;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.codehaus.jackson.map.ObjectMapper;

import com.google.inject.Inject;
import com.pete.pom.POMResponse;
import com.pete.pom.ProjectSummary;

import dao.POMDAO;

/**
 * Lists artifacts in Maven
 * @author peter
 *
 */
public class MavenList implements ProjectList {
  private static final Logger LOGGER = Logger.getLogger(MavenList.class.getName());
  private POMDAO pomdao;
  private String artifactId;

  @Inject
  public MavenList(POMDAO pomDao) {
    this.pomdao = pomDao;
  }

  @Override
  public List<ProjectSummary> getArtifactsById(String artifactId) {

    String mavenList;
    try {
      String searchURL = MavenHelper.MAVEN_API_ROOT.replace(MavenHelper.ARTIFACT_ID_PLACEHOLDER, artifactId);
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
