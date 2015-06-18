package com.pete.endpoint;

public class MavenHelper {
/**
 * Sanitize an input variable for the backend API
 * @param input
 * @return
 */
  protected static String cleanSearchVariable(final String input){
    return input.toLowerCase();
  }
}
