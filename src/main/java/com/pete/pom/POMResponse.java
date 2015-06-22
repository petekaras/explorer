package com.pete.pom;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

import com.pete.crawler.POMDeserializer;
/**
 * Wrapper class to help with the deserialization of search results from Maven
 * @author peter
 *
 */
@JsonIgnoreProperties({"responseHeader","spellcheck"}) 
 public class POMResponse {

@JsonDeserialize(using = POMDeserializer.class)
@JsonProperty(value = "response")
public List<ProjectSummary> listOfPoms;

}
