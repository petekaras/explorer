package com.pete.crawler;

import java.util.List;

import com.pete.pom.ProjectSummary;

public interface ProjectList {
  /**
   * Find latest POM versions by artifactID
   * @param artifactId 
   * @return list of POMs without dependencies loaded
   */
  public abstract List<ProjectSummary> getArtifactsById(String artifactId);
}
