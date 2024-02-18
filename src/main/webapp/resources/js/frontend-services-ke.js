angular.module('frontendServicesKe', [])
	.service('ItemService', ['$http','$q', function($http, $q) {
        return {
        	getAllItems: function(){
        		var deferred = $q.defer();
        		
        		$http.get('/item')
                .then(function (response) {
                    if (response.status == 200) {
                        deferred.resolve(response.data);
                    }
                    else {
                        deferred.reject('İçecek bilgileri alınamadı');
                    }
                });
        		
        		return deferred.promise;
        	}
        };
    }])
    .service('OrderService', ['$http','$q', function($http, $q) {
        return {
        	save: function(order){
        		var deferred = $q.defer();
        		
        		 $http({
                     method: 'POST',
                     url: '/order',
                     data: order,
                     headers: {
                         "Content-Type": "application/json",
                         "Accept": "text/plain, application/json"
                     }
                 })
                 .then(function (response) {
                     if (response.status == 200) {
                         deferred.resolve(response.data);
                     }
                     else {
                     deferred.reject("Kaydetmede hata: " + response.data);
                     }
                 });

                 return deferred.promise;
        	},
        	
        	calculateDiscount: function(order){
        		var deferred = $q.defer();

        		$http({
                    method: 'PUT',
                    url: '/order',
                    data: order,
                    headers: {
                        "Content-Type": "application/json",
                        "Accept": "text/plain, application/json"
                    }
                })
                .then(function (response) {
                    if (response.status == 200) {
                        deferred.resolve(response.data);
                    }
                    else {
                    deferred.reject("Hesaplamada hata: " + response.data);
                    }
                });
        		
        		return deferred.promise;
        	}
        };
    }])
    .service('UserService', ['$http','$q', function($http, $q) {
        return {
            getUserInfo: function() {
                var deferred = $q.defer();

                $http.get('/user')
                    .then(function (response) {
                        if (response.status == 200) {
                            deferred.resolve(response.data);
                        }
                        else {
                            deferred.reject('Kullanıcı bilgileri alınamadı');
                        }
                });

                return deferred.promise;
            },
            logout: function () {
                $http({
                    method: 'POST',
                    url: '/logout'
                })
                .then(function (response) {
                    if (response.status == 200) {
                    window.location.reload();
                    }
                    else {
                        console.log("Logout failed!");
                    }
                });
            }
        };
    }])
     .service('ReportsService', ['$http','$q', function($http, $q) {
        return {
            getUserTotals: function() {
                var deferred = $q.defer();

                $http.get('/userTotals')
                    .then(function (response) {
                        if (response.status == 200) {
                            deferred.resolve(response.data);
                        }
                        else {
                            deferred.reject('Rapor oluşturulamadı');
                        }
                });

                return deferred.promise;
            },
            getBeverageTotals: function() {
                var deferred = $q.defer();

                $http.get('/beverageTotals')
                    .then(function (response) {
                        if (response.status == 200) {
                            deferred.resolve(response.data);
                        }
                        else {
                            deferred.reject('Rapor oluşturulamadı');
                        }
                });

                return deferred.promise;
            }
            
           
        };
    }]);