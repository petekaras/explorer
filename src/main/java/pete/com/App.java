package pete.com;

import com.pete.crawler.Crawler;
import com.pete.crawler.MavenCrawler;

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
          Crawler getter = new MavenCrawler(new POMDAOHTTP());
          
          //POM rootPOM = new POM();

          //rootPOM.setGroupId("org.springframework");
          //rootPOM.setArtifactId("spring-core");
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
