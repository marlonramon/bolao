'use strict';

var app = angular.module('bolao.partidaController', []);

app.controller('PartidaListCtrl', ['$scope', '$location', 'Restangular',
    function($scope, $location, Restangular) {

        Restangular.all('partidas').getList().then(function(partidas) {
            $scope.partidas = partidas;
        });

        $scope.edit = function(idUsuario) {
            $location.path('/partida-edit/' + idUsuario);
        };

        $scope.new = function() {
            $location.path('/partida-edit');
        };
    }]);

app.controller('PartidaEditCtrl', ['$scope', '$routeParams', '$location', 'Restangular',
    function($scope, $routeParams, $location, Restangular) {

        $scope.partida = {};        
        $scope.rodadas = {};        
        
        if ($routeParams.id) {
            Restangular.one('partidas', $routeParams.id).get().then(function(partida) {
                $scope.campeonatoSelecionado = partida.rodada.campeonato;
                $scope.getRodadas();
                $scope.partida = partida;
            });
        }

        $scope.save = function() {
            
            Restangular.all('partidas').post($scope.partida).then(function() {
                $scope.cancel();
            });
        };

        $scope.cancel = function() {
            $location.path('/partida-list');
        };

        $scope.delete = function(id) {
            var confirmation = confirm('Confirma a exclusão da Partida?');
            if (confirmation === true) {
                Restangular.one('partidas', id).remove().then(function() {
                    $scope.cancel();
                });
            }
        };
        
        $scope.campeonatos = Restangular.all("campeonatos").getList().$object;
        $scope.clubes = Restangular.all("clubes").getList().$object;

        $scope.getRodadas = function(){
            if($scope.campeonatoSelecionado){
                $scope.rodadas = Restangular.one("campeonatos", $scope.campeonatoSelecionado.idCampeonato).all("rodadas").getList().$object;
            }else{
                $scope.rodadas = {};
            }
        };
    }]);