'use strict';

var app = angular.module('bolao.services', ['ngResource','ngCookies']);

app.service('usuarioService',['$cookieStore', function($cookieStore) {
  var usuarioBolao= {};
  var sessaoUsuario = {};

  this.setBolaoSelecionado = function(usuarioBolao) {
      this.usuarioBolao = usuarioBolao;
  };
  
  this.getBolaoSelecionado = function(){
      return this.usuarioBolao;
  };
  
  this.setSessaoUsuario = function (sessaoUsuario) {
      this.sessaoUsuario = sessaoUsuario;
  };
  
  this.getSessaoUsuario = function () {
      return this.sessaoUsuario;
  };
  
  this.isUsuarioLogado = function() {
      return $cookieStore.get('sessaoUsuario');
  };
}]);

