package com.pete.endpoint;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Named;

import com.google.api.server.spi.config.Api;
import com.google.inject.Inject;
import com.pete.crawler.Crawler;
import com.pete.crawler.MavenCrawler;
import com.pete.pom.POM;
import com.pete.pom.POMSummary;


@Api(name = "maven", version = "v1", scopes = { Constants.EMAIL_SCOPE }, clientIds = { Constants.WEB_CLIENT_ID, Constants.ANDROID_CLIENT_ID,
    Constants.IOS_CLIENT_ID, Constants.API_EXPLORER_CLIENT_ID }, audiences = { Constants.ANDROID_AUDIENCE }, authenticators = { })

public class Maven {
  Crawler MavenCrawler;
  private static final Logger LOGGER = Logger.getLogger(Maven.class.getName());
  @Inject
  Maven(Crawler pomCrawler) {
    MavenCrawler = pomCrawler;
  }
  
  
  public POM getDependencyTree(@Named("groupId")String groupID,@Named("artifactId")String artifactId,@Named("version")String version) throws Exception{
      return MavenCrawler.getPOMWithAllDependencies(groupID,artifactId,version, "-", null);
  }
  
  public List<POMSummary> getLatestArtifacts(@Named("artifactId")String artifactId){
    return MavenCrawler.getPOMsByArtifactId(artifactId);
  }
}
