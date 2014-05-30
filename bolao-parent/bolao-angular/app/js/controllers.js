'use strict';

var app = angular.module('bolao.controllers', []);


app.controller('UserListCtrl', ['$scope', '$location', 'Restangular',
    function($scope, $location, Restangular) {

        var usuariosRest = Restangular.all('usuarios');

        usuariosRest.getList().then(function(usuarios) {
            $scope.usuarios = usuarios;
        });

        $scope.editUser = function(idUsuario) {
            $location.path('/user-detail/' + idUsuario);
        };

        $scope.newUser = function() {
            $location.path('/user-creation');
        };
    }]);

app.controller('UserEditCtrl', ['$scope', '$routeParams', '$location', 'Restangular',
    function($scope, $routeParams, $location, Restangular) {

        var usuariosRest = Restangular.one('usuarios', $routeParams.id);

        usuariosRest.get().then(function(usuario) {
            $scope.usuario = usuario;
        });

        $scope.save = function() {
            Restangular.all('usuarios').post($scope.usuario).then(function() {
                $scope.cancel();
            });
        };

        $scope.cancel = function() {
            $location.path('/user-list');
        };

        $scope.deleteUser = function(idUsuario) {
            var confirmation = confirm('Confirma a exclusão do Usuário?');
            if (confirmation === true) {
                Restangular.one('usuarios', idUsuario).remove().then(function() {
                    $scope.cancel();
                });
            }
        };
    }]);

app.controller('UserCreationCtrl', ['$scope', '$location', 'Restangular',
    function($scope, $location, Restangular) {

        $scope.save = function() {
            Restangular.all('usuarios').post($scope.usuario).then(function() {
                $scope.cancel();
            });
        };

        $scope.cancel = function() {
            $location.path('/user-list');
        };
    }]);

app.controller('LoginCtrl', ['$scope', '$cookieStore', 'Restangular',
    function($scope, $cookieStore, Restangular) {
        
        $scope.sessaoUsuario = $cookieStore.get('sessaoUsuario');
        
        $scope.login = function() {
            Restangular.all('usuarios/login').post($scope.usuario).then(function(sessaoUsuario) {
                $scope.sessaoUsuario = sessaoUsuario;
                $cookieStore.put('sessaoUsuario', $scope.sessaoUsuario);
            });
        };

        $scope.logout = function() {
            Restangular.all('usuarios/logout').post($scope.sessaoUsuario.usuario).then(function(data) {
                $scope.sessaoUsuario = null;
                $cookieStore.remove('sessaoUsuario');
            });
        };
    }]);