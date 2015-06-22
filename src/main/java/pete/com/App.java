package pete.com;

import com.pete.crawler.ProjectBrowser;
import com.pete.crawler.MavenBrowser;

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
          ProjectBrowser getter = new MavenBrowser(new POMDAOHTTP());
          
          //POM rootPOM = new POM();

          //rootPOM.setGroupId("org.springframework");
          //rootPOM.setArtifactId("spring-core");
          //rootPOM.setVersion("4.0.4.RELEASE");
          //POM data = getter.getDependenciesFromPOM(rootPOM, "-",null);
          getter.getDependencyTree("org.springframework", "spring-core", "4.0.4.RELEASE", null);
          //sSystem.out.println(data);
        } catch (Exception e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }

        
    }
}
