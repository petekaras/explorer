package com.pete.crawler;

public class MavenHelper {
  public static final String MAVEN_ROOT = "https://repo1.maven.org/maven2";
  public static final int FIND_COUNT = 20;
  public static final String ARTIFACT_ID_PLACEHOLDER = "<artifactId>";
  public static final String MAVEN_API_ROOT = "http://search.maven.org/solrsearch/select?q=a:" + ARTIFACT_ID_PLACEHOLDER + "&rows=" + FIND_COUNT
      + "&wt=json";

  /**
   * Sanitize an input variable for the backend API
   * 
   * @param input
   * @return
   */
  public static String cleanSearchVariable(final String input) {
    return input.toLowerCase();
  }


}
