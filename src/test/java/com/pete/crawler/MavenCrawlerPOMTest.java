package com.pete.crawler;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.pete.POMTests;
import com.pete.crawler.Crawler;
import com.pete.crawler.MavenCrawler;
import com.pete.pom.POM;
import com.pete.testtools.TestHelper;

import dao.POMDAO;

public class MavenCrawlerPOMTest extends POMTests {
  private MavenCrawler MavenCrawler;
  private POMDAO mockPomdao;
  String indent;
  String parentArtifactId;
  POM rootPOM;

  @Before
  public void setUp() throws Exception {
    mockPomdao = mock(POMDAO.class);
    MavenCrawler = new MavenCrawler(mockPomdao);
    rootPOM = new POM();
    rootPOM.setArtifactId("pete");
    rootPOM.setGroupId("test");
    rootPOM.setVersion("1.0");
    indent = "-";
    parentArtifactId = null;
    String file = TestHelper.getFileAsString(XML_FILE_SIMPLE);
    when(mockPomdao.getData(anyString())).thenReturn(file);
  }

  @Test
  public void shouldGetrootInfoFromPOM() throws Exception {


    POM data = MavenCrawler.getDependenciesFromPOM(rootPOM, indent, parentArtifactId);
    assertEquals("pete", data.getArtifactId());
  }
  
  @Test
  public void shouldGetrootInfoFromPOMOverloaded() throws Exception {


    POM data = MavenCrawler.getPOMWithAllDependencies(rootPOM.getGroupId(),rootPOM.getArtifactId(),rootPOM.getVersion(), indent, parentArtifactId);
    assertEquals("pete", data.getArtifactId());
  }  
  


}
