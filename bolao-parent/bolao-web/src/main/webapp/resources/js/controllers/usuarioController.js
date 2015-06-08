'use strict';

var app = angular.module('bolao.usuarioController', []);


app.controller('UserListCtrl', ['$scope', '$location', 'Restangular',
    function ($scope, $location, Restangular) {

        var usuariosRest = Restangular.all('usuarios');

        usuariosRest.getList().then(function (usuarios) {
            $scope.usuarios = usuarios;
        });

        $scope.edit = function (idUsuario) {
            $location.path('/usuario-edit/' + idUsuario);
        };

        $scope.new = function () {
            $location.path('/usuario-edit');
        };
    }]);

app.controller('UserEditCtrl', ['$scope', '$routeParams', '$location', 'Restangular',
    function ($scope, $routeParams, $location, Restangular) {

        $scope.usuario = {};

        if ($routeParams.id) {
            Restangular.one('usuarios', $routeParams.id).get().then(function (usuario) {
                $scope.usuario = usuario;
            });
        }

        $scope.save = function () {
            Restangular.all('usuarios').post($scope.usuario).then(function () {
            	alert('Seu usuário foi cadastrado, favor efetue o login.')
                $location.path('index');
            }, function (response) {
                $scope.errors = response.data;
            });
        };

        $scope.cancel = function () {
            $location.path('index');
        };

        $scope.delete = function (id) {
            var confirmation = confirm('Confirma a exclusão do Usuário?');
            if (confirmation === true) {
                Restangular.one('usuarios', id).remove().then(function () {
                    $scope.cancel();
                });
            }
        };
    }]);

app.controller('LoginCtrl', ['$scope', '$cookieStore', 'Restangular', 'usuarioService', '$route', '$location',
    function ($scope, $cookieStore, Restangular, usuarioService, $route, $location) {

        $scope.boloesUsuario = {};
        $scope.errorsLogin = {};


        $scope.login = function () {
            usuarioService.login($scope.usuario);
        };

        $scope.sessaoUsuario = function () {
            return usuarioService.getSessaoUsuario();
        }

        $scope.entrar = function () {
            $scope.errorsLogin = {};
            $scope.usuario = {};
        };

        $scope.atualizar = function () {
            var sessaoUsuario = usuarioService.getSessaoUsuario();
            if (sessaoUsuario) {
                var idUsuario = sessaoUsuario.usuario.idUsuario;
                if (idUsuario) {
                    Restangular.one('usuarios', idUsuario).one('boloes').get().then(function (boloesUsuario) {
                        $scope.boloesUsuario = boloesUsuario;

                        usuarioService.setBolaoSelecionado(boloesUsuario[0]);


                    });
                }
            }
        };

        $scope.isAdm = function () {
            var sessaoUsuario = usuarioService.getSessaoUsuario();

            if (usuarioService.isUsuarioLogado()) {
                return sessaoUsuario.usuario.admin;
            } else {
                return false;
            }
        };

        $scope.isUsuarioLogado = function () {
            return usuarioService.isUsuarioLogado();
        };

        $scope.logout = function () {
            usuarioService.logout();
            $location.path('/index');
        };

        $scope.setBolaoSelecionado = function (usuarioBolao) {
            usuarioService.setBolaoSelecionado(usuarioBolao);
        };

        $scope.getBolaoSelecionado = function () {
            return usuarioService.getBolaoSelecionado();
        };

        $scope.toViewScore = function (bolao) {        	
            $location.path('/ranking-bolao/' + bolao.idBolao);
        };

        $scope.isActive = function (viewLocation) {
            var active = (viewLocation === $location.path());
            return active;
        };
        
        $scope.loginWithGoogle = function () {
          usuarioService.loginWithGoogle();
        };

    }]);
