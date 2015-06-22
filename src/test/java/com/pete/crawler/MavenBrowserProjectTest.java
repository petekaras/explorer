package com.pete.crawler;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.internal.runners.statements.Fail;

import com.pete.POMTests;
import com.pete.crawler.ProjectBrowser;
import com.pete.crawler.MavenDetail;
import com.pete.pom.Project;
import com.pete.testtools.TestHelper;

import dao.POMDAO;

public class MavenBrowserProjectTest extends POMTests {
  private MavenDetail mavenBrowser;
  private POMDAO mockPomdao;
  String indent;
  String parentArtifactId;
  Project rootPOM;

  @Before
  public void setUp() throws Exception {
    mockPomdao = mock(POMDAO.class);
    mavenBrowser = new MavenDetail(mockPomdao);
    rootPOM = new Project();
    rootPOM.setArtifactId("pete");
    rootPOM.setGroupId("test");
    rootPOM.setVersion("1.0");
    parentArtifactId = null;

  }

  @Test
  public void shouldGetRootInfoFromProject() throws Exception {
    String file = TestHelper.getFileAsString(XML_FILE_SIMPLE);
    when(mockPomdao.getData(anyString())).thenReturn(file);
    Project data = mavenBrowser.initializeProject(rootPOM,  parentArtifactId);
    assertEquals("pete", data.getArtifactId());
  }
  
  @Test
  public void shouldGetRootInfoFromProjectOverloaded() throws Exception {
    String file = TestHelper.getFileAsString(XML_FILE_SIMPLE);
    when(mockPomdao.getData(anyString())).thenReturn(file);
    Project data = mavenBrowser.getDependencyTree(rootPOM.getGroupId(),rootPOM.getArtifactId(),rootPOM.getVersion(), parentArtifactId);
    assertEquals("pete", data.getArtifactId());
  }  
  
  @Test
  public void shouldGetProjectWithOneUnresolvableDepenency() throws Exception {
    String thisFile = TestHelper.getFileAsString(XML_FILE_WITH_ONE_UNRESOLVABLE_PROJECT_VARIABLE);
    when(mockPomdao.getData(anyString())).thenReturn(thisFile );        
    Project data = mavenBrowser.getDependencyTree(rootPOM.getGroupId(),rootPOM.getArtifactId(),rootPOM.getVersion(), parentArtifactId);
    boolean dependencyProjectInfoResolved = data.getDependencies().get(0).isRootInformationResolved();
    
    assertEquals(false, dependencyProjectInfoResolved);
  } 
  @Test
  public void shouldGetProjectWithResolvedRootInfo() throws Exception {
    String thisFile = TestHelper.getFileAsString(XML_FILE_WITH_ONE_UNRESOLVABLE_VARIABLE);
    when(mockPomdao.getData(anyString())).thenReturn(thisFile );        
    Project data = mavenBrowser.getDependencyTree(rootPOM.getGroupId(),rootPOM.getArtifactId(),rootPOM.getVersion(), parentArtifactId);
    assertEquals(true, data.isRootInformationResolved());
  } 
  @Test
  public void shouldGetProjectWithUnResolvedRootInfo() throws Exception {
    String thisFile = TestHelper.getFileAsString(XML_FILE_WITH_ONE_UNRESOLVABLE_PROJECT_VARIABLE);
    rootPOM.setVersion("${variable}");
    when(mockPomdao.getData(anyString())).thenReturn(thisFile );        
    Project data = mavenBrowser.getDependencyTree(rootPOM.getGroupId(),rootPOM.getArtifactId(),rootPOM.getVersion(), parentArtifactId);
    assertEquals(false, data.isRootInformationResolved());
  }   
  


}
