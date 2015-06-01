package com.pete.pom;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
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


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;





public class POM {

 private String modelVersion;
 private String groupId;
 @Expose
 @SerializedName("name")
 private String artifactId;
 private String version;
 @Expose
 @SerializedName("children")
 private LinkedList<POM> dependencies;



 public POM() {
 groupId = "(null)";
  artifactId = "(null)";
  version = "(null)";
  dependencies = new LinkedList<POM>();
 }


 public String getModelVersion() {
  return modelVersion;
 }
 
 public void setModelVersion(final String modelVersion) {
  this.modelVersion = modelVersion;
 }
 
 public String getGroupId() {
  return groupId;
 }
 
 public void setGroupId(final String groupId) {
  this.groupId = groupId;
 }
 
 public String getArtifactId() {
  return artifactId;
 }
 
 public void setArtifactId(final String artifactId) {
  this.artifactId = artifactId;
 }
 
 public String getVersion() {
  return version;
 }
 
 public void setVersion(final String version) {
  this.version = version;
 }
 
 public List<POM> getDependencies() {
  return Collections.unmodifiableList( dependencies );
 }
 
 public void addDependency(final POM pom) {
  this.dependencies.add(pom);
 }
 
 public String toString() {
  return "modelVersion: "+ modelVersion + " groupId: " + groupId + " artifactId:" + artifactId + " version: " + version;
 }
 
 @Override
 public int hashCode() {
  final int prime = 31;
  int result = 1;
  result = prime * result
    + ((artifactId == null) ? 0 : artifactId.hashCode());
  result = prime * result
    + ((dependencies == null) ? 0 : dependencies.hashCode());
  result = prime * result + ((groupId == null) ? 0 : groupId.hashCode());
  result = prime * result
    + ((modelVersion == null) ? 0 : modelVersion.hashCode());
  result = prime * result + ((version == null) ? 0 : version.hashCode());
  return result;
 }
 
 @Override
 public boolean equals(final Object obj) {
  if (this == obj)
   return true;
  if (obj == null)
   return false;
  if (getClass() != obj.getClass())
   return false;
  final POM other = (POM) obj;
  if (artifactId == null) {
   if (other.artifactId != null)
    return false;
  } else if (!artifactId.equals(other.artifactId))
   return false;
  if (groupId == null) {
   if (other.groupId != null)
    return false;
  } else if (!groupId.equals(other.groupId))
   return false;
  if (version == null) {
   if (other.version != null)
    return false;
  } else if (!version.equals(other.version))
   return false;
  return true;
 }
 
 
}