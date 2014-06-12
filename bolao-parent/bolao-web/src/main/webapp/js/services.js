'use strict';

var app = angular.module('bolao.services', ['ngResource','ngCookies']);

app.service('usuarioService',['$cookieStore', function($cookieStore) {
    
  this.setBolaoSelecionado = function(usuarioBolao) {
	  $cookieStore.put('usuarioBolao', usuarioBolao);
  };
  
  this.getBolaoSelecionado = function(){
      return $cookieStore.get('usuarioBolao');
  };
  
  this.setSessaoUsuario = function (sessaoUsuario) {
	  $cookieStore.put('sessaoUsuario', sessaoUsuario);
  };
  
  this.getSessaoUsuario = function () {
      return $cookieStore.get('sessaoUsuario');
  };
  
  this.isUsuarioLogado = function() {
      return $cookieStore.get('sessaoUsuario');
  };
}]);

