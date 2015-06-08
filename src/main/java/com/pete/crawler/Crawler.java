package com.pete.crawler;

import java.util.List;

import com.pete.pom.POM;
import com.pete.pom.POMSummary;

public interface Crawler {
  /**
   * Get POM data with all data dependencies.
   * Missing POMS will be ignored.
   * This method crawls the dependncy tree till all dependencies have been loaded. 
   * @param groupID
   * @param artifactId
   * @param version
   * @param indent
   * @param parentArtifactId
   * @return the POM, or null if the POM data is missing
   * @throws Exception
   */
  public abstract POM getPOMWithAllDependencies(String groupID, String artifactId, String version, String indent, String parentArtifactId);

  /**
   * Find latest POM versions by artifactID
   * @param artifactId 
   * @return list of POMs without dependencies loaded
   */
  public abstract List<POMSummary> getPOMsByArtifactId(String artifactId);


}