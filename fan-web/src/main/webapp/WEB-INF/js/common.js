'use strict';

if (!"console" in window || typeof console == "undefined") {
    var methods = ["log", "debug", "info", "warn", "error", "assert", "dir", "dirxml", "group", "groupEnd", "time", "timeEnd", "count", "trace", "profile", "profileEnd"];
    var emptyFn = function () {};
    window.console = {};
    for (var i = 0; i < methods.length; ++i) {
        window.console[methods[i]] = emptyFn;
    }
}
