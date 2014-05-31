'use strict';

var app = angular.module('bolao.bolaoController', []);

app.controller('BolaoListCtrl', ['$scope', '$location', 'Restangular',
    function($scope, $location, Restangular) {

        Restangular.all('boloes').getList().then(function(boloes) {
            $scope.boloes = boloes;
        });

        $scope.edit = function(idUsuario) {
            $location.path('/bolao-edit/' + idUsuario);
        };

        $scope.new = function() {
            $location.path('/bolao-edit');
        };
    }]);

app.controller('BolaoEditCtrl', ['$scope', '$routeParams', '$location', 'Restangular',
    function($scope, $routeParams, $location, Restangular) {

        $scope.bolao = {};

        if ($routeParams.id) {
            Restangular.one('boloes', $routeParams.id).get().then(function(bolao) {
                $scope.bolao = bolao;
            });
        }

        $scope.save = function() {
            
            console.log('descricao: '+$scope.bolao.descricao);
            
            Restangular.all('boloes').post($scope.bolao).then(function() {
                $scope.cancel();
            });
        };

        $scope.cancel = function() {
            $location.path('/bolao-list');
        };

        $scope.delete = function(id) {
            var confirmation = confirm('Confirma a exclusão do Bolão?');
            if (confirmation === true) {
                Restangular.one('boloes', id).remove().then(function() {
                    $scope.cancel();
                });
            }
        };
        
        $scope.campeonatos = Restangular.all("campeonatos").getList().$object;
        
    }]);