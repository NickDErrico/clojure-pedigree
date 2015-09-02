var event = require('microevent');
var flux = require("flux");
var jquery = require("jquery");

//
var Dispatcher = new flux.Dispatcher();
exports.dispatcher = Dispatcher;

//
function init() {
  var global = require('./global.js');
  var findPersonProcess = require('./find_person_process.js');
  var ParentStore = global.stores.parent;
  event.mixin(ParentStore);

  Dispatcher.register(function(payload){
    switch(payload.eventName) {
    case 'remove-father':
      ParentStore.removeFather();
      ParentStore.trigger('change');
      break;
    case 'remove-mother':
      ParentStore.removeMother();
      ParentStore.trigger('change');
      break;
    case 'select-mother':
      findPersonProcess.selectMother();
      break;
    case 'select-father':
      findPersonProcess.selectFather();
      break;
    }
  });
}
exports.init = init;
