package com.pete.crawler;

import java.util.HashMap;
import java.util.Map;

import com.pete.pom.POM;

/**
 * Helper class to help resolve variables in the POM file. Need to cater for:
 * [1] properties defined internally [2] properties in parent pom files (need to
 * download these resources) [3] Project properties.
 * 
 * @author peter
 *
 */
public class POMVariableResolver {

  //TODO: passing in by reference ? good idea ?
  public POM resolve(final POM pom) {
    for (POM dependantPOM : pom.getDependencies()) {
        //System.out.println(dependantPOM.getVersion());
        String plainVariableName = dependantPOM.getVersion().replace("${", "").replace("}", "");
        if(pom.getProperties().keySet().contains(plainVariableName)){
          //System.out.println("REPLACED");
          dependantPOM.setVersion(pom.getProperties().get(plainVariableName));
        }
    }
    return pom;
  }
}
