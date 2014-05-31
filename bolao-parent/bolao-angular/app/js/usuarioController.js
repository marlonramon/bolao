'use strict';

var app = angular.module('bolao.usuarioController', []);


app.controller('UserListCtrl', ['$scope', '$location', 'Restangular',
    function($scope, $location, Restangular) {

        var usuariosRest = Restangular.all('usuarios');

        usuariosRest.getList().then(function(usuarios) {
            $scope.usuarios = usuarios;
        });

        $scope.edit = function(idUsuario) {
            $location.path('/usuario-edit/' + idUsuario);
        };

        $scope.new = function() {
            $location.path('/usuario-edit');
        };
    }]);

app.controller('UserEditCtrl', ['$scope', '$routeParams', '$location', 'Restangular',
    function($scope, $routeParams, $location, Restangular) {

        $scope.usuario = {};

        if ($routeParams.id) {
            Restangular.one('usuarios', $routeParams.id).get().then(function(usuario) {
                $scope.usuario = usuario;
            });
        }

        $scope.save = function() {
            Restangular.all('usuarios').post($scope.usuario).then(function() {
                $scope.cancel();
            });
        };

        $scope.cancel = function() {
            $location.path('/usuario-list');
        };

        $scope.delete = function(id) {
            var confirmation = confirm('Confirma a exclusão do Usuário?');
            if (confirmation === true) {
                Restangular.one('usuarios', id).remove().then(function() {
                    $scope.cancel();
                });
            }
        };
    }]);

app.controller('LoginCtrl', ['$scope', '$cookieStore', 'Restangular',
    function($scope, $cookieStore, Restangular) {

        $scope.sessaoUsuario = $cookieStore.get('sessaoUsuario');

        $scope.login = function() {
            Restangular.all('usuarios/login').post($scope.usuario).then(function(sessaoUsuario) {
                $scope.sessaoUsuario = sessaoUsuario;
                $cookieStore.put('sessaoUsuario', $scope.sessaoUsuario);

                Restangular.setDefaultHeaders({'Authorization': $cookieStore.get('sessaoUsuario').token});

            });
        };
        
        $scope.isAdm = function () {
           return $scope.sessaoUsuario.usuario.perfil === "ADMINISTRADOR";  
        };

        $scope.logout = function() {
            
            console.log('h udhasidsuai');
            
            Restangular.all('usuarios/logout').post($scope.sessaoUsuario.usuario).then(function(data) {
                $scope.sessaoUsuario = null;
                $cookieStore.remove('sessaoUsuario');
            });
        };
    }]);