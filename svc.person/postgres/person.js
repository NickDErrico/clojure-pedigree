var Sequelize = require('sequelize');
var sequelize = require('./db.js');

module.exports = sequelize.define('person', {
  full_name: Sequelize.STRING,
  birth_date: Sequelize.DATE,
  death_date: Sequelize.DATE,
  alive_status: Sequelize.STRING,
  job: Sequelize.STRING,
  address: Sequelize.STRING,
  picture: Sequelize.STRING,
  gender: Sequelize.STRING,
  phone_no: Sequelize.STRING,
  summary: Sequelize.STRING
}, {
  getterMethods: {
    fullName: function() {
      return this.getDataValue('full_name');
    },
    birthDate: function() {
      return this.getDataValue('birth_date');
    },
    deathDate: function() {
      return this.getDataValue('death_date');
    },
    aliveStatus: function() {
      return this.getDataValue('alive_status');
    },
    phoneNo: function() {
      return this.getDataValue('phone_no');
    }
  },

  timestamps: false,
  tableName: 'tbl_person'
});
