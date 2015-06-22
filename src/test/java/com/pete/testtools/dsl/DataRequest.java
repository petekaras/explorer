package com.pete.testtools.dsl;
/**
 * Provides a common inteface to get the URL to use doddle API.
 * @author peter
 *
 */
public interface DataRequest {
public static String BASE_ENDPOINT_URL = "http://localhost:8080/_ah/api/dependency/v1";
public String getURL();
}
