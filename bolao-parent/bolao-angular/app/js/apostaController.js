'use strict';

var app = angular.module('bolao.bolaoController', []);

app.controller('BolaoListCtrl', ['$scope', '$location', 'Restangular',
    function($scope, $location, Restangular) {

        $scope.list = function() {
            
            if (eval($scope.sessaoUsuario)) {

                var idUsuario = $scope.sessaoUsuario.usuario.idUsuario;
                
                if (idUsuario) {
                    Restangular.one('usuarios', idUsuario).one('boloes').get().then(function(boloesUsuario) {
                        $scope.boloesUsuario = boloesUsuario;
                        $scope.setBolaoSelecionado(boloesUsuario[0]);
                    });
                }
            }

        };

    }]);