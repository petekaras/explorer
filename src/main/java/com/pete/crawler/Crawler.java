package com.pete.crawler;

import com.pete.pom.POM;

public interface Crawler {

  public abstract POM getDependenciesFromPOM(String groupID, String artifactId, String version, String indent, String parentArtifactId)
      throws Exception;

  public abstract POM getDependenciesFromPOM(POM rootPOM, String indent, String parentArtifactId) throws Exception;

}