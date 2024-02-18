angular.module('kahveciEfendiApp', ['frontendServicesKe', 'spring-security-csrf-token-interceptor'])
    .controller('OrderCtrl', ['$scope', 'ItemService', 'OrderService', 'UserService', 'ReportsService', '$timeout', 
        function ($scope, ItemService, OrderService, UserService, ReportsService, $timeout) {

            $scope.vm = {
            	orderItem: {
            		beverage: 0,
            		additions: [],
            		totalPrice: 0            		
            	},
            	order: {
            		items: []
            	},
            	items: [],
                orderTotal: 0,
                totalPay: 0,
                discountType: '',
                orderResult: null,
                errorMessages: [],
                infoMessages: [],
                reportFields:[],
                reportType: ''
 
            };
            
            loadUserInfo();
            loadItems();
            
            function showErrorMessage(errorMessage) {
                clearMessages();
                $scope.vm.errorMessages.push({description: errorMessage});
            }
            
            function loadUserInfo() {
                UserService.getUserInfo()
                    .then(function (userInfo) {
                        $scope.vm.userName = userInfo.userName;
                        $scope.vm.userId = userInfo.id;
                    },
                    function (errorMessage) {
                        showErrorMessage(errorMessage);
                    });
                
            }
            
            function loadItems() {
            	ItemService.getAllItems()
            		.then(function (data) {
            			$scope.vm.items = data.items;
            			
            			_.each($scope.vm.items, function (item) {
                            item.selected = false;
                        });
            			
            			markAppAsInitialized();
            			
            			if ($scope.vm.items && $scope.vm.items.length == 0) {
                            showInfoMessage("Sonuç bulunamadı.");
                        }
            		},
            		function (errorMessage) {
                        showErrorMessage(errorMessage);
                        markAppAsInitialized();
                    });     		
            }

            function markAppAsInitialized() {
                if ($scope.vm.appReady == undefined) {
                    $scope.vm.appReady = true;
                }
            }
          
            function clearMessages() {
                $scope.vm.errorMessages = [];
                $scope.vm.infoMessages = [];
            }

            function showInfoMessage(infoMessage) {
                $scope.vm.infoMessages = [];
                $scope.vm.infoMessages.push({description: infoMessage});
                $timeout(function () {
                    $scope.vm.infoMessages = [];
                }, 1000);
            }
            
            $scope.showPrice = function () {
            	$scope.vm.orderItem.totalPrice = $scope.vm.orderItem.beverage.price;
            };

            $scope.addToOrderItem = function (item) {
            	
            	if($scope.vm.orderItem.beverage == 0){
            		item.selected = false;
            		return;
            	}
            	
            	var totalPrice = $scope.vm.orderItem.totalPrice;
                
                if (item.selected){
                	$scope.vm.orderItem.additions.push(item);
                	totalPrice = totalPrice + item.price;
                }else{
                	$scope.vm.orderItem.additions.pop();
                	totalPrice = totalPrice - item.price;               	
                }
                
                $scope.vm.orderItem.totalPrice = totalPrice;
                
            };
            
            $scope.addToBasket = function (orderItem) {
            	
            	if ($scope.vm.orderItem.beverage == 0){
            		showInfoMessage("İçecek seçiniz");
            		return;
            	}
            	
            	$scope.vm.order.items.push(orderItem);
            	$scope.vm.orderItem = {
                		beverage: 0,
                		additions: [],
                		totalPrice: 0            		
                	};
            	_.each($scope.vm.items, function (item) {
                    item.selected = false;
                });
            	
            	
            	$scope.vm.orderTotal = $scope.vm.orderTotal + orderItem.totalPrice;
            };
            
            function prepareOrderDto(order) {
                return  orderDTO = {
                            id: 1,
                            user: {
                            	id: $scope.vm.userId,
                            	userName: $scope.vm.userName                      	
                            },
                            orderItems: order.items,
                            date: order.date,
                            time: order.time,
                            totalAmount: $scope.vm.orderTotal,
                            paidAmount: $scope.vm.totalPay
                        };

            }
            
            $scope.saveOrder = function (order) {
            	orderDTO = prepareOrderDto(order);
            	OrderService.save(orderDTO)
	                .then(function (orderResult) {
	                	$scope.vm.orderResult = orderResult.id;
	                	showInfoMessage("Siparişiniz alınmıştır.");
	                },
	                function (errorMessage) {
	                    showErrorMessage(errorMessage);
	                });
	
            }
            
            $scope.calculateDiscount = function (order) {
            	orderDTO = prepareOrderDto(order);

            	OrderService.calculateDiscount(orderDTO)
	                .then(function (discount) {
	                    $scope.vm.totalPay = discount.paidAmount;
	                    $scope.vm.discountType = discount.discountType;
	                },
	                function (errorMessage) {
	                    showErrorMessage(errorMessage);
	                });
            }
            
            $scope.clear = function(){
            	$scope.vm.orderItem = {
            		beverage: 0,
            		additions: [],
            		totalPrice: 0            		
            	};
            	$scope.vm.order.items = [];
            	$scope.vm.orderTotal = 0;
            	$scope.vm.totalPay =  0;
            	$scope.vm.discountType = '';
            	_.each($scope.vm.items, function (item) {
                    item.selected = false;
                });
            }
            
            $scope.newOrder = function() {
            	$scope.vm.orderResult = null;
            	$scope.clear();
            }
            
            $scope.getUserTotalsReport = function () {
            	ReportsService.getUserTotals()
                 .then(function (userTotals) {
                     $scope.vm.reportFields = userTotals.reportItems;
                 },
                 function (errorMessage) {
                     showErrorMessage(errorMessage);
                 });
            }
            
            $scope.getBeverageTotalsReport = function () {
            	ReportsService.getBeverageTotals()
                 .then(function (beverageTotals) {
                     $scope.vm.reportFields = beverageTotals.reportItems;
                 },
                 function (errorMessage) {
                     showErrorMessage(errorMessage);
                 });
            }
            
            $scope.comboChange = function () {
            	var reportType =  $scope.vm.reportType;
            	if (reportType === 'userTotals'){
            		$scope.getUserTotalsReport();
            		return;
            	}
            	if (reportType === 'beverageTotals'){
            		$scope.getBeverageTotalsReport();
            		return;
            	}
            };

            $scope.logout = function () {
                UserService.logout();
            }

        }])
        .directive('errorMessages', function() {
        return {
            restrict: 'E',
            link: function(scope, element, attrs) {
                scope.extraStyles = attrs.extraStyles;
            },
            templateUrl: '/resources/public/partials/error-messages.html'
        }
    });

