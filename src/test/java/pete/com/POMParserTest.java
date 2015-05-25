package pete.com;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.pete.POMParser;
import com.pete.pom.POM;

public class POMParserTest extends POMTests {
  private POMParser pomParser = null;
  
  @Before
  public void setUp() throws Exception {
    pomParser = new POMParser(XML_FILE);
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
