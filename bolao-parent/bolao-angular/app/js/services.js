'use strict';

var app = angular.module('bolao.services', ['ngResource']);

app.factory('_', function() {
    return window._; // assumes underscore has already been loaded on the page
});


app.service('usuarioService', function() {
  var usuarioBolao= {};
  var sessaoUsuario = {};

  this.setBolaoSelecionado = function(usuarioBolao) {
      this.usuarioBolao = usuarioBolao;
  };
  
  this.getBolaoSelecionado = function(){
      return this.usuarioBolao;
  };
  
  this.setUsuario = function (sessaoUsuario) {
      this.sessaoUsuario = sessaoUsuario;
  };
  
  this.isUsuarioLogado = function() {
      return this.sessaoUsuario;
  };
});


