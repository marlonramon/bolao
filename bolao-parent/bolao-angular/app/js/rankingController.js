'use strict';

var app = angular.module('bolao.rankingController', []);

app.controller('RankingBolaoCtrl', ['$scope', '$routeParams', '$location', 'Restangular',
    function($scope, $routeParams, $location, Restangular) {

        if ($routeParams.id) {
            Restangular.one('boloes', $routeParams.id).all('ranking/10').getList().then(function(rankingBolao) {
                $scope.rankingBolao = rankingBolao;
            });
        }

    }]);

app.controller('RankingRodadaCtrl', ['$scope', '$routeParams', '$location', 'Restangular',
    function($scope, $routeParams, $location, Restangular) {

        if ($routeParams.id) {
            Restangular.one('rodadas', $routeParams.id).all('ranking/10').getList().then(function(rankingRodada) {
                $scope.rankingRodada = rankingRodada;
            });
        }

    }]);