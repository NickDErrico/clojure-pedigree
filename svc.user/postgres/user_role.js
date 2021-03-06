'use strict';

var Sequelize = require('sequelize');
const _ = require('lodash');
var sequelize = require('./db.js');

const instanceProps = [
  'id',
  'userId',
  'roleName'
];

module.exports = sequelize.define('userRole', {
  user_id: Sequelize.STRING,
  role_name: Sequelize.STRING
}, {
  getterMethods: {
    userId: function() {
      return this.getDataValue('user_id');
    },
    roleName: function() {
      return this.getDataValue('role_name');
    }
  },

  setterMethods: {
    userId: function(v) {
      this.setDataValue('user_id', v);
    },
    roleName: function(v) {
      this.setDataValue('role_name', v);
    }
  },

  instanceMethods: {
    getData: function(fields) {
      if (fields) {
        return _.pick(this.fields);
      }
      return _.pick(this, instanceProps);
    }
  },

  classMethods: {
    findByUserId: function(userId) {
      return this.findOne({
        where: {
          user_id: userId
        }
      });
    }
  },

  timestamps: false,
  tableName: 'tbl_user_role'
});
