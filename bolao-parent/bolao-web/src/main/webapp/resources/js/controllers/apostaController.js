'use strict';

var app = angular.module('bolao.apostaController', []);

app.controller('ApostaListCtrl', ['$scope', 'Restangular', 'usuarioService', '$route', 'flash',
    function ($scope, Restangular, usuarioService, $route, flash) {


        Restangular.one('usuarioboloes').one('usuarios', usuarioService.getSessaoUsuario().usuario.idUsuario).getList().then(function (usuarioBoloes) {
            $scope.usuarioBoloes = usuarioBoloes;
            $scope.selecionarBolao(usuarioBoloes[0]);
        });

        $scope.selecionarBolao = function (usuarioBolao) {
            $scope.usuarioBolao = usuarioBolao;
            
            Restangular.one("campeonatos", usuarioBolao.bolao.campeonato.idCampeonato).all("rodadas").getList().then(function (rodadas) {
                $scope.rodadas = rodadas;

                for (var i = 0; i < $scope.rodadas.length; i++) {

                    var r = $scope.rodadas[i];

                    if (r.rodadaAtual) {
                        $scope.buscarApostas(r);
                        break;
                    }
                }

            });



        };



        $scope.isRodadaAtual = function (numero) {
            return $scope.rodada && numero === $scope.rodada.numero;
        };

        $scope.buscarApostas = function (rodada) {
            $scope.rodada = rodada;
            if (rodada) {
                $scope.apostas = Restangular.one('apostas').one('usuariobolao', $scope.usuarioBolao.idUsuarioBolao).one("rodada", rodada.idRodada).getList().$object;
            } else {
                $scope.apostas = {};
            }
        };

        $scope.salvar = function () {
            $scope.errors = {};
            $scope.messages = {};
            if ($scope.apostas) {
                Restangular.all("apostas").post($scope.apostas).then(function () {
                    flash.success = 'Salvo com sucesso';
                    $route.reload();
                }, function (response) {
                    flash.error = response.data.mensagem;
                });
            }
        };
    }]);

app.controller('ApostaFinalizadaListCtrl', ['$scope', 'Restangular', '$routeParams',
    function ($scope, Restangular, $routeParams) {

        if ($routeParams.id) {
            $scope.apostas = Restangular.one("apostas").one("usuariobolao", $routeParams.id).getList().$object;
        }

    }]);