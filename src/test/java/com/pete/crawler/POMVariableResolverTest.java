package com.pete.crawler;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.pete.POMParser;
import com.pete.POMTests;
import com.pete.pom.POM;
import com.pete.testtools.TestHelper;

public class POMVariableResolverTest extends POMTests {
  private static final String POM_WITH_VARIABLES_POM = "/com/pete/crawler/pomSimpleWithVariables.pom";
  POMVariableResolver pomVariableResolver;

  @Before
  public void setUp() throws Exception {
    pomVariableResolver = new POMVariableResolver();
  }

  @Test
  public void shouldResolveVariables() throws ParserConfigurationException, SAXException, IOException {
    POM testPOM = preparePOMForTest(POM_WITH_VARIABLES_POM);
    POM result = pomVariableResolver.resolve(testPOM);
    assertEquals("3.0.1", result.getDependencies().get(0).getVersion());
  }

  /**
   * Simply test that all works OK when there are no variables to be resolved
   * 
   * @throws ParserConfigurationException
   * @throws SAXException
   * @throws IOException
   */
  @Test
  public void shouldNotResolveVariables() throws ParserConfigurationException, SAXException, IOException {
    POM testPOM = preparePOMForTest(XML_FILE);
    POM result = pomVariableResolver.resolve(testPOM);
    assertEquals("1.1.3", result.getDependencies().get(0).getVersion());
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
  private POM preparePOMForTest(final String testFile) throws ParserConfigurationException, SAXException, IOException {
    String file = TestHelper.getFileAsString(testFile);
    POMParser pomParser = new POMParser(file);
    return pomParser.getPomObject();
  }
}
