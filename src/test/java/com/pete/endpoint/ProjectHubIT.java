package com.pete.endpoint;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.junit.Ignore;
import org.junit.Test;

import com.pete.testtools.IntegrationTest;
import com.pete.testtools.dsl.RequestMavenDependencyData;
import com.pete.testtools.dsl.ResquestMavenArtifacts;
/**
 * This integration test actually calls out to the Maven repo.
 * It is just a smoke test to ensure app works end to end. 
 * Unit tests provide more in depth testing using service stubs and mocks.
 * @author peter
 *
 */
public class ProjectHubIT extends IntegrationTest{
  @Test
  public void shouldgetMavenDependencies() throws ClientProtocolException, IOException, InterruptedException {

    String url = new RequestMavenDependencyData.Builder().groupId("org.springframework").artifactId("spring-core").version("4.0.4.RELEASE").build().getURL();
    System.out.println("GETTING: " + url);
    String result = post(url);   
    
    /**
     * Test just to see if our requested data contains real data
     */
    assertEquals(true, result.contains("junit"));

  }
  
  @Test 
  public void shouldReturnNotFoundForArtifact() throws ClientProtocolException, IOException, InterruptedException {

    String url = new RequestMavenDependencyData.Builder().groupId("some.silly.group").artifactId("webdriver-selenium").version("0.9.7376").build().getURL();
    String result = post(url);   
    
    /**
     * Test for 404 code
     */
    assertEquals(true, result.contains("404"));

  }
  
  @Test 
  public void shouldReturnNotFoundForArtifacts() throws ClientProtocolException, IOException, InterruptedException {

    String url = new ResquestMavenArtifacts.Builder().artifactId("sillArtifact").build().getURL();    
    String result = post(url); 
    
    /**
     * Test for 404 code
     */
    assertEquals(true, result.contains("404"));

  }


  
  @Test
  public void shouldgetSomeArtifacts() throws ClientProtocolException, IOException, InterruptedException {

    String url = new ResquestMavenArtifacts.Builder().artifactId("guice").build().getURL();    
    String result = post(url);   
    
    /**
     * Test just to see if our requested data contains the correct structure
     */
    assertEquals(true, result.contains("group"));
    assertEquals(true, result.contains("name"));    
    assertEquals(true, result.contains("version"));
    /**
     * Make sure basic data is there
     */
    assertEquals(true, result.contains("guice"));

  }
}
