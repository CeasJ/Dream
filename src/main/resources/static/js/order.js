const app = angular.module('order_app', []);
app.controller('order_ctrl', function($scope, $http) {
    $scope.listOrders = [];
    $scope.listOrdersConfirmed = [];
    $scope.listOrdersCancelled = [];
    $scope.listOrdersSuccessful = [];
    $scope.listOrderIsShipping = []
	
	$scope.initialize = function() {
		$http.get(`/rest/order`).then(resp => {
			$scope.listOrders = resp.data;
		});

        $http.get(`/rest/order/confirm`).then(resp => {
			$scope.listOrdersConfirmed = resp.data;
		});

        $http.get(`/rest/order/cancel`).then(resp => {
			$scope.listOrdersCancelled = resp.data;
		});

        $http.get(`/rest/order/success`).then(resp => {
			$scope.listOrdersSuccessful = resp.data;
		});

        $http.get(`/rest/order/ship`).then(resp => {
			$scope.listOrderIsShipping = resp.data;
		});
	}

    $scope.initialize();


	$scope.updateOrder = {

		confirmOrder(orderID){
			let orderToUpdate = $scope.listOrders.find(function(order){
				return order.id === orderID;
			})
			if (orderToUpdate){
				let updateOrder = angular.copy(orderToUpdate);
				updateOrder.status = 2;
				$http.put(`/rest/order/${orderToUpdate.id}`, updateOrder).then(resp => {
					$scope.initialize();
				}).catch(err => {
				})
			}
		},

		cancelOrder(orderID){
			let orderToUpdateWhenOrderConfirm = $scope.listOrdersConfirmed.find(function(order){
				return order.id === orderID;			
			})

			let orderUpdateWhenOrder = $scope.listOrders.find(function(order){
				return order.id === orderID;			
			})

			let orderUpdateWhenOrderIsShipping = $scope.listOrderIsShipping.find(function(order){
				return order.id === orderID;			
			})

			if(orderToUpdateWhenOrderConfirm){
				let orderCancel =  angular.copy(orderToUpdateWhenOrderConfirm)
				orderCancel.status = 5;
				$http.put(`/rest/order/cancel/${orderToUpdateWhenOrderConfirm.id}`,orderCancel).then(resp=>{
					$scope.initialize();
				}).catch(err => {
				})
			} else if(orderUpdateWhenOrder){
				let orderCancel =  angular.copy(orderUpdateWhenOrder)
				orderCancel.status = 5;
				$http.put(`/rest/order/cancel/${orderUpdateWhenOrder.id}`,orderCancel).then(resp=>{
					$scope.initialize();
				}).catch(err => {
				})
			} else if(orderUpdateWhenOrderIsShipping){
				let orderCancel =  angular.copy(orderUpdateWhenOrderIsShipping)
				orderCancel.status = 5;
				$http.put(`/rest/order/cancel/${orderUpdateWhenOrderIsShipping.id}`,orderCancel).then(resp=>{
					$scope.initialize();
				}).catch(err => {
				})
			} 
		},
		resetOrder(orderID){
			let orderToUpdate = $scope.listOrdersCancelled.find(function(order){
				return order.id === orderID;			
			})
			if(orderToUpdate){
				let orderReset =  angular.copy(orderToUpdate)
				orderReset.status = 2;
				$http.put(`/rest/order/reset/${orderToUpdate.id}`,orderReset).then(resp=>{
					$scope.initialize();
				}).catch(err => {
				})
			}
		},
		successOrder(orderID){
			let orderToUpdate = $scope.listOrdersConfirmed.find(function(order){
				return order.id === orderID;			
			})

            let orderUpdateWhenOrderIsShipping = $scope.listOrderIsShipping.find(function(order){
				return order.id === orderID;			
			})

			if(orderToUpdate){
				let orderSuccess =  angular.copy(orderToUpdate)
				orderSuccess.status = 4;
				$http.put(`/rest/order/success/${orderToUpdate.id}`,orderSuccess).then(resp=>{
					$scope.initialize();
				}).catch(err => {
				})
			} else if(orderUpdateWhenOrderIsShipping) {
				let orderSuccess =  angular.copy(orderUpdateWhenOrderIsShipping)
				orderSuccess.status = 4;
				$http.put(`/rest/order/success/${orderUpdateWhenOrderIsShipping.id}`,orderSuccess).then(resp=>{
					$scope.initialize();
				}).catch(err => {
				})
			}
		},
        shippingOrder(orderID){
			let orderToUpdate = $scope.listOrdersConfirmed.find(function(order){
				return order.id === orderID;			
			})
          
			if(orderToUpdate){
				let orderIsShipping =  angular.copy(orderToUpdate)
				orderIsShipping.status = 3;
				$http.put(`/rest/order/ship/${orderToUpdate.id}`,orderIsShipping).then(resp=>{
					$scope.initialize();
				}).catch(err => {
				})
			}
		}
	};
});