'use strict';

var app = angular.module('bolao.clubeController', []);

app.controller('ClubeListCtrl', ['$scope', '$location', 'Restangular',
    function($scope, $location, Restangular) {

        Restangular.all('clubes').getList().then(function(clubes) {
            $scope.clubes = clubes;
        });

        $scope.edit = function(idUsuario) {
            $location.path('/clube-edit/' + idUsuario);
        };

        $scope.new = function() {
            $location.path('/clube-edit');
        };
    }]);

app.controller('ClubeEditCtrl', ['$scope', '$routeParams', '$location', 'Restangular',
    function($scope, $routeParams, $location, Restangular) {

        $scope.clube = {};

        if ($routeParams.id) {
            Restangular.one('clubes', $routeParams.id).get().then(function(clube) {
                $scope.clube = clube;
            });
        }

        $scope.save = function() {
            
            console.log('descricao: '+$scope.clube.descricao);
            
            Restangular.all('clubes').post($scope.clube).then(function() {
                $scope.cancel();
            });
        };

        $scope.cancel = function() {
            $location.path('/clube-list');
        };

        $scope.delete = function(id) {
            var confirmation = confirm('Confirma a exclusão do Clube?');
            if (confirmation === true) {
                Restangular.one('clubes', id).remove().then(function() {
                    $scope.cancel();
                });
            }
        };
    }]);