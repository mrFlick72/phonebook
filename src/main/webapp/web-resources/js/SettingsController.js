app.controller('settingsController', function ($scope, $http) {
    $scope.submitForm = function () {
        $http({
            method: 'PUT',
            url: '/contact',
            data: $scope.phoneBoockUser
        });
    };
});