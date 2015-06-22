package com.pete.testtools.dsl;

public class RequestMavenDependencyData implements DataRequest{
  private String groupId;
  private String artifactId;
  private String version;

  
  public String getURL(){
    return BASE_ENDPOINT_URL +"/project/" +groupId+"/"+artifactId+"/"+version;
  }
  
  public static class Builder {
    private String groupId;
    private String artifactId;
    private String version;

    public Builder groupId(String groupId) {
      this.groupId = groupId;
      return this;
    }

    public Builder artifactId(String artifactId) {
      this.artifactId = artifactId;
      return this;
    }

    public Builder version(String version) {
      this.version = version;
      return this;
    }

    public RequestMavenDependencyData build() {
      return new RequestMavenDependencyData(this);
    }
  }

  private RequestMavenDependencyData(Builder builder) {
    this.groupId = builder.groupId;
    this.artifactId = builder.artifactId;
    this.version = builder.version;
  }
}
