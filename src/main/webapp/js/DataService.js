app.service('DataService', function() {

	this.getData = function($scope,details) {

		gapi.client.maven.maven.getDependencyTree(details).execute(function(resp) {
			if(!resp.code){				
				$scope.data = resp;
				$scope.$apply();

			}else{	
				alert('fail');					
			}
	 });		
	}
	
	this.getSearchData = function($scope,details) {	
		gapi.client.maven.maven.getLatestArtifacts(details).execute(function(resp) {
			if(!resp.code){


				$scope.libraries = resp.items;
				$scope.tableParams.reload();
				$scope.$apply();
				

			}else{	
				alert('fail');					
			}
	 });		
	}	
});