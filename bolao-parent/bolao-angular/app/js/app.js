'use strict';


// Declare app level module which depends on filters, and services
angular.module('bolao', [
  'ngRoute',
  'bolao.filters',
  'bolao.services',
  'bolao.directives',
  'bolao.controllers'
])
.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/user-list', {templateUrl: 'partials/user-list.html', controller: 'UserListCtrl'});  
  $routeProvider.when('/user-creation', {templateUrl: 'partials/user-creation.html', controller: 'UserCreationCtrl'});
  $routeProvider.when('/user-detail/:id', {templateUrl: 'partials/user-detail.html', controller: 'UserDetailCtrl'});
  $routeProvider.otherwise({redirectTo: '/user-list'});
 

}]);
