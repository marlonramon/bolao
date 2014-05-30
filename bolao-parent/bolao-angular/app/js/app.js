'use strict';


// Declare app level module which depends on filters, and services
var app = angular.module('bolao', [
    'ngRoute',
    'ngCookies',
    'restangular',
    'bolao.services',
    'bolao.usuarioController',
    'bolao.campeonatoController'


]);


app.config(['$routeProvider', function($routeProvider) {
        //usuarios
        $routeProvider.when('/usuario-list', {templateUrl: 'partials/usuarios/usuario-list.html', controller: 'UserListCtrl'});        
        $routeProvider.when('/usuario-edit', {templateUrl: 'partials/usuarios/usuario-edit.html', controller: 'UserEditCtrl'});
        $routeProvider.when('/usuario-edit/:id', {templateUrl: 'partials/usuarios/usuario-edit.html', controller: 'UserEditCtrl'});
        $routeProvider.when('/usuario-login', {templateUrl: 'partials/usuarios/usuario-login.html', controller: 'LoginCtrl'});
        
        //campenatos
        $routeProvider.when('/campeonato-list', {templateUrl: 'partials/campeonato/campeonato-list.html', controller: 'CampeonatoListCtrl'});
        $routeProvider.when('/campeonato-edit', {templateUrl: 'partials/campeonato/campeonato-edit.html', controller: 'CampeonatoEditCtrl'});
        $routeProvider.when('/campeonato-edit/:id', {templateUrl: 'partials/campeonato/campeonato-edit.html', controller: 'CampeonatoEditCtrl'});
        
        $routeProvider.otherwise({redirectTo: '/index'});

    }]);


app.config(function(RestangularProvider) {
    
//    var sessaoUsuario = $cookieStore.get('sessaoUsuario');
    
//    RestangularProvider.setDefaultHeaders({'Authorization': '123456789'});

    var baseUrl = 'http://localhost:8080/bolao-web/app';

    RestangularProvider.setBaseUrl(baseUrl);

    RestangularProvider.setErrorInterceptor(function(response) {
        //stopLoading();
        //displayError();
    });

    RestangularProvider.setResponseInterceptor(function(data, operation, what) {
        //stopLoading();
        return data;
    });

    RestangularProvider.setRequestInterceptor(function(elem) {
        //startLoading();
        return elem;
    });

});
