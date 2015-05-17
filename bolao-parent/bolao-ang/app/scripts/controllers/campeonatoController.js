'use strict';

var app = angular.module('bolao.campeonatoController', []);

app.controller('CampeonatoListCtrl', ['$scope', '$location', 'Restangular',
    function($scope, $location, Restangular) {

        Restangular.all('campeonatos').getList().then(function(campeonatos) {
            $scope.campeonatos = campeonatos;
        });

        $scope.edit = function(idUsuario) {
            $location.path('/campeonato-edit/' + idUsuario);
        };

        $scope.new = function() {
            $location.path('/campeonato-edit');
        };
    }]);

app.controller('CampeonatoEditCtrl', ['$scope', '$routeParams', '$location', 'Restangular',
    function($scope, $routeParams, $location, Restangular) {

        $scope.campeonato = {};

        if ($routeParams.id) {
            Restangular.one('campeonatos', $routeParams.id).get().then(function(campeonato) {
                $scope.campeonato = campeonato;
            });
        }

        $scope.save = function() {
            
            Restangular.all('campeonatos').post($scope.campeonato).then(function() {
                $scope.cancel();
            });
        };

        $scope.cancel = function() {
            $location.path('/campeonato-list');
        };

        $scope.delete = function(id) {
            var confirmation = confirm('Confirma a exclusão do Campeonato?');
            if (confirmation === true) {
                Restangular.one('campeonatos', id).remove().then(function() {
                    $scope.cancel();
                });
            }
        };
    }]);