'use strict';

var app = angular.module('bolao.controllers', []);


app.controller('UserListCtrl', ['$scope', 'UsersFactory', '$location',
    function($scope, UsersFactory, $location) {

        var reload = function() {
            $scope.users = UsersFactory.query();
        };

        $scope.editUser = function(userId) {
            $location.path('/user-detail/' + userId);
        };

        $scope.deleteUser = function(userId) {                        
            UsersFactory.delete({id: userId}, function(){
                reload();                
            }, function(error){
                $scope.errors = error.data;
            });  
        };

        $scope.newUser = function() {
            $location.path('/user-creation');
        };

        reload();

    }]);

app.controller('UserDetailCtrl', ['$scope', '$routeParams', 'UsersFactory', '$location',
    function($scope, $routeParams, UsersFactory, $location) {

        $scope.save = function() {
            UsersFactory.save($scope.user,function(){
                $location.path('/user-list');
            }, function(error){
                $scope.errors = error.data;
            });            
        };

        $scope.cancel = function() {
            $location.path('/user-list');
        };
        
        $scope.user = UsersFactory.get({id: $routeParams.id});
    }]);

app.controller('UserCreationCtrl', ['$scope', 'UsersFactory', '$location',
    function($scope, UsersFactory, $location) {
    
        /* callback for ng-click 'createNewUser': */
        $scope.save = function() {
            UsersFactory.save($scope.user, function(){
                $location.path('/user-list');
            }, function(error){                
                $scope.errors = error.data;
            });            
        };
        
        $scope.cancel = function() {
            $location.path('/user-list');
        };
        
     
    }]);