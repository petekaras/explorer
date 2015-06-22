package com.pete.endpoint;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Named;
import javax.ws.rs.BadRequestException;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.response.ConflictException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.inject.Inject;
import com.pete.crawler.MavenHelper;
import com.pete.crawler.MavenList;
import com.pete.crawler.ProjectBrowser;
import com.pete.crawler.MavenDetail;
import com.pete.crawler.ProjectList;
import com.pete.pom.Project;
import com.pete.pom.ProjectSummary;


@Api(name = "dependency", version = "v1", scopes = { Constants.EMAIL_SCOPE }, clientIds = { Constants.WEB_CLIENT_ID, Constants.ANDROID_CLIENT_ID,
    Constants.IOS_CLIENT_ID, Constants.API_EXPLORER_CLIENT_ID }, audiences = { Constants.ANDROID_AUDIENCE }, authenticators = { })

public class ProjectHub {
  ProjectBrowser mavenCrawler;
  ProjectList mavenList;
  private static final Logger LOGGER = Logger.getLogger(ProjectHub.class.getName());
  @Inject
  ProjectHub(ProjectBrowser pomCrawler,ProjectList projectList) {
    mavenCrawler = pomCrawler;
    mavenList = projectList;
  }
  
  
  public Project artifact(@Named("groupId")String groupId,@Named("artifactId")String artifactId,@Named("version")String version) throws NotFoundException{
      
      
      String cleanArtifactId = MavenHelper.cleanSearchVariable(artifactId);
      Project project = mavenCrawler.getDependencyTree(groupId,cleanArtifactId,version, null);
      if(project==null){
        throw new NotFoundException(artifactId);
      }
      return mavenCrawler.getDependencyTree(groupId,cleanArtifactId,version, null);


  }
  
  public List<ProjectSummary> artifacts(@Named("artifactId")String artifactId) throws  NotFoundException{
   
    
    String cleanArtifactId = MavenHelper.cleanSearchVariable(artifactId);
    
    List<ProjectSummary> artifacts = mavenList.getArtifactsById(cleanArtifactId);
    if(artifacts.isEmpty()){
      throw new NotFoundException(artifactId);
    }
    return artifacts;

  }
}
