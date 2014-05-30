'use strict';


// Declare app level module which depends on filters, and services
var app = angular.module('bolao', [
    'ngRoute',
    'ngCookies',
    'restangular',
    'bolao.services',
    'bolao.controllers'


]);


app.config(['$routeProvider', function($routeProvider) {
        $routeProvider.when('/user-list', {templateUrl: 'partials/user-list.html', controller: 'UserListCtrl'});
        $routeProvider.when('/user-creation', {templateUrl: 'partials/user-creation.html', controller: 'UserCreationCtrl'});
        $routeProvider.when('/user-detail/:id', {templateUrl: 'partials/user-detail.html', controller: 'UserEditCtrl'});
        $routeProvider.when('/user-login', {templateUrl: 'partials/user-login.html', controller: 'LoginCtrl'});
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
