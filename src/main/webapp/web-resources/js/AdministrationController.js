myApp.controller('administrationController', function ($scope, $http) {
    initDataPitcher($scope);

    $scope.userName;

    $scope.initAdministratorHomePage = function(userName){

        console.log(userName);
        $http.get('/phoneBoockUser/'+userName).
                success(function (data) {
                    $scope.administrator = data;

                console.log(data);
                });
    }

});