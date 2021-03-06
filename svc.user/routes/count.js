'use strict';

const router = require('koa-router')();


// Koa handler function
function* countHandler() {
  const logTrace = this.logTrace;
  const User = this.pg.User;

  logTrace.add('info', 'User.count()');
  const count = yield User.count();

  let message = `Found ${count} users`;
  logTrace.add('info', message);

  this.body = {
    success: true,
    message,
    data: count
  };
}

router.get('/count', countHandler);

module.exports = router;
