package pete.com;

import java.net.UnknownHostException;
import java.util.Arrays;

import com.pete.crawler.Crawler;
import com.pete.crawler.POMCrawler;
import com.pete.pom.POM;

import dao.POMDAOHTTP;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) 
    {
      
        try {
          Crawler getter = new POMCrawler(new POMDAOHTTP());
          
          //POM rootPOM = new POM();
          //rootPOM.setArtifactId("spring-core");
          //rootPOM.setGroupId("org.springframework");
          //rootPOM.setVersion("4.0.4.RELEASE");
          //POM data = getter.getDependenciesFromPOM(rootPOM, "-",null);
          getter.getPOMWithAllDependencies("org.springframework", "spring-core", "4.0.4.RELEASE", "-", null);
          //sSystem.out.println(data);
        } catch (Exception e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }

        
    }
}
