'use strict';

var app = angular.module('bolao.controllers', []);


app.controller('UserListCtrl', ['$scope', 'UsersFactory', '$location', 'Restangular' ,
    function($scope, UsersFactory, $location, Restangular) {

        var reload = function() {
            $scope.users = Restangular.all('usuarios').getList().$object;
        };

        $scope.editUser = function(userId) {
            $location.path('/user-detail/' + userId);
        };

        $scope.deleteUser = function(userId) {

            var confirmation = confirm('Are you sure you want to delete this user?');
            if (confirmation === true) {
                UsersFactory.delete({id: userId}, function() {
                    reload();
                }, function(error) {
                    $scope.errors = error.data;
                });
            }
        };

        $scope.newUser = function() {
            $location.path('/user-creation');
        };

        reload();

    }]);

app.controller('UserDetailCtrl', ['$scope', '$routeParams', 'UsersFactory', '$location', 'Restangular' ,
    function($scope, $routeParams, UsersFactory, $location, Restangular) {

        $scope.save = function() {
            UsersFactory.save($scope.user, function() {
                $location.path('/user-list');
            }, function(error) {
                $scope.errors = error.data;
            });
        };

        $scope.cancel = function() {
            $location.path('/user-list');
        };

        $scope.user =  Restangular.one('usuarios',$routeParams.id);
                
    }]);

app.controller('UserCreationCtrl', ['$scope', 'UsersFactory', '$location',
    function($scope, UsersFactory, $location) {

        /* callback for ng-click 'createNewUser': */
        $scope.save = function() {
            UsersFactory.save($scope.user, function() {
                $location.path('/user-list');
            }, function(error) {
                $scope.errors = error.data;
            });
        };

        $scope.cancel = function() {
            $location.path('/user-list');
        };


    }]);