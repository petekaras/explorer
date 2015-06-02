var app = angular.module('app', [ 'ui.bootstrap','ngTable' ]);

app.controller('appController', function($scope, DataService, ngTableParams) {
    
    $scope.libraries = [{groupId: "org.springframework", artifactId: "spring-core",version:"4.0.4.RELEASE"},
                    {groupId: "merlin", artifactId: "merlin-api",version:"3.3.0"},
                    {groupId: "org.apache.camel", artifactId: "camel",version:"2.15.2"},
                    {groupId: "com.fizz-buzz", artifactId: "fb-android-dagger",version:"1.0.6"},
                    {groupId: "org.gridkit.jvmtool", artifactId: "hprof-heap",version:"0.3.6"}];
    
    
    
    $scope.tableParams = new ngTableParams({
        page: 1,            // show first page
        count: 10           // count per page
    }, {
        total: $scope.libraries.length, // length of data
        getData: function ($defer, params) {
            var pageData = $scope.libraries.slice((params.page() - 1) * params.count(), params.page() * params.count()),
                sum = 0;

 
            $defer.resolve(pageData);
        }
    })
    
    
    
    
    
    
    $scope.getMavenData = function(form) {		
		
	if (form.$valid) {
	    DataService.getData($scope, {
		'groupId' : $scope.group,
		'artifactId' : $scope.artifact,
		'version' : $scope.version
	    });
	}
    };
    
    $scope.getMavenDataFromTableClick = function(library) {		


	    DataService.getData($scope, {
		'groupId' : library.groupId,
		'artifactId' : library.artifactId,
		'version' : library.version
	    });

    };    

});

/**
 * TODO: integrate better into Angular JS
 */
function init() {
    // var ROOT = 'https://sigma-cortex-91512.appspot.com/_ah/api';
    var ROOT = 'http://localhost:8080/_ah/api/';
    // var CLIENT_ID =
    // '39770402649-5ogij7343kcteu7lv2emk6jdfpohe0tc.apps.googleusercontent.com';
    gapi.client.load('maven', 'v1', function() {

    }, ROOT);

}
