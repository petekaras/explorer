package com.pete;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.pete.pom.Project;
import com.pete.testtools.TestHelper;
/**
 * Test extracting more complex POM information 
 * @author peter
 *
 */
public class POMParserComplexTests extends POMTests{
  private POMParser pomParser = null;

  @Before
  public void setUp() throws Exception {
    String file = TestHelper.getFileAsString(XML_FILE_SIMPLE_WITH_VARIABLES);
    pomParser = new POMParser(file);
  }
  
  
  @Test
  public void shouldParseFile() throws Exception {
    Project pom = pomParser.getPomObject();
    assertEquals("connid", pom.getArtifactId());

  }
  
  @Test
  public void shouldGetParentVersion() throws Exception {
    Project pom = pomParser.getPomObject();
    assertEquals("1.4.0.0", pom.getParentPOM().getVersion());
  }
  @Test
  public void shouldGetParentGroupId() throws Exception {
    Project pom = pomParser.getPomObject();
    assertEquals("net.tirasa.connid", pom.getParentPOM().getGroupId());
  }
  @Test
  public void shouldGetParentArtifactId() throws Exception {
    Project pom = pomParser.getPomObject();
    assertEquals("connid", pom.getParentPOM().getArtifactId());
  }
}
