'use strict';

var app = angular.module('bolao.rankingController', []);

app.controller('RankingBolaoCtrl', ['$scope', '$routeParams', '$location', 'Restangular',
    function($scope, $routeParams, $location, Restangular) {
		
        $scope.rankingBolao = {};

        if ($routeParams.id) {
            $scope.rankingBolao = Restangular.one('boloes', $routeParams.id).one('ranking/100').get().$object;
        }

    }]);

app.controller('RankingRodadaCtrl', ['$scope', '$routeParams', '$location', 'Restangular',
    function($scope, $routeParams, $location, Restangular) {

        $scope.rankingRodada = {};

        if ($routeParams.id) {
            $scope.rankingRodada = Restangular.one('rodadas', $routeParams.id).one('ranking/100').get().$object;
        }

    }]);