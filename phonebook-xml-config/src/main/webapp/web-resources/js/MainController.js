angular.module('elenco-telefonico')
    .controller("mainController",function($scope){
        $scope.dateOptions = {
            dateFormat: 'dd/mm/yy'
        };
        $scope.dateFormFormatting='dd/MM/yyyy';
});
