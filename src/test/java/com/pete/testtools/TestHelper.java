package com.pete.testtools;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.pete.crawler.MavenCrawler;

/**
 * General unit and integration test helper.
 * 
 * @author peter
 *
 */
public class TestHelper {
  private static final Logger LOGGER = Logger.getLogger(MavenCrawler.class.getName());

  public static String getFileAsString(final String filePath) {

    java.net.URL url = TestHelper.class.getResource(filePath);

    String file = null;
    try {
      Path resPath = Paths.get(url.toURI());
      file = new String(Files.readAllBytes(resPath), "UTF8");      
    } catch (IOException | URISyntaxException e) {
      LOGGER.log(Level.WARNING, "Cannot read test data: " + filePath + ":" + e);
    }
    return file;
  }
  
  public static File getFile(final String filePath) throws URISyntaxException {
    java.net.URL url = TestHelper.class.getResource(filePath);
    return new File(url.toURI());
  }
}
