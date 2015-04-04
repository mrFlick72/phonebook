angular.module('elenco-telefonico')
    .controller("reportController",function($scope,$http){
        $scope.printReport = function(user,reportType){
            var url = ['/${build.finalName}/createPhoneBoocDocumentByMail',user,reportType,'service'].join("/");
            $http.get(url);

        };
    });
