package com.pete.endpoint;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.junit.Test;

import com.pete.testtools.IntegrationTest;
import com.pete.testtools.dsl.RequestMavenDependencyData;
/**
 * This integration test actually calls out to the Maven repo.
 * It is just a smoke test to ensure app works end to end. 
 * Unit tests provide more in depth testing using service stubs and mocks.
 * @author peter
 *
 */
public class MavenIT extends IntegrationTest{
  @Test
  public void shouldgetMavenDependencies() throws ClientProtocolException, IOException, InterruptedException {

    String url = new RequestMavenDependencyData.Builder().groupId("org.springframework").artifactId("spring-core").version("4.0.4.RELEASE").build().getURL();
    System.out.println("GETTING: " + url);
    String postResult = get(url);   
    
    /**
     * Test just to see if our requested data contains real data
     */
    assertEquals(true, postResult.contains("junit"));
    


  }
}
