'use strict';

app.controller('HomeCtrl', ['$scope', '$location', 'Restangular', 'usuarioService' ,
    function ($scope, $location, Restangular, usuarioService) {
        
        $scope.errors = {};
        
        Restangular.all('campeonatos').getList().then(function(campeonatos) {
            $scope.campeonatos = campeonatos;
        });
        
        Restangular.all('boloes').getList().then(function(boloes) {
            $scope.boloes = boloes;
        });
        
        $scope.participar = function(bolao) {
           var usuarioBolao = {};
           usuarioBolao.usuario = usuarioService.getSessaoUsuario().usuario;
           usuarioBolao.bolao   = bolao;
           
           Restangular.all('usuarioboloes').post(usuarioBolao).then(function() {
                
           });
           
        };
        
    }]);

