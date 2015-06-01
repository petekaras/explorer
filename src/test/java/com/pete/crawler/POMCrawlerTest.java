package com.pete.crawler;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.pete.POMTests;
import com.pete.crawler.Crawler;
import com.pete.crawler.POMCrawler;
import com.pete.pom.POM;

import dao.POMDAO;

public class POMCrawlerTest extends POMTests {
  private POMCrawler POMCrawler;
  private POMDAO mockPomdao;
  String indent;
  String parentArtifactId;
  POM rootPOM;

  @Before
  public void setUp() throws Exception {
    mockPomdao = mock(POMDAO.class);
    POMCrawler = new POMCrawler(mockPomdao);
    rootPOM = new POM();
    rootPOM.setArtifactId("pete");
    rootPOM.setGroupId("test");
    rootPOM.setVersion("1.0");
    indent = "-";
    parentArtifactId = null;
    when(mockPomdao.getData(anyString())).thenReturn(XML_FILE_SIMPLE);
  }

  @Test
  public void testGetrootInfoFromPOM() throws Exception {


    POM data = POMCrawler.getDependenciesFromPOM(rootPOM, indent, parentArtifactId);
    assertEquals("pete", data.getArtifactId());
  }
  
  @Test
  public void testGetrootInfoFromPOMOverloaded() throws Exception {


    POM data = POMCrawler.getPOMWithAllDependencies(rootPOM.getGroupId(),rootPOM.getArtifactId(),rootPOM.getVersion(), indent, parentArtifactId);
    assertEquals("pete", data.getArtifactId());
  }  

}
