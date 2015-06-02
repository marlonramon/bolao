'use strict';

app.controller('HomeCtrl', ['$scope', '$location', 'Restangular',
    function ($scope, $location, Restangular) {
            
        Restangular.all('campeonatos').getList().then(function(campeonatos) {
            $scope.campeonatos = campeonatos;
        });
        
        
        
    }]);

