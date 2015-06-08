package com.pete;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.pete.POMParser;
import com.pete.pom.POM;
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

    POM pom = pomParser.getPomObject();
    assertEquals("spring-core", pom.getArtifactId());

  }

  @Test
  public void shouldParseDependencies() throws Exception {
    
    POM pom = pomParser.getPomObject();
    assertEquals(4, pom.getDependencies().size());

  }

}
