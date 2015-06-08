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
import com.pete.pom.POM;
import com.pete.pom.POMResponse;
import com.pete.pom.POMSummary;
/**
 * Convert results of Maven search to a list of search Objects.
 * @author peter
 *
 */
public class POMDeserializer extends JsonDeserializer<List<POMSummary>> {

  @Override
  public List<POMSummary> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {

    ObjectMapper mapper = new ObjectMapper();
    JsonNode node = mapper.readTree(jsonParser);

    POMSummary[] poms = mapper.convertValue(node.findValue("docs"), POMSummary[].class);
   
    return Arrays.asList(poms);

  }
}
