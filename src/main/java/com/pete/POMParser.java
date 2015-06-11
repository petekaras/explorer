package com.pete;

/*
 * Analyzes third party libraries of a Java applications
 * Copyright (C) 2011 Ogi Werest
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see http://www.gnu.org/licenses/.
 *
 * Version info:
 *     $HeadURL$
 *     $LastChangedDate$
 *     $LastChangedRevision$
 *     $LastChangedBy$
 */

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.pete.crawler.POMVariableResolver;
import com.pete.pom.POM;
import com.pete.pom.POMResponse;
import com.pete.pom.ParentPOM;

/**
 * Parses a POM XML into a POM object. TODO: review and reimplement a lot of
 * this. Dont like static code and generally needs a tidy up...
 * 
 * @author Ognjen Bubalo
 * @version $Id$
 */
public class POMParser {
  private POM pomObject;
  private static String lastDescription;
  private static String lastName;

  private static final Logger LOGGER = Logger.getLogger(POMParser.class.getName());

  /**
   * Returns the last parsed description.
   *
   * @return the last parsed description.
   */
  public static String getLastDescription() {
    return lastDescription;
  }

  /**
   * Returns the last parsed name.
   *
   * @return the last parsed name.
   */
  public static String getLastName() {
    return lastName;
  }

  /**
   * Returns the parsed POM object.
   *
   * @return the parsed pomObject
   */
  public POM getPomObject() {
    return pomObject;
  }

  public void setPomObject(POM pomObject) {
    this.pomObject = pomObject;
  }

  /**
   * Parses a pom.xml file into a POM object. TODO: Refactor: should not do so
   * much work on constructor
   * 
   * @param File
   *          xmlFile - XML File that should be parsed
   * @throws Exception
   */
  public POMParser(File xmlFile) throws Exception {
    pomObject = new POM();

    File fXmlFile = xmlFile;
    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

    Document doc = dBuilder.parse(fXmlFile);

    parsePOM(doc);

  }

  private void parsePOM(Document doc) {
    try {
      Element eElement = doc.getDocumentElement();
      if (eElement.getNodeType() == Node.ELEMENT_NODE) {

        pomObject.setModelVersion(getTagValue("modelVersion", eElement));

        pomObject.setGroupId(getTagValue("groupId", eElement));

        pomObject.setArtifactId(getTagValue("artifactId", eElement));

        pomObject.setVersion(getTagValue("version", eElement));
        POMParser.lastDescription = getTagValue("description", eElement);
        POMParser.lastName = getTagValue("name", eElement);

        /**
         * Parent POM info
         */        
        ParentPOM parentPOM = new ParentPOM();
        
        NodeList parentElement = eElement.getElementsByTagName("parent");
        if (parentElement.getLength() != 0) {
          Element element = (Element) parentElement.item(0);
          parentPOM.setGroupId(getTagValue("groupId", element));
          parentPOM.setArtifactId(getTagValue("artifactId", element));
          parentPOM.setVersion(getTagValue("version", element));          
        }
        
        pomObject.setParentPOM(parentPOM);
        // parentPOM.setVersion(version);

        // REFACTOR into same code as dependecy processing
        NodeList propertiesNode = eElement.getElementsByTagName("properties");
        if (propertiesNode.getLength() > 0) {
          NodeList propertiesList = propertiesNode.item(0).getChildNodes();
          for (int i = 0; i < propertiesList.getLength(); ++i) {
            if (propertiesList.item(i).getNodeType() == Node.ELEMENT_NODE) {
              Element element = (Element) propertiesList.item(i);
              pomObject.addProperty(element.getTagName(), element.getTextContent());
            }
          }
        }

        NodeList hList = eElement.getElementsByTagName("dependencies");

        if (hList.getLength() > 0) {

          if (hList.item(0).getNodeType() == Node.ELEMENT_NODE) {

            NodeList depList = hList.item(0).getChildNodes();

            for (int i = 0; i < depList.getLength(); ++i) { // dependency 1, 2,
                                                            // 3, ..
              if (depList.item(i).getNodeType() == Node.ELEMENT_NODE) {

                POM pomDependency = new POM();
                pomDependency.setArtifactId(getTagValue("artifactId", (Element) depList.item(i)));
                pomDependency.setGroupId(getTagValue("groupId", (Element) depList.item(i)));
                if (getTagValue("version", (Element) depList.item(i)) != null) {
                  String version = getTagValue("version", (Element) depList.item(i));
                  // TODO: revise this: refactor
                  String resolvedVersion = POMVariableResolver.resolveVariable(pomObject.getProperties(), version);
                  String resolvedVersionWithVersion = POMVariableResolver.resolveProjectVersionVariables(resolvedVersion, pomObject.getVersion(), pomObject.getParentPOM().getVersion());
                  pomDependency.setVersion(resolvedVersionWithVersion);
                }
                pomObject.addDependency(pomDependency);
              }
            }
          }
        }
      }
    } catch (Exception e) {
      LOGGER.log(Level.WARNING, e.getMessage());
    }
  }

  /**
   * Parses a pom.xml String into a POM object.
   *
   * @param String
   *          xmlFile - The String in XML format, that should be parsed into a
   *          POM object.
   * @throws ParserConfigurationException
   * @throws IOException
   * @throws SAXException
   * @throws Exception
   * @see POMParser#POMParser(File)
   */
  public POMParser(String xmlFile) throws ParserConfigurationException, SAXException, IOException {
    pomObject = new POM();

    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
    InputStream is = new ByteArrayInputStream(xmlFile.getBytes("UTF-8"));
    Document doc = dBuilder.parse(is);
    parsePOM(doc);

  }

  private static String getTagValue(String sTag, Element eElement) {
    String returner = null;
    try {
      NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();

      Node nValue = (Node) nlList.item(0);
      returner = nValue.getNodeValue();
    } catch (Exception e) {
      // TODO: bad, bad refactor.
    }
    return returner;
  }

}
