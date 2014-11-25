myApp.controller('settingsController', function ($scope, $http) {

    $scope.initUserSettingsPage = function(userName){
        $http.get(['/phoneBoockUser',userName,'data'].join('/')).
            success(function (data) {
                $scope.phoneBoockUser = data;
            });
    };

    $scope.submitForm = function () {
        $http({
            method: 'PUT',
            url: ['/phoneBoockUser',$scope.phoneBoockUser.userName,'data'].join('/'),
            data: $scope.phoneBoockUser
        });
    };
});