'use strict';

// libs
var koa = require('koa-router')();
var KoaApi = require('pg.koa.api');

// config
var config = require('./config');
var port = config.serverPort;

// context
var postgres = require('./postgres');
var context = {
  pg: postgres
};

// routes
var add = require('./routes/add.js');
koa.use('/add', add.routes(), add.allowedMethods());
var routes = koa.routes();

// create the app
new KoaApi({
  port,
  routes,
  context,
  svcName: 'svc.user'
});
