myApp.controller('administrationController', function ($scope, $http,$location) {
    initDataPitcher($scope);

    $scope.initAdministratorHomePage = function(user){
        $http.get('/phoneBoockUser/'+encodeURI(user.userName)).
                success(function (data) {
                    $scope.administrator = data;
                });
    };

    $scope.resetPassword=function(userName){
        console.log('/phoneBoockUser/'+userName+"/password")
        $http.put('/phoneBoockUser/'+userName+"/password");
    };

    $scope.initAdministratorUserList = function(){
        $http.get('/phoneBoockUser').
            success(function (data) {
                $scope.users = data;
            });
    }
});