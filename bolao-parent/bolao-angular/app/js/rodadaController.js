'use strict';

var app = angular.module('bolao.rodadaController', []);

app.controller('RodadaListCtrl', ['$scope', '$location', 'Restangular',
    function($scope, $location, Restangular) {

        Restangular.all('rodadas').getList().then(function(rodadas) {
            $scope.rodadas = rodadas;
        });

        $scope.edit = function(idUsuario) {
            $location.path('/rodada-edit/' + idUsuario);
        };

        $scope.new = function() {
            $location.path('/rodada-edit');
        };
    }]);

app.controller('RodadaEditCtrl', ['$scope', '$routeParams', '$location', 'Restangular',
    function($scope, $routeParams, $location, Restangular) {

        $scope.rodada = {};

        if ($routeParams.id) {
            Restangular.one('rodadas', $routeParams.id).get().then(function(rodada) {
                $scope.rodada = rodada;
            });
        }
        

        $scope.save = function() {
            Restangular.all('rodadas').post($scope.rodada).then(function() {
                $scope.cancel();
            });
        };

        $scope.cancel = function() {
            $location.path('/rodada-list');
        };

        $scope.delete = function(id) {
            var confirmation = confirm('Confirma a exclusão do Rodada?');
            if (confirmation === true) {
                Restangular.one('rodadas', id).remove().then(function() {
                    $scope.cancel();
                });
            }
        };
        
        $scope.campeonatos = Restangular.all("campeonatos").getList().$object;
        
    }]);