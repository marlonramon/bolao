'use strict';

var app = angular.module('bolao.apostaController', []);

app.controller('ApostaListCtrl', ['$scope', 'Restangular', 'usuarioService', '$route',
    function($scope, Restangular, usuarioService, $route) {

        var usuarioBolao = usuarioService.getBolaoSelecionado();

        if (usuarioBolao) {
            Restangular.one("campeonatos", usuarioBolao.bolao.campeonato.idCampeonato).all("rodadas").getList().then(function(rodadas) {
                $scope.rodadas = rodadas;

                for (var i = 0; i < $scope.rodadas.length; i++) { 

                    var r = $scope.rodadas[i];

                    if (r.rodadaAtual) {
                        $scope.getApostas(r);
                        break;
                    }
                }

            });

        }

        $scope.isRodadaAtual = function(numero) {
            return $scope.rodada && numero === $scope.rodada.numero;
        };

        $scope.getApostas = function(rodada) {
            $scope.rodada = rodada;
            if (rodada) {
                $scope.apostas = Restangular.one('apostas').one('usuariobolao', usuarioBolao.idUsuarioBolao).one("rodada", rodada.idRodada).getList().$object;
            } else {
                $scope.apostas = {};
            }
        };

        $scope.salvar = function() {
            $scope.errors = {};
            $scope.messages = {};
            if ($scope.apostas) {
                Restangular.all("apostas").post($scope.apostas).then(function() {
                    $scope.messages = {"message": "Palpites salvos com sucesso."};
                    $route.reload();
                }, function(response) {
                    $scope.errors = response.data;
                });
            }
        };



    }]);