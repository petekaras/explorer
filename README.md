# explorer

What is this ?
=============================================
Maven dependency explorer.
This application crawls POM files in the Maven central repo and discovers dependencies.


Built on Google Cloud with an AngularJS front end. D3 for data visualization.
### Build and test

`mvn clean install`

 This will:
 
   * Compile the application
   * Unit test the code
   * Integration test the code against a dev server running locally
   
   
### Run dev server locally
   
 `mvn appengine:devserver`
   
 This will:
 
   * Compile the application
   * Unit test the code
   * Run the application on the local dev server
   
 If you want to just run the server without running tests:
 `mvn appengine:devserver -Dmaven.test.skip=true`
   
### Update Google App engine
 
 `mvn appengine:update`  
 
 This will:
 
   * Compile the application
   * Unit test the code
   * Integration test the code against a dev server running locally
   * Deploy up to Google cloud    
 
### Import into Eclipse
 
 `mvn eclipse:eclipse`  
 
 This will:
 
   * Create the eclipse project so you can 'import existing project' in eclipse. 
