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

app.controller('LoginCtrl', ['$scope', '$cookieStore', 'Restangular', 'usuarioService',
    function($scope, $cookieStore, Restangular, usuarioService) {

        $scope.boloesUsuario = {};
        $scope.sessaoUsuario = $cookieStore.get('sessaoUsuario');

        $scope.login = function() {
            Restangular.all('usuarios/login').post($scope.usuario).then(function(sessaoUsuario) {
                $scope.sessaoUsuario = sessaoUsuario;
                $cookieStore.put('sessaoUsuario', $scope.sessaoUsuario);

                Restangu$scopelar.setDefaultHeaders({'Authorization': $cookieStore.get('sessaoUsuario').token});
                
                $scope.atualizar();                
                
                $('#loginModal').modal('hide');
                
            });
        };

        $scope.atualizar = function() {

            if (eval($scope.sessaoUsuario)) {
                
                var idUsuario = $scope.sessaoUsuario.usuario.idUsuario;
                if (idUsuario) {
                    Restangular.one('usuarios', idUsuario).one('boloes').get().then(function(boloesUsuario) {
                        $scope.boloesUsuario = boloesUsuario;
                        
                        usuarioService.setBolaoSelecionado(boloesUsuario[0]);
                        
                    });
                }
            }
        };

        $scope.atualizar();

        $scope.isAdm = function() {
            var sessaoUsuario = $scope.sessaoUsuario;

            if ($scope.isUsuarioLogado()) {
                return  sessaoUsuario.usuario.perfil === "ADMINISTRADOR";
            } else {
                return false;
            }
        };

        $scope.isUsuarioLogado = function() {
            return $scope.sessaoUsuario;
        };

        $scope.logout = function() {
            Restangular.all('usuarios/logout').post($scope.sessaoUsuario.usuario).then(function(data) {
                $scope.sessaoUsuario = null;
                $cookieStore.remove('sessaoUsuario');
                console.log(data.status);
            }, function(error) {
                $scope.sessaoUsuario = null;
                $cookieStore.remove('sessaoUsuario');
                console.log(error.status);
            });
        };
        
        $scope.setBolaoSelecionado = function (usuarioBolao){
            usuarioService.setBolaoSelecionado(usuarioBolao); 
        };
        
        $scope.getBolaoSelecionado = function (){
            return usuarioService.getBolaoSelecionado();
        };
        
    }]);
