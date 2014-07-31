var myApp = angular.module('elenco-telefonico',['ui.utils','ui.date']);

function initDataPitcher(scope){
    scope.dateOptions = {
        dateFormat: 'dd/mm/yy'
    };
    scope.dateFormFormatting='dd/MM/yyyy';
}

myApp.controller('handleFormController', function ($scope, $http) {
    initDataPitcher($scope);

    $http.get('/contacts').
        success(function (data) {
            $scope.contacts = data;
        });

    $scope.openDialog = function () {
        $("#insertNewAccountDialogForm").modal('show');
        $scope.newcontact = "";
    };

    $scope.shwoContactDetails = function(searchId){
        $scope.contact = "";
        $http({
            method: 'GET',
            url: '/contact/'+searchId
        }).success(function (data) {
            $scope.contactDetails = data;
            $("#popUpContactDialog").modal('show');
        });
    };

    $scope.submitForm = function () {
        $http({
            method: 'POST',
            url: '/contact',
            data: $scope.newContact
        }).success(function (data) {
            $scope.contact = "";

            $http.get('/contacts').
                success(function (data) {
                    $scope.contacts = data;
                    $("#insertNewAccountDialogForm").modal('hide')
                });
        });
    };
});

/*myApp.controller('handleMenu', function ($scope, $http) {
    initDataPitcher($scope);

    $scope.openDialog = function () {
        $("#insertNewAccountDialogForm").modal('show')

       console.log("handleMenu")
    };
});*/

