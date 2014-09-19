myApp.controller('handleFormController', function ($scope, $http) {
    initDataPitcher($scope);

    $http.get('/contacts').
        success(function (data) {
            $scope.contacts = data;
        });

    $scope.openDialog = function () {
        $scope.newContactPopUpForm.$setPristine();
        $scope.newContact = {
            firstName : null,
            lastName : null,
            birth : null,
            telephoneNumber : null
        };

        $("#insertNewAccountDialogForm").modal('show');
    };

    $scope.shwoContactDetails = function(searchId){
        $scope.contactDetails = "";
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
            $http.get('/contacts').
                success(function (data) {
                    $scope.contacts = data;
                    $("#insertNewAccountDialogForm").modal('hide')
                });
        });
    };
});