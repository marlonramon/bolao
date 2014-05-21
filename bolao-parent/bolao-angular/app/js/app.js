'use strict';


// Declare app level module which depends on filters, and services
var app = angular.module('bolao', [
  'ngRoute',
  'ngCookies',
  'restangular',
  'bolao.filters',
  'bolao.services',
  'bolao.directives',
  'bolao.controllers'
  
  
]);


app.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/user-list', {templateUrl: 'partials/user-list.html', controller: 'UserListCtrl'});  
  $routeProvider.when('/user-creation', {templateUrl: 'partials/user-creation.html', controller: 'UserCreationCtrl'});
  $routeProvider.when('/user-detail/:id', {templateUrl: 'partials/user-detail.html', controller: 'UserDetailCtrl'});
  $routeProvider.otherwise({redirectTo: '/user-list'});

}]);


app.config(function(RestangularProvider) {
    RestangularProvider.setDefaultHeaders({'Authorization': 'Basic 123:123'});
    
    var baseUrl = 'http://localhost:8081/bolao-web';
    
    RestangularProvider.setBaseUrl(baseUrl);
    
   
});
