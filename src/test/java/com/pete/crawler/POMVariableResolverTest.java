package com.pete.crawler;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.pete.ProjectParser;
import com.pete.POMTests;
import com.pete.pom.Project;
import com.pete.testtools.TestHelper;

public class POMVariableResolverTest extends POMTests {
  private static final String POM_WITH_VARIABLES_POM = "/com/pete/crawler/pomSimpleWithVariables.pom";
  POMVariableResolver pomVariableResolver;

  @Before
  public void setUp() throws Exception {
    pomVariableResolver = new POMVariableResolver();
  }




  
  @Test
  public void shouldResolveVariable() throws ParserConfigurationException, SAXException, IOException {

    String result = POMVariableResolver.resolveVariable(getProperties(), "${default.encoding}");
    assertEquals("UTF-8", result);
  }
  
  @Test
  public void shouldNotResolveVariable() throws ParserConfigurationException, SAXException, IOException {

    String result = POMVariableResolver.resolveVariable(getProperties(), "${variable.not.in.properties}");
    assertEquals("${variable.not.in.properties}", result);
  }
  
  @Test 
  public void shouldResolvePOMVersion(){
    String result = POMVariableResolver.resolveProjectVersionVariables("${version}", "12.8",null);
    assertEquals("12.8", result);
  }
  @Test 
  public void shouldResolvePOMProjectVersion(){
    String result = POMVariableResolver.resolveProjectVersionVariables("${project.version}", "12.8","3.4");
    assertEquals("12.8", result);
  }
  @Test 
  public void shouldResolvePOMProjectVersionFromParent(){
    String result = POMVariableResolver.resolveProjectVersionVariables("${project.version}", null,"3.4");
    assertEquals("3.4", result);
  }  
  @Test 
  public void shouldWorkWithEmptyString(){
    String result = POMVariableResolver.resolveProjectVersionVariables("${project.version}", "","3.4");
    assertEquals("3.4", result);
  } 
  
  @Test 
  public void shouldNotResolvePOMVersion(){
    String result = POMVariableResolver.resolveProjectVersionVariables("${groupId}", "12.8",null);
    assertEquals("${groupId}", result);
  }  
  @Test 
  public void shouldIdentifyResolved(){
    boolean result = POMVariableResolver.isResolved("1.5.6");
    assertEquals(true, result);
  } 
  @Test 
  public void shouldIdentifyUnResolved(){
    boolean result = POMVariableResolver.isResolved("${version}");
    assertEquals(false, result);
  }
  @Test 
  public void shouldIdentifyResolvedNull(){
    boolean result = POMVariableResolver.isResolved("(null)");
    assertEquals(false, result);
  } 
  @Test 
  public void shouldCopeWithNull(){
    boolean result = POMVariableResolver.isResolved(null);
    assertEquals(false, result);
  } 
  @Test 
  public void shouldCopeWithEmptyString(){
    boolean result = POMVariableResolver.isResolved("");
    assertEquals(false, result);
  }   
 

  
  private Map<String, String>  getProperties(){
    Map<String, String> properties = new HashMap<String, String>();
    properties.put("default.encoding", "UTF-8");
    properties.put("default.other", "some-other-value");
    return properties;
  }
  /**
   * We are using the actual POM Parser here to prepare test data, but we can
   * live with this test dependency for now.
   * 
   * @param testFile
   * @return
   * @throws ParserConfigurationException
   * @throws SAXException
   * @throws IOException
   */
  private Project preparePOMForTest(final String testFile) throws ParserConfigurationException, SAXException, IOException {
    String file = TestHelper.getFileAsString(testFile);
    ProjectParser pomParser = new ProjectParser(file);
    return pomParser.getProject();
  }
}
