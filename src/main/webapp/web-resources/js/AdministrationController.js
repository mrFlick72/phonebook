myApp.controller('administrationController', function ($scope, $http) {
    initDataPitcher($scope);

    $scope.initAdministratorHomePage = function(user){
        $http.get('/phoneBoockUser/'+user.userName).
                success(function (data) {
                    $scope.administrator = data;
                });
    }

    $scope.initAdministratorUserList = function(){
        $http.get('/phoneBoockUsers').
            success(function (data) {
                $scope.users = data;
                console.log(data)
            });
    }
});