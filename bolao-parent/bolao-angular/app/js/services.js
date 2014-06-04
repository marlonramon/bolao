'use strict';

var app = angular.module('bolao.services', ['ngResource']);

app.service('usuarioService', function() {
  var usuarioBolao= {};

  this.setBolaoSelecionado = function(usuarioBolao) {
      this.usuarioBolao = usuarioBolao;
  };
  
  this.getBolaoSelecionado = function(){
      return this.usuarioBolao;
  };
});