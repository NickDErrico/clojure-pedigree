// Modules
var global;
var FamilyStore;
var FindPersonAction;

function init(opts) {
  global = require('./global.js');
  FamilyStore = global.stores.family;
  FindPersonAction = global.actions.findPerson;

  return this;
}

function addPartner() {
  FindPersonAction.selectPerson({}).then(function(person){
    FamilyStore.addPartner(person);
  });
}

function removePartner(id) {
  FamilyStore.removePartner(id);
}

var action = {
  init: init,
  addPartner: addPartner,
  removePartner: removePartner
};
module.exports = action;