app.service('DataService', function() {

	this.getData = function($scope,$loading,details) {
		var messageText;
		var messageType;

		gapi.client.maven.maven.getDependencyTree(details).execute(function(resp) {
			//TODO: refactor messages into a helper service (unit test). Also handle different error code
			
			
			if(!resp.code){				
				$scope.data = resp;
				messageText = "Showing dependency tree for : " + details.artifactId + "-" + details.version;
				messageType = "success";
			}else{
				messageText = "Could not get data for : " + details.artifactId + "-" + details.version; 				
				messageType = "danger";
			}
			$scope.message = { type: messageType, msg: messageText };
			$loading.finish('libraries');
			$scope.$apply();
	 });		
	}
	
	this.getSearchData = function($scope,details) {	
		gapi.client.maven.maven.getLatestArtifacts(details).execute(function(resp) {
			if(!resp.code){
				messageText = "Showing all artifacts with id of : " + details.artifactId;
				messageType = "success";
				
				$scope.libraries = resp.items;
				$scope.tableParams.reload();

				

			}else{	
				messageText = "Couldnt get anything for an artifact with id of : " + details.artifactId;
				messageType = "danger";	

			}
			$scope.message = { type: messageType, msg: messageText };
			$scope.$apply();
	 });		
	}	
});