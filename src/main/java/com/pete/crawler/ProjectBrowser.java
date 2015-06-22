package com.pete.crawler;

import java.util.List;

import com.pete.pom.Project;
import com.pete.pom.ProjectSummary;

public interface ProjectBrowser {
  /**
   * Get POM data with all data dependencies.
   * Missing POMS will be ignored.
   * This method crawls the dependncy tree till all dependencies have been loaded. 
   * @param groupID
   * @param artifactId
   * @param version
   * @param parentArtifactId
   * @return the POM, or null if the POM data is missing
   * @throws Exception
   */
  public abstract Project getDependencyTree(String groupID, String artifactId, String version, String parentArtifactId);




}