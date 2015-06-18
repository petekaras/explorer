package com.pete.endpoint;

import static org.junit.Assert.*;

import org.junit.Test;

import com.pete.endpoint.MavenHelper;

public class MavenHelperTest {

  @Test
  public void shouldSanitizeVariable() {
    String result = MavenHelper.cleanSearchVariable("UpperAndLOWEr");
    assertEquals("upperandlower", result);
  }

}
