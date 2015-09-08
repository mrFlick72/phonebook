var myApp = angular.module('elenco-telefonico',['ui.utils','ui.date']);

function initDataPitcher(scope){
    scope.dateOptions = {
        dateFormat: 'dd/mm/yy'
    };
    scope.dateFormFormatting='dd/MM/yyyy';
}