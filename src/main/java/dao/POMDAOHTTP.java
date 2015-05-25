package dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

public class POMDAOHTTP implements POMDAO {
  /**
   * Sends an URL request and returns the answer.
   *
   * @param urlStr
   * @return the answer.
   * @throws FileNotFoundException
   * @throws UnknownHostException
   */
  public String getData(String urlStr) throws Exception {
    String result = null;

      URL url = new URL(urlStr);
      URLConnection conn = url.openConnection();

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
