package com.pete.crawler;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.NoRouteToHostException;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Inject;
import com.pete.POMParser;
import com.pete.pom.POM;

import dao.POMDAO;

public class POMCrawler implements Crawler {

  private static final String MAVEN_ROOT = "https://repo1.maven.org/maven2";
  private static final Logger LOGGER = Logger.getLogger(POMCrawler.class.getName());
  private POMDAO pomdao;
  
  @Inject
  public POMCrawler(POMDAO pomDao){
    this.pomdao = pomDao;
  }
  
  
  /* (non-Javadoc)
   * @see com.pete.crawler.Crawler#getDependenciesFromPOM(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public POM getDependenciesFromPOM(final String groupID,final String artifactId,final String version, final String indent, final String parentArtifactId) throws Exception {
    POM rootPOM = new POM();
    rootPOM.setArtifactId(artifactId);
    rootPOM.setGroupId(groupID);
    rootPOM.setVersion(version);
    return getDependenciesFromPOM(rootPOM, indent, parentArtifactId);
    
  }
  /* (non-Javadoc)
   * @see com.pete.crawler.Crawler#getDependenciesFromPOM(com.pete.pom.POM, java.lang.String, java.lang.String)
   */
  @Override
  public POM getDependenciesFromPOM(final POM rootPOM, final String indent, final String parentArtifactId) throws Exception {

    StringBuilder urlStrBuilder = new StringBuilder(MAVEN_ROOT);
    urlStrBuilder.append("/" + rootPOM.getGroupId().replace(".", "/"));
    urlStrBuilder.append("/" + rootPOM.getArtifactId());
    urlStrBuilder.append("/" + rootPOM.getVersion());
    urlStrBuilder.append("/" + rootPOM.getArtifactId() + "-" + rootPOM.getVersion() + ".pom");

    String dataFromMaven = null;
    LOGGER.log(Level.INFO, "GETTING: " + urlStrBuilder.toString());
    try {
      dataFromMaven = (pomdao.getData(urlStrBuilder.toString()));
    } catch (FileNotFoundException e) {
      return null;

    } catch (Exception e) {
      throw e;
    }
    
    if(dataFromMaven==null){
      return null;
    }
    POMParser pp = new POMParser(dataFromMaven);

    List<POM> poms = pp.getPomObject().getDependencies();

    for (POM pom : poms) {
      System.out.println(indent + " " + pom.getArtifactId() + ":" + pom.getVersion());

      rootPOM.addDependency(pom);
      if (pom != null && !rootPOM.getVersion().contains("$") && !pom.getArtifactId().equals(parentArtifactId)) {
        getDependenciesFromPOM(pom, indent + "-", parentArtifactId);
      }
    }

    //Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    //return gson.toJson(rootPOM);
    return rootPOM;
  }




}