'use strict';

var app = angular.module('bolao.services', ['ngResource', 'ngCookies']);

app.factory('usuarioService', ['Restangular','$http', '$window', function($http,  Restangular, $window) {
        
        

        function login(usuario) {
          
            Restangular.all('usuarios/login').post($scope.usuario).then(function (sessaoUsuario) {                
                
                Restangular.setDefaultHeaders({'Authorization': sessaoUsuario.token});
                
                $window.sessionStorage["sessaoUsuario"] = sessaoUsuario;
        
            }, function (response) {
        
                
            });
        
            
        };


        function getSessaoUsuario () {
            return $window.sessionStorage["sessaoUsuario"];
        };

        function isUsuarioLogado() {
            return $window.sessionStorage["sessaoUsuario"];
        };

        function logout() {
            Restangular.all('usuarios/logout').post(getSessaoUsuario().usuario).then(function(data) {
                $window.sessionStorage["sessaoUsuario"] = null;                
            }, function(error) {
                $window.sessionStorage["sessaoUsuario"] = null;                
            });
        };


    }]);

