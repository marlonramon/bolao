'use strict';

var app = angular.module('bolao.apostaController', []);

app.controller('ApostaListCtrl', ['$scope', 'Restangular', 'usuarioService',
    function($scope, Restangular, usuarioService) {

        var sessaoUsuario = $scope.sessaoUsuario;
        var usuarioBolao = usuarioService.getBolaoSelecionado();

//        if (sessaoUsuario) {
//
//            var idUsuario = $scope.sessaoUsuario.usuario.idUsuario;
//            
//            $scope.usuarioBoloes = Restangular.one('usuarios', idUsuario).one('boloes').getList().$object;
//        }


        console.log('usuarioBolao ' + usuarioBolao);
        if (usuarioBolao) {
            $scope.rodadas = Restangular.one("campeonatos", usuarioBolao.bolao.campeonato.idCampeonato).all("rodadas").getList().$object;
        } else {
            $scope.rodadas = {};
        }


        $scope.getApostas = function(rodada) {
            if (rodada) {
                $scope.apostas = Restangular.one('apostas').one('usuariobolao', usuarioBolao.idUsuarioBolao).one("rodada", rodada.idRodada).getList().$object;
            } else {
                $scope.apostas = {};
            }
        };

        $scope.salvar = function() {
            if ($scope.apostas) {
                Restangular.all("apostas").post($scope.apostas).then(function() {

                });
            }
        };

    }]);