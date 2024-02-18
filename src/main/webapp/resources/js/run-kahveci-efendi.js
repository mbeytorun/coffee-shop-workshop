///////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Defines the javascript files that need to be loaded and their dependencies.
//
///////////////////////////////////////////////////////////////////////////////////////////////////////////

require.config({
    paths: {
        angular: '../bower_components/angular/angular',
        angularMessages: '../bower_components/angular-messages/angular-messages',
        csrfInterceptor: '../bower_components/spring-security-csrf-token-interceptor/dist/spring-security-csrf-token-interceptor.min',
        lodash: "../bower_components/lodash/dist/lodash",
        frontendServicesKe: 'frontend-services-ke',
        kahveciEfendiApp: "app"
    },
    shim: {
        angular: {
            exports: "angular"
        },
        csrfInterceptor: {
            deps: ['angular']
        },
        angularMessages: {
            deps: ['angular']
        },
        frontendServicesKe: {
            deps: ['angular', 'lodash', 'csrfInterceptor']
        },
        kahveciEfendiApp: {
            deps: [ 'lodash', 'angular', 'angularMessages', 'frontendServicesKe']
        }
    }
});

require(['kahveciEfendiApp'], function () {

    angular.bootstrap(document.getElementById('kahveciEfendiApp'), ['kahveciEfendiApp']);

});