var app = angular.module('app', [ 'ui.bootstrap' ]);

app.controller('appController', function($scope, DataService) {
    
    
    $scope.getMavenData = function(form) {		
		
	if (form.$valid) {
	    DataService.getData($scope, {
		'groupId' : $scope.group,
		'artifactId' : $scope.artifact,
		'version' : $scope.version
	    });
	}
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
