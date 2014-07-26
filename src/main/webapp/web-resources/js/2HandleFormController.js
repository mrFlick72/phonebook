function handleFormController($scope,$http) {

    $http.get('/indexWithJson').
        success(function (data) {
            $scope.persone = data;
        });

    $scope.openDialog = function () {
        $("#insertNewAccountDialogForm").modal('show')
        /*
         ngDialog.open({
         template: 'content/insertNewAccountDialogForm',
         controller: 'handleFormController'
         //            className: 'ngdialog-theme-default ngdialog-theme-custom'
         });*/
    };

    $scope.submitForm = function () {
        $http({
            method: 'POST',
            url: '/persona',
            data: $scope.persona
//           headers : { 'Content-Type': 'application/json' }
//           http://www.angularjshub.com/examples/forms/formsubmission/
        }).success(function (data) {
            $scope.persona = "";

            $http.get('/indexWithJson').
                success(function (data) {
                    $scope.persone = data;
                    $("#insertNewAccountDialogForm").modal('hide')
                });
        });
    };
}

function handleMenu($scope,$http) {

    $scope.openDialog = function () {
        $("#insertNewAccountDialogForm").modal('show')
    };
}
