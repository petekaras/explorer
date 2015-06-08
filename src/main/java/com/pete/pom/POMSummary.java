package com.pete.pom;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.google.api.server.spi.config.ApiResourceProperty;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Summary Object(DTO) for returning summary of POM Objects Note: Annotation
 * here refer to [1] conversion of JSON from Maven to POJO using Jackson and [2]
 * conversion of POJO by endpoint code.
 * 
 * @author peter
 *
 */
@JsonIgnoreProperties({ "id", "repositoryId", "p", "timestamp", "versionCount", "text", "ec" })
public class POMSummary {

  @Expose
  @SerializedName("group")
  @JsonProperty(value = "g")
  private String group;
  @Expose
  @SerializedName("name")
  @JsonProperty(value = "a")
  private String artifact;
  @Expose
  @SerializedName("version")
  @JsonProperty(value = "latestVersion")
  private String version;
  
  @ApiResourceProperty(name = "group")
  public String getGroupId() {
    return group;
  }

  public void setGroupId(String groupId) {
    this.group = groupId;
  }
  @ApiResourceProperty(name = "name")
  public String getArtifactId() {
    return artifact;
  }

  public void setArtifactId(String artifactId) {
    this.artifact = artifactId;
  }
  @ApiResourceProperty(name = "version")
  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

}
