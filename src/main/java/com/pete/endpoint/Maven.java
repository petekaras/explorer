package com.pete.endpoint;
import javax.inject.Named;

import com.google.api.server.spi.config.Api;
import com.google.inject.Inject;
import com.pete.crawler.Crawler;
import com.pete.pom.POM;


@Api(name = "maven", version = "v1", scopes = { Constants.EMAIL_SCOPE }, clientIds = { Constants.WEB_CLIENT_ID, Constants.ANDROID_CLIENT_ID,
    Constants.IOS_CLIENT_ID, Constants.API_EXPLORER_CLIENT_ID }, audiences = { Constants.ANDROID_AUDIENCE }, authenticators = { })

public class Maven {
  Crawler POMCrawler;
  @Inject
  Maven(Crawler pomCrawler) {
    POMCrawler = pomCrawler;
  }
  
  
  public POM getDependencyTree(@Named("groupID")String groupID,@Named("artifactId")String artifactId,@Named("version")String version) throws Exception{
    return POMCrawler.getDependenciesFromPOM(groupID,artifactId,version, "-", null);    
  }
}
