'use strict';

var app = angular.module('bolao.apostaController', []);

app.controller('ApostaListCtrl', ['$scope', '$cookieStore', 'Restangular', 'usuarioService',
    function($scope, $cookieStore, Restangular, usuarioService) {

        // $scope.list = function() {

        var sessaoUsuario = $cookieStore.get('sessaoUsuario');

        if (eval(sessaoUsuario)) {

            var idUsuarioBolao = usuarioService.getBolaoSelecionado();
            
            Restangular.one('apostas').one('usuariobolao', idUsuarioBolao.idUsuarioBolao).one("rodada", 1).get().then(function(boloesUsuario) {
//                        $scope.boloesUsuario = boloesUsuario;
//                        $scope.setBolaoSelecionado(boloesUsuario[0]);

                console.log('deu certo');
            });

        }

        //  };

    }]);