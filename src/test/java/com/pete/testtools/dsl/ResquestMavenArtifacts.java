package com.pete.testtools.dsl;

public class ResquestMavenArtifacts implements DataRequest {
  private String artifactId;

  @Override
  public String getURL() {
    return BASE_ENDPOINT_URL + "/projectsummarycollection/" + artifactId;
  }

  public static class Builder {
    private String artifactId;

    public Builder artifactId(String artifactId) {
      this.artifactId = artifactId;
      return this;
    }

    public ResquestMavenArtifacts build() {
      return new ResquestMavenArtifacts(this);
    }
  }

  private ResquestMavenArtifacts(Builder builder) {
    this.artifactId = builder.artifactId;
  }
}
