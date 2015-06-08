package dao;

import java.io.IOException;
import java.net.MalformedURLException;

public interface POMDAO {
  public String getData(String urlStr) throws MalformedURLException, IOException;
  
}