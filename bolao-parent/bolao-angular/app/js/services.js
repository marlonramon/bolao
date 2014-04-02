'use strict';

var services = angular.module('bolao.services', ['ngResource']);

var baseUrl = 'http://localhost:8080/bolao-web/rest';

services.factory('UsersFactory', function ($resource) {
    return $resource(baseUrl + '/users/:id', {id: '@id'}, {});
});