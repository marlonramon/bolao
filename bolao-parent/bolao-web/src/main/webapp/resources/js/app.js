'use strict';

var url = 'http://localhost:8080/bolao-web';

var baseUrl = url + '/app';

var baseUrlImages = url + '/resources/images';

// Declare app level module which depends on filters, and services
var app = angular.module('bolao', [
    'ngRoute',
    'ngCookies',
    'restangular',
    'angular-flash.service',
    'angular-flash.flash-alert-directive',
    'angular-underscore',
    'ngQuickDate',    
    'bolao.usuarioController',
    'bolao.campeonatoController',
    'bolao.bolaoController',
    'bolao.clubeController',
    'bolao.rodadaController',
    'bolao.partidaController',
    'bolao.rankingController',
    'bolao.apostaController',
    'bolao.usuarioBolaoController'

]);


app.config(['$routeProvider', function($routeProvider) {
        //usuarios
        $routeProvider.when('/usuario-list', {templateUrl: 'resources/partials/usuarios/usuario-list.html', controller: 'UserListCtrl'});
        $routeProvider.when('/usuario-edit', {templateUrl: 'resources/partials/usuarios/usuario-edit.html', controller: 'UserEditCtrl'});
        $routeProvider.when('/usuario-edit/:id', {templateUrl: 'resources/partials/usuarios/usuario-edit.html', controller: 'UserEditCtrl'});
        $routeProvider.when('/usuario-login', {templateUrl: 'resources/partials/usuarios/usuario-login.html', controller: 'LoginCtrl'});
        $routeProvider.when('/usuario-create', {templateUrl: 'resources/partials/usuarios/usuario-create.html', controller: 'UserEditCtrl'});

        //campenatos
        $routeProvider.when('/campeonato-list', {templateUrl: 'resources/partials/campeonato/campeonato-list.html', controller: 'CampeonatoListCtrl'});
        $routeProvider.when('/campeonato-edit', {templateUrl: 'resources/partials/campeonato/campeonato-edit.html', controller: 'CampeonatoEditCtrl'});
        $routeProvider.when('/campeonato-edit/:id', {templateUrl: 'resources/partials/campeonato/campeonato-edit.html', controller: 'CampeonatoEditCtrl'});

        //boloes
        $routeProvider.when('/bolao-list', {templateUrl: 'resources/partials/bolao/bolao-list.html', controller: 'BolaoListCtrl'});
        $routeProvider.when('/bolao-edit', {templateUrl: 'resources/partials/bolao/bolao-edit.html', controller: 'BolaoEditCtrl'});
        $routeProvider.when('/bolao-edit/:id', {templateUrl: 'resources/partials/bolao/bolao-edit.html', controller: 'BolaoEditCtrl'});

        //boloes
        $routeProvider.when('/clube-list', {templateUrl: 'resources/partials/clube/clube-list.html', controller: 'ClubeListCtrl'});
        $routeProvider.when('/clube-edit', {templateUrl: 'resources/partials/clube/clube-edit.html', controller: 'ClubeEditCtrl'});
        $routeProvider.when('/clube-edit/:id', {templateUrl: 'resources/partials/clube/clube-edit.html', controller: 'ClubeEditCtrl'});

        //rodadas
        $routeProvider.when('/rodada-list', {templateUrl: 'resources/partials/rodada/rodada-list.html', controller: 'RodadaListCtrl'});
        $routeProvider.when('/rodada-edit', {templateUrl: 'resources/partials/rodada/rodada-edit.html', controller: 'RodadaEditCtrl'});
        $routeProvider.when('/rodada-edit/:id', {templateUrl: 'resources/partials/rodada/rodada-edit.html', controller: 'RodadaEditCtrl'});

        //partidas
        $routeProvider.when('/partida-list', {templateUrl: 'resources/partials/partida/partida-list.html', controller: 'PartidaListCtrl'});
        $routeProvider.when('/partida-edit', {templateUrl: 'resources/partials/partida/partida-edit.html', controller: 'PartidaEditCtrl'});
        $routeProvider.when('/partida-edit/:id', {templateUrl: 'resources/partials/partida/partida-edit.html', controller: 'PartidaEditCtrl'});

        //Aposta
        $routeProvider.when('/aposta-list', {templateUrl: 'resources/partials/aposta/aposta-list.html', controller: 'ApostaListCtrl'});

        $routeProvider.when('/ranking-bolao/:id', {templateUrl:  'resources/partials/ranking/ranking-bolao.html', controller: 'RankingBolaoCtrl'});
        $routeProvider.when('/ranking-rodada/:id', {templateUrl: 'resources/partials/ranking/ranking-rodada.html', controller: 'RankingRodadaCtrl'});

        $routeProvider.when('/usuariobolao-list', {templateUrl: 'resources/partials/usuariobolao/usuariobolao-list.html', controller: 'UsuarioBolaoCtrl'});

        $routeProvider.when('/aposta-finalizada/:id', {templateUrl: 'resources/partials/aposta/aposta-finalizada.html', controller: 'ApostaFinalizadaListCtrl'});

        $routeProvider.when('/index', {templateUrl: 'resources/partials/home.html', controller: 'HomeCtrl'});


        $routeProvider.otherwise({redirectTo: '/index'});

    }]);

app.run(function($rootScope) {
    $rootScope.baseUrlImages = baseUrlImages;
});

app.run(function(Restangular, $cookieStore,$location,flash, usuarioService) {
    Restangular.setBaseUrl(baseUrl);
    
    if (usuarioService.isUsuarioLogado()) {
        Restangular.setDefaultHeaders({'Authorization': usuarioService.getSessaoUsuario().token});
    }

    Restangular.setErrorInterceptor(function(response) {

        flash.error = response.data.mensagem;

        if (response.status === 403) {
            flash.error = response.status + ' ' + response.data.mensagem;
            //$location.path('/index');
            //return false;
        }

        if (response.status === 401) {
            flash.error = response.status + ' ' + response.data.mensagem;
            //$location.path('/index');
            //return false;
        }

        if (response.status === 500) {
            alert('Erro interno no Servidor. Contate o dministrador.');
        }
        
        if (response.status === 404) {
            alert('Erro interno no Servidor. Contate o dministrador.');
        }
        

    });

});

app.run(function($rootScope, $location, usuarioService, $cookieStore,flash) {

    // enumerate routes that don't need authentication
    var routesThatDontRequireAuth = ['/index', '/usuario-login','/usuario-create','/ranking-bolao','/aposta-finalizada'];

    // check if current location matches route  
    var routeClean = function(route) {

        return _.find(routesThatDontRequireAuth,
                function(noAuthRoute) {
                    return _.str.startsWith(route, noAuthRoute);
                });
    };

    $rootScope.$on('$routeChangeStart', function(event, next, current) {
        // if route requires auth and user is not logged in
    	flash.error = null;
        if (!routeClean($location.url()) && !usuarioService.isUsuarioLogado()) {
            // redirect back to login
            $location.path('/index');
        }
    });
});


app.config(function(ngQuickDateDefaultsProvider) {
    return ngQuickDateDefaultsProvider.set({
    });
});

app.config(function(flashProvider) {

    // Support bootstrap 3.0 "alert-danger" class with error flash types
    flashProvider.errorClassnames.push('alert-danger');

    /**
     * Also have...
     *
     * flashProvider.warnClassnames
     * flashProvider.infoClassnames
     * flashProvider.successClassnames
     */

});

app.directive('numberMask', function() {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            $(element).numeric();
        }
    }
});


