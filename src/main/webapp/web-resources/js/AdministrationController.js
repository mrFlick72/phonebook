myApp.controller('administrationController', function ($scope, $http) {
    initDataPitcher($scope);

    $scope.initAdministratorHomePage = function(user){
        $http.get('/phoneBoockUser/'+encodeURI(user.userName)).
                success(function (data) {
                    $scope.administrator = data;
                });
    };

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