package com.pete.crawler;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.pete.POMTests;
import com.pete.pom.Project;
import com.pete.pom.ProjectSummary;
import com.pete.testtools.TestHelper;

import dao.POMDAO;
import dao.POMDAOHTTP;

public class MavenCrawlerGetListTest extends POMTests{
  private MavenBrowser mavenCrawler;
  private POMDAO mockPomdao;
  
  
  @Before
  public void setUp() {
    mockPomdao = mock(POMDAO.class);
    mavenCrawler = new MavenBrowser(mockPomdao);

  }
  
  @Test
  public void shouldReturnFromArtifactIdSearch() throws Exception{
    setUpSearchResult(XML_RESULT_FILE_SERACH_FOR_GUICE);
    List<ProjectSummary> poms = mavenCrawler.getArtifactsById("anything-as-the-return-mocked");
    assertEquals(9,poms.size());

  }
  @Test
  public void shouldReturnVersionFromArtifactIdSearch() throws Exception{
    setUpSearchResult(XML_RESULT_FILE_SERACH_FOR_GUICE);
    List<ProjectSummary> poms = mavenCrawler.getArtifactsById("anything-as-the-return-mocked");
    assertEquals("0.0.9",poms.get(0).getVersion());
  }
  @Test
  public void shouldReturnGroupFromArtifactIdSearch() throws Exception{
    setUpSearchResult(XML_RESULT_FILE_SERACH_FOR_GUICE);
    List<ProjectSummary> poms = mavenCrawler.getArtifactsById("anything-as-the-return-mocked");
    assertEquals("io.prometheus.client.examples",poms.get(0).getGroupId());
  }  
  @Test
  public void shouldReturnArtifactFromArtifactIdSearch() throws Exception{
    setUpSearchResult(XML_RESULT_FILE_SERACH_FOR_GUICE);
    List<ProjectSummary> poms = mavenCrawler.getArtifactsById("anything-as-the-return-mocked");
    assertEquals("guice",poms.get(0).getArtifactId());
  }   
  @Test
  public void shouldReturnNothingFromArtifactIdSearch() throws Exception{
    setUpSearchResult(XML_RESULT_FILE_SEARCH_FOR_ITEM_NOT_IN_REPO);
    List<ProjectSummary> poms = mavenCrawler.getArtifactsById("anything-as-the-return-mocked");
    assertEquals(0,poms.size());
  }  
  
  private void setUpSearchResult(String testFilePath) throws Exception{
    String file = TestHelper.getFileAsString(testFilePath);
    when(mockPomdao.getData(anyString())).thenReturn(file);
    
  }
}
