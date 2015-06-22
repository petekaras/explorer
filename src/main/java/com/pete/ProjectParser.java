package com.pete;

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
import com.pete.pom.ParentPOM;
import com.pete.pom.Project;

/**
 * Parses a POM XML into a POM object.
 * 
 */
public class ProjectParser {
  private static final String VERSION = "version";
  private static final String ELEMENT_GROUP_ID = "groupId";
  private static final String ELEMENT_ARTIFACT_ID = "artifactId";
  private Project project;
  private static final Logger LOGGER = Logger.getLogger(ProjectParser.class.getName());

  /**
   * Returns the parsed POM object.
   *
   * @return the parsed pomObject
   */
  public Project getProject() {
    return project;
  }

  /**
   * Parses a pom.xml file into a POM object. TODO: Refactor: should not do so
   * much work on constructor
   * 
   * @param File
   *          xmlFile - XML File that should be parsed
   * @throws Exception
   */
  public ProjectParser(File xmlFile) throws Exception {
    project = new Project();

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

        project.setModelVersion(getTagValue("modelVersion", eElement));

        project.setGroupId(getTagValue(ELEMENT_GROUP_ID, eElement));

        project.setArtifactId(getTagValue(ELEMENT_ARTIFACT_ID, eElement));

        project.setVersion(getTagValue(VERSION, eElement));

        NodeList propertiesNode = eElement.getElementsByTagName("properties");
        if (propertiesNode.getLength() > 0) {
          NodeList propertiesList = propertiesNode.item(0).getChildNodes();
          for (int i = 0; i < propertiesList.getLength(); ++i) {
            if (propertiesList.item(i).getNodeType() == Node.ELEMENT_NODE) {
              Element element = (Element) propertiesList.item(i);
              project.addProperty(element.getTagName(), element.getTextContent());
            }
          }
        }

        NodeList dependencies = eElement.getElementsByTagName("dependencies");

        if (dependencies.getLength() > 0 && dependencies.item(0).getNodeType() == Node.ELEMENT_NODE) {

          NodeList dependencyList = dependencies.item(0).getChildNodes();

          for (int i = 0; i < dependencyList.getLength(); ++i) {
            Node dependency = dependencyList.item(i);
            if (dependency.getNodeType() == Node.ELEMENT_NODE) {

              Project projectDependency = new Project();
              projectDependency.setArtifactId(getTagValue(ELEMENT_ARTIFACT_ID, (Element) dependency));
              projectDependency.setGroupId(getTagValue(ELEMENT_GROUP_ID, (Element) dependency));
              //Version is often substituted with a variable, so try to resolve that here ie: ${version} -> 1.4.5
              String version = getTagValue(VERSION, (Element) dependency);
              String resolvedVersion = POMVariableResolver.resolveVariable(project.getProperties(), version);
              projectDependency.setVersion(resolvedVersion);

              project.addDependency(projectDependency);
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
   * @see ProjectParser#POMParser(File)
   */
  public ProjectParser(String xmlFile) throws ParserConfigurationException, SAXException, IOException {
    project = new Project();

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
      LOGGER.log(Level.WARNING, e.getMessage());
    }
    return returner;
  }

}
