'use strict';

var app = angular.module('bolao.services', ['ngResource', 'ngCookies']);

app.service('usuarioService', ['$cookieStore','Restangular', function($cookieStore, Restangular) {

        this.setBolaoSelecionado = function(usuarioBolao) {
            $cookieStore.put('usuarioBolao', usuarioBolao);
        };

        this.getBolaoSelecionado = function() {
            return $cookieStore.get('usuarioBolao');
        };

        this.setSessaoUsuario = function(sessaoUsuario) {
            $cookieStore.put('sessaoUsuario', sessaoUsuario);
        };

        this.getSessaoUsuario = function() {
            return $cookieStore.get('sessaoUsuario');
        };

        this.isUsuarioLogado = function() {
            return $cookieStore.get('sessaoUsuario');
        };

        this.logout = function() {
            Restangular.all('usuarios/logout').post(getSessaoUsuario().usuario).then(function(data) {
                setSessaoUsuario(null);
                $cookieStore.remove('sessaoUsuario');
            }, function(error) {
                //$scope.sessaoUsuario = null;
                $cookieStore.remove('sessaoUsuario');
                //console.log(error.status);
            });
        };


    }]);

