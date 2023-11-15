const app = angular.module("order_app", []);
app.controller("order_ctrl", function ($scope, $http) {
  $scope.listOrders = [];
  $scope.listOrdersConfirmed = [];
  $scope.listOrdersCancelled = [];
  $scope.listOrdersSuccessful = [];
  $scope.listOrderIsShipping = [];
  $scope.orderDetails = {};
  $scope.listOrder = [];
  $scope.status = [];

  $scope.selectedStatusChanged = function () {
    const statusMappings = {
      1: "order",
      2: "order-confirm",
      3: "order-shipping",
      4: "order-success",
      5: "order-cancel",
    };

    Object.values(statusMappings).forEach((id) => {
      document.getElementById(id).style.display = "none";
    });

    const selectedID = statusMappings[$scope.selectedStatus];
    if (selectedID) {
      document.getElementById(selectedID).style.display = "block";
    }
  };

  $scope.selectOrder = function (orderID) {
    this.selectedOrderId = orderID;
    $http
      .get("/detail/" + this.selectedOrderId)
      .then((response) => {
        if (response.data) {
          $scope.listOrder = response.data;
        }
      })
      .catch((error) => {});
  };

  $scope.getSubTotal = function () {
	  let subTotal = 0;
	  angular.forEach($scope.listOrder, function (orderDetail) {
		  subTotal += orderDetail.quantity * orderDetail.price;
		});
		return subTotal;
	};
	
	$scope.getTotal = function () {
		let subTotal = $scope.getSubTotal();
		return subTotal;
	};
	
	$scope.initialize = function () {
	  if (
		$scope.selectedStatus === null ||
		$scope.selectedStatus === undefined ||
		isNaN($scope.selectedStatus) ||
		$scope.selectedStatus === ""
	  ) {
		document.getElementById("order").style.display = "block";
		document.getElementById("order-confirm").style.display = "none";
		document.getElementById("order-shipping").style.display = "none";
		document.getElementById("order-cancel").style.display = "none";
		document.getElementById("order-success").style.display = "none";
	}

    $http.get(`/rest/order`).then((resp) => {
      $scope.listOrders = resp.data;
    });
	
    $http.get(`/rest/order/status`).then((resp) => {
      $scope.status = resp.data;
    });

    $http.get(`/rest/order/confirm`).then((resp) => {
      $scope.listOrdersConfirmed = resp.data;
    });

    $http.get(`/rest/order/cancel`).then((resp) => {
      $scope.listOrdersCancelled = resp.data;
    });

    $http.get(`/rest/order/success`).then((resp) => {
      $scope.listOrdersSuccessful = resp.data;
    });

    $http.get(`/rest/order/ship`).then((resp) => {
      $scope.listOrderIsShipping = resp.data;
    });

  };

  $scope.initialize();

//   $scope.showOrderSection = function(sectionId) {
//     document.getElementById("order").style.display = "none";
//     document.getElementById("order-confirm").style.display = "none";
//     document.getElementById("order-shipping").style.display = "none";
//     document.getElementById("order-cancel").style.display = "none";
//     document.getElementById("order-success").style.display = "none";

//     if (sectionId) {
//         document.getElementById(sectionId).style.display = "block";
//     }
// };


  $scope.updateOrder = {
    confirmOrder(orderID) {
      let orderToUpdate = $scope.listOrders.find(function (order) {
        return order.id === orderID;
      });
      if (orderToUpdate) {
        let updateOrder = angular.copy(orderToUpdate);
        updateOrder.status = 2;
        $http
          .put(`/rest/order/${orderToUpdate.id}`, updateOrder)
          .then((resp) => {
            $scope.initialize();
            $scope.selectedStatusChanged(2);
          })
          .catch((err) => {});
      }
    },

    cancelOrder(orderID) {
      let orderToUpdateWhenOrderConfirm = $scope.listOrdersConfirmed.find(
        function (order) {
          return order.id === orderID;
        }
      );

      let orderUpdateWhenOrder = $scope.listOrders.find(function (order) {
        return order.id === orderID;
      });

      let orderUpdateWhenOrderIsShipping = $scope.listOrderIsShipping.find(
        function (order) {
          return order.id === orderID;
        }
      );

      if (orderToUpdateWhenOrderConfirm) {
        let orderCancel = angular.copy(orderToUpdateWhenOrderConfirm);
        orderCancel.status = 5;
        $http
          .put(
            `/rest/order/cancel/${orderToUpdateWhenOrderConfirm.id}`,
            orderCancel
          )
          .then((resp) => {
            $scope.initialize();
            $scope.selectedStatusChanged(5);
          })
          .catch((err) => {});
        } else if (orderUpdateWhenOrder) {
          let orderCancel = angular.copy(orderUpdateWhenOrder);
          orderCancel.status = 5;
          $http
          .put(`/rest/order/cancel/${orderUpdateWhenOrder.id}`, orderCancel)
          .then((resp) => {
            $scope.initialize();
            $scope.selectedStatusChanged(5);
          })
          .catch((err) => {});
        } else if (orderUpdateWhenOrderIsShipping) {
          let orderCancel = angular.copy(orderUpdateWhenOrderIsShipping);
          orderCancel.status = 5;
          $http
          .put(
            `/rest/order/cancel/${orderUpdateWhenOrderIsShipping.id}`,
            orderCancel
            )
            .then((resp) => {
              $scope.initialize();
              $scope.selectedStatusChanged(5);
            })
            .catch((err) => {});
          }
    },
    resetOrder(orderID) {
      let orderToUpdate = $scope.listOrdersCancelled.find(function (order) {
        return order.id === orderID;
      });
      if (orderToUpdate) {
        let orderReset = angular.copy(orderToUpdate);
        orderReset.status = 2;
        $http
          .put(`/rest/order/reset/${orderToUpdate.id}`, orderReset)
          .then((resp) => {
            $scope.initialize();
            $scope.selectedStatusChanged(2);
          })
          .catch((err) => {});
        }
      },
      successOrder(orderID) {
        let orderToUpdate = $scope.listOrdersConfirmed.find(function (order) {
        return order.id === orderID;
      });
      
      let orderUpdateWhenOrderIsShipping = $scope.listOrderIsShipping.find(
        function (order) {
          return order.id === orderID;
        }
        );
        
        if (orderToUpdate) {
          let orderSuccess = angular.copy(orderToUpdate);
          orderSuccess.status = 4;
          $http
          .put(`/rest/order/success/${orderToUpdate.id}`, orderSuccess)
          .then((resp) => {
            $scope.initialize();
            $scope.selectedStatusChanged(4);
          })
          .catch((err) => {});
        } else if (orderUpdateWhenOrderIsShipping) {
          let orderSuccess = angular.copy(orderUpdateWhenOrderIsShipping);
          orderSuccess.status = 4;
          $http
          .put(
            `/rest/order/success/${orderUpdateWhenOrderIsShipping.id}`,
            orderSuccess
            )
            .then((resp) => {
              $scope.initialize();
              $scope.selectedStatusChanged(4);
            })
            .catch((err) => {});
          }
        },
        shippingOrder(orderID) {
          let orderToUpdate = $scope.listOrdersConfirmed.find(function (order) {
            return order.id === orderID;
          });
          
      if (orderToUpdate) {
        let orderIsShipping = angular.copy(orderToUpdate);
        orderIsShipping.status = 3;
        $http
        .put(`/rest/order/ship/${orderToUpdate.id}`, orderIsShipping)
        .then((resp) => {
          $scope.initialize();
          $scope.selectedStatusChanged(3);
          })
          .catch((err) => {});
      }
    },
  };

  $scope.pagerOrder = {
		page: 0,
		size: 5,
		get paginatedList() {
			var start = this.page * this.size;
			return $scope.listOrders.slice(start, start + this.size);
		},
		get count() {
			return Math.ceil(1.0 * $scope.listOrders.length / this.size);
		},
		first() {
			this.page = 0;
		},
		prev() {
			this.page--;
			if (this.page < 0) {
				this.last();
			}
		},
		next() {
			this.page++;
			if (this.page >= this.count) {
				this.first();
			}
		},
		last() {
			this.page = this.count - 1;
		}
	};

  // page confirm
  $scope.pagerOrderConfirm= {
		page: 0,
		size: 5,
		get paginatedList() {
			var start = this.page * this.size;
			return $scope.listOrdersConfirmed.slice(start, start + this.size);
		},
		get count() {
			return Math.ceil(1.0 * $scope.listOrders.length / this.size);
		},
		first() {
			this.page = 0;
		},
		prev() {
			this.page--;
			if (this.page < 0) {
				this.last();
			}
		},
		next() {
			this.page++;
			if (this.page >= this.count) {
				this.first();
			}
		},
		last() {
			this.page = this.count - 1;
		}
	};
 
  // page ship 
  $scope.pagerOrderShipping = {
		page: 0,
		size: 5,
		get paginatedList() {
			var start = this.page * this.size;
			return $scope.listOrderIsShipping.slice(start, start + this.size);
		},
		get count() {
			return Math.ceil(1.0 * $scope.listOrders.length / this.size);
		},
		first() {
			this.page = 0;
		},
		prev() {
			this.page--;
			if (this.page < 0) {
				this.last();
			}
		},
		next() {
			this.page++;
			if (this.page >= this.count) {
				this.first();
			}
		},
		last() {
			this.page = this.count - 1;
		}
	};

  // page success
  $scope.pagerOrderSuccess = {
		page: 0,
		size: 5,
		get paginatedList() {
			var start = this.page * this.size;
			return $scope.listOrdersSuccessful.slice(start, start + this.size);
		},
		get count() {
			return Math.ceil(1.0 * $scope.listOrders.length / this.size);
		},
		first() {
			this.page = 0;
		},
		prev() {
			this.page--;
			if (this.page < 0) {
				this.last();
			}
		},
		next() {
			this.page++;
			if (this.page >= this.count) {
				this.first();
			}
		},
		last() {
			this.page = this.count - 1;
		}
	};
  //page cancel
  $scope.pagerOrderCancel = {
		page: 0,
		size: 5,
		get paginatedList() {
			var start = this.page * this.size;
			return $scope.listOrdersCancelled.slice(start, start + this.size);
		},
		get count() {
			return Math.ceil(1.0 * $scope.listOrders.length / this.size);
		},
		first() {
			this.page = 0;
		},
		prev() {
			this.page--;
			if (this.page < 0) {
				this.last();
			}
		},
		next() {
			this.page++;
			if (this.page >= this.count) {
				this.first();
			}
		},
		last() {
			this.page = this.count - 1;
		}
	};
});
