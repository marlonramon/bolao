'use strict';

var app = angular.module('bolao.apostaController', []);

app.controller('ApostaListCtrl', ['$scope', 'Restangular',
    function($scope, Restangular) {

        var sessaoUsuario = $scope.sessaoUsuario;
        
        if (sessaoUsuario) {

            var idUsuario = $scope.sessaoUsuario.usuario.idUsuario;
            
            $scope.usuarioBoloes = Restangular.one('usuarios', idUsuario).one('boloes').getList().$object;
        }

        $scope.getRodadas = function() {
            if ($scope.usuarioBolaoSelecionado) {
                $scope.rodadas = Restangular.one("campeonatos", $scope.usuarioBolaoSelecionado.bolao.campeonato.idCampeonato).all("rodadas").getList().$object;
            } else {
                $scope.rodadas = {};
            }
        };
        
        $scope.getApostas = function(rodada) {
            if (rodada) {
                $scope.apostas = Restangular.one('apostas').one('usuariobolao', $scope.usuarioBolaoSelecionado.idUsuarioBolao).one("rodada", rodada.idRodada).getList().$object;
            } else {
                $scope.apostas = {};
            }
        };
        
        $scope.salvar = function() {
            if($scope.apostas){
                Restangular.all("apostas").post($scope.apostas).then(function(){
                    
                });
            }
        };

    }]);