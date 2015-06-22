package com.pete;

public class POMTests {

  protected String XML_FILE = "/com/pete/pomParserFile.xml";
  protected String XML_FILE_SIMPLE = "/com/pete/crawler/pomSimple.xml";
  protected String XML_FILE_SIMPLE_WITH_VARIABLES = "/com/pete/crawler/pomSimpleWithVariables.pom";
  protected String XML_FILE_WITH_ONE_UNRESOLVABLE_VARIABLE = "/com/pete/crawler/pomWithOneUnresolvableDependencyVariable.pom";
  protected String XML_FILE_WITH_ONE_UNRESOLVABLE_PROJECT_VARIABLE = "/com/pete/crawler/pomWithOneProjectInfoVariable.pom";
  protected String XML_RESULT_FILE_SERACH_FOR_GUICE = "/com/pete/crawler/artifactSearch.json";
  protected String XML_RESULT_FILE_SEARCH_FOR_ITEM_NOT_IN_REPO = "/com/pete/crawler/artifactSearchNothingFound.json";
  

  public POMTests() {
    super();
  }

}