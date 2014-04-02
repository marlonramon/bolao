'use strict';

var app = angular.module('bolao.controllers', []);


app.controller('UserListCtrl', ['$scope', 'UsersFactory', '$location',
    function($scope, UsersFactory, $location) {

        var reload = function() {
            console.log('reload');
            $scope.users = UsersFactory.query();
        };

        $scope.editUser = function(userId) {
            $location.path('/user-detail/' + userId);
        };

        $scope.deleteUser = function(userId) {
            console.log('delete');
            UsersFactory.delete({id: userId}, function(){
                reload();                
            }, function(error){
                alert('error: '+error)
            });          
        };

        $scope.createNewUser = function() {
            $location.path('/user-creation');
        };

        reload();

    }]);

app.controller('UserDetailCtrl', ['$scope', '$routeParams', 'UsersFactory', '$location',
    function($scope, $routeParams, UsersFactory, $location) {

        /* callback for ng-click 'updateUser': */
        $scope.updateUser = function() {
            UsersFactory.update($scope.user);
            $location.path('/user-list');
        };

        /* callback for ng-click 'cancel': */
        $scope.cancel = function() {
            $location.path('/user-list');
        };

        $scope.user = UsersFactory.show({id: $routeParams.id});
    }]);

app.controller('UserCreationCtrl', ['$scope', 'UsersFactory', '$location',
    function($scope, UsersFactory, $location) {
    
        /* callback for ng-click 'createNewUser': */
        $scope.createNewUser = function() {
            UsersFactory.save($scope.user, function(){
                $location.path('/user-list');
            }, function(error){                
                $scope.errors = error.data;
            });            
        };
        
     
    }]);