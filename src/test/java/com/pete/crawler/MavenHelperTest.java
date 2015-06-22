package com.pete.crawler;

import static org.junit.Assert.*;

import org.junit.Test;

import com.pete.crawler.MavenHelper;

public class MavenHelperTest {

  @Test
  public void shouldSanitizeVariable() {
    String result = MavenHelper.cleanSearchVariable("UpperAndLOWEr");
    assertEquals("upperandlower", result);
  }
  

}
