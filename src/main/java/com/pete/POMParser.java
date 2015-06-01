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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;





import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.pete.pom.POM;

/**
 * Parses a POM XML into a POM object.
 *
 * @author Ognjen Bubalo
 * @version $Id$
 */
public class POMParser {
 private POM pomObject;
 private static String lastDescription;
 private static String lastName;
 
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
   * Parses a pom.xml file into a POM object.
   * TODO: Refactor: should not do so much work on constructor
   * @param File
   *          xmlFile - XML File that should be parsed
   * @throws Exception
   */
 public POMParser(File xmlFile) throws Exception{
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
   //System.out.println("Root element :" + eElement.getNodeName()); //project
   if (eElement.getNodeType() == Node.ELEMENT_NODE) {

    //System.out.println("modelVersion : " + getTagValue("modelVersion", eElement));
    pomObject.setModelVersion(getTagValue("modelVersion", eElement));

    //System.out.println("groupId : " + getTagValue("groupId", eElement));
    pomObject.setGroupId(getTagValue("groupId", eElement));

    //System.out.println("artifactId : " + getTagValue("artifactId", eElement));
    pomObject.setArtifactId(getTagValue("artifactId", eElement));

    //System.out.println("version : " + getTagValue("version", eElement));
    pomObject.setVersion(getTagValue("version", eElement));
    POMParser.lastDescription = getTagValue("description", eElement);
    POMParser.lastName = getTagValue("name", eElement);

    NodeList hList = eElement.getElementsByTagName("dependencies");

    if (hList.getLength()>0) {
     //System.out.println("Node Name : " + hList.item(0).getNodeName()); //dependencies
     if (hList.item(0).getNodeType() == Node.ELEMENT_NODE) {

      NodeList depList = hList.item(0).getChildNodes();

      for (int i=0;i<depList.getLength();++i) { //dependency 1, 2, 3, ..
       if (depList.item(i).getNodeType() == Node.ELEMENT_NODE) {
       // System.out.println("Dependency : " + depList.item(i).getNodeName()); 
        //System.out.println("FIGY: " + getTagValue("groupId",(Element)depList.item(i)));
        POM pomDependency = new POM();
        pomDependency.setArtifactId(getTagValue("artifactId",(Element)depList.item(i)));
        pomDependency.setGroupId(getTagValue("groupId",(Element)depList.item(i)));
        if (getTagValue("version",(Element)depList.item(i))!=null){
         pomDependency.setVersion(getTagValue("version",(Element)depList.item(i)));
        }
        pomObject.addDependency(pomDependency);
       }
      }
     }
    }
   }
  } catch (Exception e) {
  // e.printStackTrace();
  }
 }
 
 /**
  * Parses a pom.xml String into a POM object.
  *
  * @param String xmlFile - The String in XML format, that should be parsed into a POM object.
 * @throws Exception 
  * @see POMParser#POMParser(File)
  */
 public POMParser(String xmlFile) throws Exception {
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
   //System.err.println(sTag+" tag does not exist");
  }
  return returner;
 }

}
