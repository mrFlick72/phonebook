myApp.controller('settingsController', function ($scope, $http) {

    $scope.initUserSettingsPage = function(userName){
        $http.get(['/${build.finalName}','phoneBoockUser',userName,'data'].join('/')).
            success(function (data) {
                $scope.phoneBoockUser = data;

                $scope.phoneBoockUser.password='';
            });
    };

    $scope.submitForm = function () {
        $http({
            method: 'PUT',
            url: ['/${build.finalName}','phoneBoockUser',$scope.phoneBoockUser.userName,'data'].join('/'),
            data: $scope.phoneBoockUser
        });
    };
});