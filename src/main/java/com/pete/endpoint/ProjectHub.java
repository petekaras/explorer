package com.pete.endpoint;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Named;

import com.google.api.server.spi.config.Api;
import com.google.inject.Inject;
import com.pete.crawler.ProjectBrowser;
import com.pete.crawler.MavenBrowser;
import com.pete.pom.Project;
import com.pete.pom.ProjectSummary;


@Api(name = "projectHub", version = "v1", scopes = { Constants.EMAIL_SCOPE }, clientIds = { Constants.WEB_CLIENT_ID, Constants.ANDROID_CLIENT_ID,
    Constants.IOS_CLIENT_ID, Constants.API_EXPLORER_CLIENT_ID }, audiences = { Constants.ANDROID_AUDIENCE }, authenticators = { })

public class ProjectHub {
  ProjectBrowser MavenCrawler;
  private static final Logger LOGGER = Logger.getLogger(ProjectHub.class.getName());
  @Inject
  ProjectHub(ProjectBrowser pomCrawler) {
    MavenCrawler = pomCrawler;
  }
  
  
  public Project getDependencyTree(@Named("groupId")String groupID,@Named("artifactId")String artifactId,@Named("version")String version) throws Exception{
    //TODO: validation 
    
    //TODO: as part of refactoring use a builder pattern.
    //MavenCrawler.withGroupId()..
    //MavenCrawler.withVersion()...
    //Only artifact ID / group ID is manadatory, otherwise get latest version
    //MavenCrawler.withCleanVariables().getDependencyTree();
    String cleanArtifactId = MavenHelper.cleanSearchVariable(artifactId);
    return MavenCrawler.getDependencyTree(groupID,cleanArtifactId,version, null);
  }
  
  public List<ProjectSummary> getLatestArtifacts(@Named("artifactId")String artifactId){
    //TODO: validation
    String cleanArtifactId = MavenHelper.cleanSearchVariable(artifactId);
    return MavenCrawler.getArtifactsById(cleanArtifactId);
  }
}
