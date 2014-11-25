myApp.controller('administrationController', function ($scope, $http) {
    initDataPitcher($scope);

    $scope.resetPassword = function(userName,mail){
        var url = ['/phoneBoockUser',userName,'password'];
        $http.post(url.join('/'),mail);
        $("#createNoncePopup").modal("show");
    };

    $scope.initAdministratorUserList = function(){
        $http.get('/phoneBoockUser').
            success(function (data) {
                $scope.users = data;
            });
    }
});