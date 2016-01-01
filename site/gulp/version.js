var gulp = require('gulp');
var git = require('simple-git')('/usr/src/root');
var fs = require('fs');

// update version
gulp.task('update-version', function(){
  git.log(function(err, log){
    var hash = log.latest.hash.substring(1);
    var fileContent = "";
    fileContent +=  ";;; GENERATED BY GULP, DO NOT EDIT\n";
    fileContent += "(ns app.views.version)\n";
    fileContent += "(def version \"" + hash + "\")\n";

    var configFile = 'src/app/views/version.clj';
    fs.writeFileSync(configFile, fileContent);
  });
});
