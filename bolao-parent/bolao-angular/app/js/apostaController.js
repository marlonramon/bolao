'use strict';

var app = angular.module('bolao.apostaController', []);

app.controller('ApostaListCtrl', ['$scope', 'Restangular', 'usuarioService',
    function($scope, Restangular, usuarioService) {

        var sessaoUsuario = $scope.sessaoUsuario;
        var usuarioBolao = usuarioService.getBolaoSelecionado();
        
        if (usuarioBolao) {
            var rodadas = Restangular.one("campeonatos", usuarioBolao.bolao.campeonato.idCampeonato).all("rodadas").getList().$object;
            $scope.rodadas = rodadas;
        } 


        $scope.getApostas = function(rodada) {
            $scope.rodada = rodada;
            if (rodada) {
                $scope.apostas = Restangular.one('apostas').one('usuariobolao', usuarioBolao.idUsuarioBolao).one("rodada", rodada.idRodada).getList().$object;
            } else {
                $scope.apostas = {};
            }
        };
        
        


        $scope.salvar = function() {
            if ($scope.apostas) {
                Restangular.all("apostas").post($scope.apostas).then(function() {
                    $scope.messages = {"message":"Palpites salvos com sucesso."}
                }, function(response){
                    $scope.errors = response.data;
                });
            }
        };
        
        

    }]);