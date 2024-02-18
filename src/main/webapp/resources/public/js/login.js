angular.module('loginApp', ['common', 'spring-security-csrf-token-interceptor'])
    .controller('LoginCtrl', ['$scope', '$http', function ($scope, $http) {

        $scope.onLogin = function () {
            console.log('Giriş yapan kullanıcı adı ' + $scope.vm.username + ' ve şifresi ' + $scope.vm.password);

            $scope.vm.submitted = true;

            if ($scope.form.$invalid) {
                return;
            }

            $scope.login($scope.vm.userName, $scope.vm.password);

        };

    }]);