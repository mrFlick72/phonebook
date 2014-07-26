var myApp = angular.module('elenco-telefonico',['ui.utils','ui.date']);

function initDataPitcher(scope){
    scope.dateOptions = {
        dateFormat: 'dd/mm/yy'
    };
    scope.dateFormFormatting='dd/MM/yyyy';
}

myApp.controller('handleFormController', function ($scope, $http) {
    initDataPitcher($scope);

    $http.get('/persone').
        success(function (data) {
            $scope.persone = data;
        });

    $scope.openDialog = function () {
        $("#insertNewAccountDialogForm").modal('show');
        $scope.persona = "";
    };

    $scope.shwoContactDetails = function(searchId){
        $scope.contact = "";
        $http({
            method: 'GET',
            url: '/persona/'+searchId
        }).success(function (data) {
            $scope.contact = data;
            $("#popUpContactDialog").modal('show');
        });
    };

    $scope.submitForm = function () {
        $http({
            method: 'POST',
            url: '/persona',
            data: $scope.persona
        }).success(function (data) {
            $scope.persona = "";

            $http.get('/persone').
                success(function (data) {
                    $scope.persone = data;
                    $("#insertNewAccountDialogForm").modal('hide')
                });
        });
    };
});

myApp.controller('handleMenu', function ($scope, $http) {
    initDataPitcher($scope);

    $scope.openDialog = function () {
        $("#insertNewAccountDialogForm").modal('show')

       console.log("handleMenu")
    };
});

