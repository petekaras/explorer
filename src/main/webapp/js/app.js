var app = angular.module('app', [ 'ui.bootstrap', 'ngTable', 'darthwade.loading' ]);

app.controller('appController', function($scope, DataService,$filter, $loading, ngTableParams) {

	$scope.libraries = [];
	$scope.message = { type: 'success', msg: 'Search for an artifact and the dependcy will be visualized below...' };

	$loading.setDefaultOptions({

			  active: false, // Defines current loading state
			  text: 'Get dependency data...', // Display text
			  className: '', // Custom class, added to directive
			  overlay: true, // Display overlay
			  spinner: true, // Display spinner
			  spinnerOptions: {
			    lines: 12, // The number of lines to draw
			    length: 7, // The length of each line
			    width: 4, // The line thickness
			    radius: 10, // The radius of the inner circle
			    rotate: 0, // Rotation offset
			    corners: 1, // Roundness (0..1)
			    color: '#000', // #rgb or #rrggbb
			    direction: 1, // 1: clockwise, -1: counterclockwise
			    speed: 1, // Rounds per second
			    trail: 100, // Afterglow percentage
			    opacity: 1 / 4, // Opacity of the lines
			    fps: 20, // Frames per second when using setTimeout()
			    zIndex: 2e9, // Use a high z-index by default
			    className: 'dw-spinner', // CSS class to assign to the element
			    top: 'auto', // Center vertically
			    left: 'auto', // Center horizontally
			    position: 'relative' // Element position
			  }

	});
	
	$scope.tableParams = new ngTableParams({
		page : 1, // show first page
		count : 10,
		sorting : {
			groupId : 'asc' // initial sorting
		}
	}, {
		total : $scope.libraries.length, // length of data
		getData : function($defer, params) {

			var orderedData = params.sorting() ? $filter('orderBy')($scope.libraries, params.orderBy()) : $scope.libraries;

			var pageData = orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count()), sum = 0;

			$defer.resolve(pageData);
		}
	})

	$scope.getSimpleMavenData = function(form, data) {

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
		$loading.start('libraries');
		DataService.getData($scope,$loading, {
			'groupId' : library.group,
			'artifactId' : library.name,
			'version' : library.version
		});

	};
	$scope.clear = function() {
		alert("clearing...");
		$scope.data = [];
		$scope.$apply();

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
