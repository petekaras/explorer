package com.pete.pom;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;

import com.pete.crawler.MavenDetail;

import dao.POMDAO;

public class ProjectTest {
  private static final String ARTIFACT_ID = "struts.io";
  private static final String GROUP_ID = "struts.io";
  private static final String VERSION = "1.4.5";
  Project project;
  @Before
  public void setUp() throws Exception {
    project = new Project();
  }
  @Test
  public void shouldBeResolved() {
    project.setArtifactId(ARTIFACT_ID);
    project.setGroupId(GROUP_ID);
    project.setVersion(VERSION);
    assertEquals(true, project.isRootInformationResolved());
    
  }
  
  @Test
  public void shouldNotBeResolved() {
    project.setArtifactId(null);
    project.setGroupId(GROUP_ID);
    project.setVersion(VERSION);
    assertEquals(false, project.isRootInformationResolved());
    
  }
  @Test
  public void shouldNotBeResolvedWithVariableName() {
    project.setArtifactId(ARTIFACT_ID);
    project.setGroupId(GROUP_ID);
    project.setVersion("${some_variable}");
    assertEquals(false, project.isRootInformationResolved());
    
  }
  @Test
  public void shouldNotBeResolvedWithEmptyString() {
    project.setArtifactId(ARTIFACT_ID);
    project.setGroupId("");
    project.setVersion(VERSION);
    assertEquals(false, project.isRootInformationResolved());
    
  }

}
