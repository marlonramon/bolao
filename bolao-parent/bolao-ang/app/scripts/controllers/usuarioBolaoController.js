'use strict';

var app = angular.module('bolao.usuarioBolaoController', []);

app.controller('UsuarioBolaoCtrl', ['$scope', '$route', 'Restangular',
    function($scope, $route, Restangular) {

        var idUsuario = $scope.sessaoUsuario.usuario.idUsuario;

        Restangular.all('usuarioboloes').one('usuarios', idUsuario).getList().then(function(usuarioBoloes) {
            $scope.usuarioBoloes = usuarioBoloes;
        });

        $scope.vincular = function(usuariobolao) {
            Restangular.all('usuarioboloes').post(usuariobolao).then(function() {
                $route.reload();
            });
        };
        
        $scope.delete = function(id) {
            var confirmation = confirm('Confirma a saída do Bolão?'+id);
            if (confirmation === true) {
                Restangular.one('usuarioboloes', id).remove().then(function() {
                    $route.reload();
                });
            }
        };
    }]);