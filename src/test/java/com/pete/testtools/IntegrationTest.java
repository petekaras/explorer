package com.pete.testtools;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.junit.After;
import org.junit.Before;



public abstract class IntegrationTest {
  

  


  @Before
  public void setUp() {

    System.out.println(" _       _                       _   _               _            _");   
    System.out.println("(_)     | |                     | | (_)             | |          | |");  
    System.out.println(" _ _ __ | |_ ___  __ _ _ __ __ _| |_ _  ___  _ __   | |_ ___  ___| |");
    System.out.println("| | '_ \\| __/ _ \\/ _` | '__/ _` | __| |/ _ \\| '_ \\  | __/ _ \\/ __| __|");
    System.out.println("| | | | | ||  __/ (_| | | | (_| | |_| | (_) | | | | | ||  __/\\__ \\ |_"); 
    System.out.println("|_|_| |_|\\__\\___|\\__, |_|  \\__,_|\\__|_|\\___/|_| |_|  \\__\\___||___/\\__|");
    System.out.println("                  __/ |");                                               
    System.out.println("                 |___/");                                                
  }
  
  @After
  public void tearDown() {


  }
  /**
   * POST to a url
   * @param url
   * @return
   * @throws ClientProtocolException
   * @throws IOException
   * @throws InterruptedException 
   */
  public String post(final String url) throws ClientProtocolException, IOException, InterruptedException{

    HttpPost httppost = new HttpPost(url);
    String result = doWork(httppost,null);
    return result;
  }
  


  /**
   * GET a Url
   * @param url
   * @return
   * @throws ClientProtocolException
   * @throws IOException
   */
  public String get(final String url) throws ClientProtocolException, IOException{

    HttpGet httpGet = new HttpGet(url);
    return doWork(httpGet,null);

  } 
  
  
  public String getWithAuthCode(final String url, final String authCode) throws ClientProtocolException, IOException{
    System.out.println("URL: "+url );
    HttpGet httpGet = new HttpGet(url);
    return doWork(httpGet,authCode);

  } 
  
  public String putWithAuthCode(final String url, final String authCode) throws ClientProtocolException, IOException, InterruptedException{
    HttpPut httpPut = new HttpPut(url);
    String result = doWork(httpPut,authCode);
   
    
    return result;  
  }
  public String postWithAuthCode(final String url, final String authCode) throws ClientProtocolException, IOException, InterruptedException{
    HttpPost httpPost = new HttpPost(url);
    
    /**
     * This is a testing anti pattern, but updates in datastore need time to settle as indexes are built.
     * So we need to wait otherwise gets to verify results wont work.
     */
    
    String result = doWork(httpPost,authCode);
    return result;

  }   
 
  private String doWork(final HttpUriRequest request,final String authCode) throws ClientProtocolException, IOException{
    HttpClient httpclient = HttpClients.createDefault();
    
    request.setHeader("Authorization",authCode);
    
    
    HttpResponse response = httpclient.execute(request);
    HttpEntity entity = response.getEntity();    
    if (entity != null) {
      InputStream instream = entity.getContent();
      
      try {
        String theString = IOUtils.toString(instream);        
        System.out.println(theString );
        return theString;
      } finally {
          instream.close();
      }
  }
    return null;
  } 
  /**
   * Put user in DB
   * May or may not exist. Doesnt matter.
   * @throws InterruptedException 
   */  
  protected String putFaceBookUserInDatabase(final String authCode) throws ClientProtocolException, IOException, InterruptedException{
      return postWithAuthCode("http://localhost:8080/_ah/api/doddle/v1/createFaceBookUser", authCode);
  }
  
  
  protected String getRandomString(){
    char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    StringBuilder sb = new StringBuilder();
    Random random = new Random();
    for (int i = 0; i < 20; i++) {
        char c = chars[random.nextInt(chars.length)];
        sb.append(c);
    }
    return sb.toString();

  }
  
  protected String getLoggedInTestUser() throws ClientProtocolException, IOException{
    return getWithAuthCode("http://localhost:8080/_ah/api/register/v1/userdto", "");
    }
  
  protected String getAuthCodeFromResponse(final String postResult){
    Object obj = JSONValue.parse(postResult);
    JSONObject jsonObject = (JSONObject) obj;
    String token = jsonObject.get("accessToken").toString();
    return token;
  }
  
  protected String getFirstCatIdFromResponse(final String result){
    Object obj=JSONValue.parse(result);
    JSONObject jsonObject = (JSONObject) obj;
    JSONArray items = (JSONArray) jsonObject.get("catIds");
    String catkey = (String)items.get(0);
    return catkey.toString();
    
  }
  
  protected String getRandomEmail(){
    return RandomStringUtils.random(10, "abcdefghijklmnopqrstuvwxyz")+ "@gmail.com";
  }

}

