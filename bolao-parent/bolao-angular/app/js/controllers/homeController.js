'use strict';

app.controller('HomeCtrl', ['$scope', '$location', 'Restangular', 'usuarioService',
    function ($scope, $location, Restangular, usuarioService) {

        

        Restangular.all('campeonatos').getList().then(function (campeonatos) {
            $scope.campeonatos = campeonatos;
        });


        if (usuarioService.isUsuarioLogado()) {
            
            var usuario = usuarioService.getSessaoUsuario().usuario;
            
            Restangular.one('boloes').one("usuario", usuario.idUsuario).getList().then(function (boloes) {
                $scope.boloes = boloes;
            });
            
        } else {
            Restangular.all('boloes').getList().then(function (boloes) {
                $scope.boloes = boloes;
            });
        }

        $scope.participar = function (bolao) {
            var usuarioBolao = {};
            usuarioBolao.usuario = usuarioService.getSessaoUsuario().usuario;
            usuarioBolao.bolao = bolao;

            Restangular.all('usuarioboloes').post(usuarioBolao).then(function () {

            });

        };

    }]);

