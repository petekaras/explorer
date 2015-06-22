package com.pete.pom;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.ApiResourceProperty;
import com.google.appengine.repackaged.org.codehaus.jackson.map.annotate.JsonRootName;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pete.crawler.POMDeserializer;
import com.pete.crawler.POMVariableResolver;

/**
 * Project Object
 * 
 * 
 */

public class Project {

  private String modelVersion;
  @Expose
  @SerializedName("group")
  @JsonProperty(value = "g")
  private String groupId;
  @Expose
  @SerializedName("name")
  @JsonProperty(value = "a")
  private String artifactId;
  @Expose
  @SerializedName("version")
  @JsonProperty(value = "latestVersion")
  private String version;
  @Expose
  @SerializedName("children")
  private LinkedList<Project> dependencies;

  private Map<String, String> properties;

  //private ParentPOM parentPOM;

  /**
   * Wether this POM has all its information resolved
   */
  public boolean isRootInformationResolved() {

    if (POMVariableResolver.isResolved(artifactId) && POMVariableResolver.isResolved(groupId) && POMVariableResolver.isResolved(version)) return true;
    return false;
  }

//  public ParentPOM getParentPOM() {
//    return parentPOM;
//  }
//
//  public void setParentPOM(ParentPOM parentPOM) {
//    this.parentPOM = parentPOM;
//  }

  public Project() {
    groupId = "(null)";
    artifactId = "(null)";
    version = "(null)";
    dependencies = new LinkedList<Project>();
    properties = new HashMap<String, String>();
  }

  @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
  public String getModelVersion() {
    return modelVersion;
  }

  public void setModelVersion(final String modelVersion) {
    this.modelVersion = modelVersion;
  }

  @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
  public String getGroupId() {
    return groupId;
  }

  public void setGroupId(final String groupId) {
    this.groupId = groupId;
  }

  public String getArtifactId() {
    return artifactId;
  }

  @ApiResourceProperty(name = "name")
  public void setArtifactId(final String artifactId) {
    this.artifactId = artifactId;
  }

  @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
  public String getVersion() {
    return version;
  }

  public void setVersion(final String version) {
    this.version = version;
  }

  @ApiResourceProperty(name = "children")
  public List<Project> getDependencies() {
    return Collections.unmodifiableList(dependencies);
  }

  public void addDependency(final Project pom) {
    this.dependencies.add(pom);
  }

  public void addProperty(final String key, final String value) {
    this.properties.put(key, value);
  }

  public Map<String, String> getProperties() {
    return properties;
  }

  public String toString() {
    return "modelVersion: " + modelVersion + " groupId: " + groupId + " artifactId:" + artifactId + " version: " + version;
  }

}
