'use strict';

app.factory('usuarioService', ['$http', 'Restangular' , '$window', function ($http, Restangular, $window) {

        var sessionUser;

        return {
            
            login: function (usuario) {

                Restangular.all('usuarios/login').post(usuario).then(function (sessaoUsuario) {

                    Restangular.setDefaultHeaders({'Authorization': sessaoUsuario.token});

                    sessionUser = sessaoUsuario;

                    //$window.sessionStorage["sessaoUsuario"] = sessaoUsuario;
                    $window.localStorage.setItem('sessaoUsuario', JSON.stringify(sessaoUsuario));
                    
                    return sessionUser;

                }, function (response) {


                });


            },
            
            getSessaoUsuario: function () {
                return JSON.parse($window.localStorage.getItem('sessaoUsuario'));
            },
            
            isUsuarioLogado: function () {
                return $window.localStorage.getItem('sessaoUsuario');
            },
            
            logout: function () {
                Restangular.all('usuarios/logout').post(this.getSessaoUsuario().usuario).then(function (data) {
                    //$window.sessionStorage["sessaoUsuario"] = null;
                    
                    $window.localStorage.removeItem('sessaoUsuario');
                }, function (error) {
                    //$window.sessionStorage["sessaoUsuario"] = null;
                    $window.localStorage.removeItem('sessaoUsuario');
                });
            }
        }

    }]);

