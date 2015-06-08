var app = angular.module('app', [ 'ui.bootstrap','ngTable' ]);

app.controller('appController', function($scope, DataService, ngTableParams) {
    
    $scope.libraries = [{group: "org.springframework", name: "spring-core",version:"4.0.4.RELEASE"},
                    {group: "merlin", name: "merlin-api",version:"3.3.0"}];
    

    
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
    
    
    
    
    $scope.getSimpleMavenData = function(form,data) {		

	if (form.$valid) {
	    DataService.getSearchData($scope, {
		'artifactId' : data.artifactId
	    });
	}
    };
    
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
		'groupId' : library.group,
		'artifactId' : library.name,
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
