const child_process = require("child_process");
const path = require("path");

module.exports = function (ctx) {
  const Q = ctx.requireCordovaModule("q");

  if (!ctx.opts.platforms.includes('ios'))
    return;

  const deferral = new Q.defer();
  console.log("Running manual pod update");

  child_process.exec("pod update", { cwd: path.join(ctx.opts.projectRoot, "platforms/ios/") }, (err, stdout, stderr) => {
    if (err)
    {
      console.log("Pod update failed");
      if (stdout)
        console.log(stdout);
      if (stderr)
        console.log(stderr);
      deferral.reject();
    }
    else {
      deferral.resolve();
    }
  });

  return deferral.promise;
};
