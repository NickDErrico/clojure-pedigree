'use strict';

const BaobabReactApp = require('BaobabReactApp');

const MainView = require('./view.jsx');

BaobabReactApp.renderMainLayout(MainView, {
  person: null
}, '/person/detail/:personId');
