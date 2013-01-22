'use strict';

angular.module('fanedit', []).config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/index', {
		templateUrl : 'ng/index',
		controller : IndexCtrl
	}).otherwise({
		redirectTo : '/index'
	});
} ]);

function IndexCtrl($scope) {
	console.log('Creating login controller');
	// TODO Is this controller needed at all?
}

IndexCtrl.$inject = [ '$scope' ];
