myApp.controller('administrationController', function ($scope, $http) {
    initDataPitcher($scope);

    $scope.initAdministratorHomePage = function(user){
        $http.get('/phoneBoockUser/'+user.userName).
                success(function (data) {
                    $scope.administrator = data;
                });
    }

});