package com.pete.crawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.google.appengine.repackaged.com.google.protos.gdata.proto2api.Core.Response;
import com.pete.pom.Project;
import com.pete.pom.POMResponse;
import com.pete.pom.ProjectSummary;
/**
 * Convert results of Maven search to a list of search Objects.
 * @author peter
 *
 */
public class POMDeserializer extends JsonDeserializer<List<ProjectSummary>> {

  @Override
  public List<ProjectSummary> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {

    ObjectMapper mapper = new ObjectMapper();
    JsonNode node = mapper.readTree(jsonParser);

    ProjectSummary[] poms = mapper.convertValue(node.findValue("docs"), ProjectSummary[].class);
   
    return Arrays.asList(poms);

  }
}
