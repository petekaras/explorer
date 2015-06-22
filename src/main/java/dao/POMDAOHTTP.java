package dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.pete.endpoint.ProjectHub;



public class POMDAOHTTP implements POMDAO {
  private static final int RESPONSE_OK = 200;
  private static final Logger LOGGER = Logger.getLogger(POMDAOHTTP.class.getName());
  /**
   * Sends an URL request and returns the answer.
   *
   * @param urlStr - the fully formatted URL string
   * @return the response body as a string or null if anything other than a HTTP OK found.
   * @throws IOException 
   * @throws FileNotFoundException
   * @throws UnknownHostException
   */
  public String getData(String urlStr) throws IOException {
    String result = null;

      URL url = new URL(urlStr);
      HttpURLConnection conn = (HttpURLConnection)url.openConnection();
      if(conn.getResponseCode()!=RESPONSE_OK){
        LOGGER.log(Level.WARNING, "Response not OK: " + conn.getResponseCode() + " URL: " + urlStr);
        return null;
      }
      // Get the response
      BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      
      StringBuffer sb = new StringBuffer();
      String line;
      while ((line = rd.readLine()) != null) {
        sb.append(line);
      }
      rd.close();

      result = sb.toString();

    return result;
  }
}
