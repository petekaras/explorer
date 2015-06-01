package pete.com.testtools.dsl;
/**
 * Provides a common inteface to get the URL to use doddle API.
 * @author peter
 *
 */
public interface DataRequest {
public static String BASE_ENDPOINT_URL = "http://localhost:8080/_ah/api/maven/v1/pom/"; 
public String getURL();
}
