package com.pete;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.pete.pom.Project;
import com.pete.testtools.TestHelper;

public class POMParserTest extends POMTests {
  private POMParser pomParser = null;

  @Before
  public void setUp() throws Exception {
    String file = TestHelper.getFileAsString(XML_FILE);
    pomParser = new POMParser(file);
  }

  @Test
  public void shouldParseFile() throws Exception {

    Project pom = pomParser.getPomObject();
    assertEquals("spring-core", pom.getArtifactId());

  }

  @Test
  public void shouldParseDependency() throws Exception {
    Project pom = pomParser.getPomObject();
    for(Project dependency : pom.getDependencies()){
      if(dependency.getArtifactId().equals("jopt-simple")){
        assertEquals(dependency.getVersion(), "4.6");
      }
    }
  }
  
  @Test
  public void shouldResolveDependency() throws Exception {

    Project pom = pomParser.getPomObject();
    assertEquals(4, pom.getDependencies().size());

  }

  @Test
  public void shouldParseProperties() throws Exception {

    Project pom = pomParser.getPomObject();
    assertEquals(3, pom.getProperties().size());

  }

  @Test
  public void shouldParsePropertyKey() throws Exception {

    Project pom = pomParser.getPomObject();
    assertEquals(true, pom.getProperties().containsKey("maven.version"));

  }

  @Test
  public void shouldParsePropertyValue() throws Exception {

    Project pom = pomParser.getPomObject();
    assertEquals(true, pom.getProperties().containsValue("3.0.0"));

  }

}
