//var app = angular.module('app', []);

var app = angular
.module('app', ['xml'])
.config(function ($httpProvider) {
  $httpProvider.interceptors.push('xmlHttpInterceptor');
});


app.controller('appController', function ($scope,userService) {
	var data = userService.getSubredditsSubmittedToBy('yoitsnate');
	 
  });

angular.module("app").service("userService",
		function($http) {
		  return {
		    getSubredditsSubmittedToBy: function(user) {
		    	var url = "http://api.reddit.com/user/yoitsnate/submitted.json";
		    	var urlMaven = "https://repo1.maven.org/maven2/com/jolira/guice/3.0.0/guice-3.0.0.pom";
		    	var urlMaven2 = "http://search.maven.org/solrsearch/select?q=guice&rows=20&wt=json";
		    	return $http.get(urlMaven2).then(function(response) {
		    	console.log('---' + response);
		        return response;
		      });
		    }
		  };
		});

