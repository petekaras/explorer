package com.pete.crawler;

import java.util.HashMap;
import java.util.Map;

import com.pete.pom.POM;

/**
 * Helper class to help resolve variables in the POM file. Need to cater for:
 * [1] properties defined internally [2] properties in parent pom files (need to
 * download these resources) [3] Project properties.
 * 
 * @author peter
 *
 */
public class POMVariableResolver {
  private static final String VARIABLE_PREFIX = "${";
  private static final String VARIABLE_SUFFIX = "}";
  private static final String PROJECT_VERSION = VARIABLE_PREFIX + "project.version" + VARIABLE_SUFFIX;
  private static final String VERSION = VARIABLE_PREFIX + "version" + VARIABLE_SUFFIX;
  private static final String NULL_VERSION = "(null)";

  /**
   * Resolve variables that are set as properties in the POM
   * 
   * @param properties
   * @param variable
   * @return
   */
  public static String resolveVariable(final Map<String, String> properties, final String variable) {
    String cleanVariable = variable.replace("${", "").replace("}", "");
    if (properties.keySet().contains(cleanVariable)) {
      return properties.get(cleanVariable);
    }
    return variable;
  }

  /**
   * Determine the project version. this could be directly set in the POM or in
   * the parent POM element
   * 
   * @param version
   * @return
   */
  public static String resolveProjectVersionVariables(final String variable, final String POMversion, final String ParentPOMVersion) {
    if (variable.equals(VERSION) || variable.equals(PROJECT_VERSION)) {
      if (POMversion != null && !POMversion.isEmpty())
        return POMversion;
      if (ParentPOMVersion != null && !ParentPOMVersion.isEmpty())
        return ParentPOMVersion;
    }
    return variable;
  }

  /**
   * Determine if the version number is resolved or a variable
   * 
   * @param version
   * @return
   */
  public static boolean isResolved(final String version) {
    return (!version.contains(VARIABLE_PREFIX) || !version.contains(VARIABLE_SUFFIX) || version.contains(NULL_VERSION) );
  }
}
