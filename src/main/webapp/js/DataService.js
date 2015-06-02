app.service('DataService', function() {

	this.getData = function($scope,details) {	
		gapi.client.maven.maven.getDependencyTree(details).execute(function(resp) {
			if(!resp.code){

				
				$scope.data = resp;
				$scope.$apply();

			}else{	
//				MsgService.push('danger',ERROR_GENERAL);
//				$scope.alerts = MsgService.list();
//				$scope.$apply();
				alert('fail');	

				
			}
	 });		
	}
});