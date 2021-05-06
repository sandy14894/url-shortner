var app = angular.module('myApp', []);
app.controller('myCtrl', function($scope,$http) {
	$scope.urlList;
	$scope.jsonData = {longUrl : ''};  
	$scope.urlText = '';
   $scope.getUrlList = function()
   {	
			$http({
					method : "GET",
					url:"http://localhost:8080/api/v1/getUrlList"
				 }).then(function mySuccess(response) {
					$scope.urlList = response.data; 
			}, function myError(response) {
				alert("something went wrong while getting the url list");
			});
	   
   };
   
   $scope.getUrlList();
   
   $scope.shortUrl = function()
   {
		if($scope.urlText == undefined || $scope.urlText.length == 0 || validURL($scope.urlText) == false)
		{
			alert("Please insert valid url in format https://www.google.com");
			return;
		}
		
		$scope.jsonData.longUrl = $scope.urlText;
		$http({
					method : "POST",
					url:"http://localhost:8080/api/v1/shortenUrl",
					data: $scope.jsonData
				 }).then(function mySuccess(response) {
					$scope.urlText = '';
					$scope.jsonData = {longUrl : ''}; 
					alert("Your shorten Url is :http://localhost:8080/api/v1/"+response.data[0]);
					$scope.getUrlList();
			}, function myError(response) {
			console.log(response);
				
			});
		
   };
   
   
   function validURL(str) {
  var pattern = new RegExp('^(https?:\\/\\/)?'+ // protocol
    '((([a-z\\d]([a-z\\d-]*[a-z\\d])*)\\.)+[a-z]{2,}|'+ // domain name
    '((\\d{1,3}\\.){3}\\d{1,3}))'+ // OR ip (v4) address
    '(\\:\\d+)?(\\/[-a-z\\d%_.~+]*)*'+ // port and path
    '(\\?[;&a-z\\d%_.~+=-]*)?'+ // query string
    '(\\#[-a-z\\d_]*)?$','i'); // fragment locator
  return !!pattern.test(str);
}
});