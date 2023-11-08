(function ($) {
  "use strict";

  // Spinner
  var spinner = function () {
    setTimeout(function () {
      if ($("#spinner").length > 0) {
        $("#spinner").removeClass("show");
      }
    }, 1);
  };
  spinner();

  new WOW().init();

  // Sticky Navbar
  $(window).scroll(function () {
    if ($(this).scrollTop() > 300) {
      $(".sticky-top").addClass("shadow-sm").css("top", "0px");
    } else {
      $(".sticky-top").removeClass("shadow-sm").css("top", "-150px");
    }
  });

  // Back to top button
  $(window).scroll(function () {
    if ($(this).scrollTop() > 300) {
      $(".back-to-top").fadeIn("slow");
    } else {
      $(".back-to-top").fadeOut("slow");
    }
  });

  // Show .cart-0 if cartCount is less than or equal to 0, otherwise show .cart-1
  var cartCount = parseInt($("#cartCount").text());

  if (cartCount <= 0) {
    $(".cart-0").show();
    $(".cart-1").hide();
  } else {
    $(".cart-0").hide();
    $(".cart-1").show();
  }

  $("#step-1").addClass("active-stext");
  // Show infor-cart and hide cart-0, cart-1 on Buy button click
  $("#buyButton").click(function () {
    $(".infor-cart").show();
    $(".cart-0, .cart-1, .form-buy").hide();

    // Add active class to number-2 and apply animation
    $("#number-2").addClass("active");
    $("#line-1").addClass("active-line");
    $("#step-2").addClass("active-stext");

    // Change color of number-2 to match .step-button.active
    $("#number-2").css({
      "background-color": "var(--primary)", // Màu xanh lá của bạn
      color: "white",
    });
  });

  $("#backButton").click(function () {
    $(".cart-0, .cart-1, .form-buy").show();
    $(".infor-cart").hide();

    // Remove active class from number-2 and remove animation
    $("#number-2").removeClass("active");
    $("#line-1").removeClass("active-line");
    $("#step-2").removeClass("active-stext");
    // Reset color of number-2 to default state
    $("#number-2").css({
      "background-color": "lightgray", // Màu mặc định của number-2
    });

    if (cartCount <= 0) {
      $(".cart-0").show();
      $(".cart-1").hide();
    } else {
      $(".cart-0").hide();
      $(".cart-1").show();
    }
  });

  $(document).ready(function () {
    $("#applyDiscountBtn").click(function () {
      var toast = new bootstrap.Toast(document.getElementById("successToast"));
      toast.show();
    });
  });
})(jQuery);

//Cart Control Begin
const app = angular.module("cart_app", []);

app.controller("ctrl", function ($scope, $http, $timeout) {
  $scope.provinces = [];
  $scope.districts = [];
  $scope.wards = [];
  $scope.selectedProvince = "";
  $scope.selectedDistrict = "";
  $scope.selectedWard = "";
  $scope.result = "";
  $scope.orderDetails = {};
  $scope.listOrder = [];
  $scope.selectedSizeID = "";

  $scope.selectOrder = function (orderID) {
    this.selectedOrderId = orderID;
    $http
      .get("/detail/" + this.selectedOrderId)
      .then((response) => {
        if (response.data) {
          $scope.listOrder = response.data;
        }
      })
      .catch((error) => {
      });
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
    let shippingCost = 20000;
    return subTotal + shippingCost;
  };

  $http
    .get("https://provinces.open-api.vn/api/?depth=1")
    .then(function (response) {
      $scope.provinces = response.data;
    });

  $scope.getDistricts = function () {
    if ($scope.selectedProvince) {
      $http
        .get(
          "https://provinces.open-api.vn/api/p/" +
            $scope.selectedProvince +
            "?depth=2"
        )
        .then(function (response) {
          $scope.districts = response.data.districts;
        });
    }
  };

  $scope.getWards = function () {
    if ($scope.selectedDistrict) {
      $http
        .get(
          "https://provinces.open-api.vn/api/d/" +
            $scope.selectedDistrict +
            "?depth=2"
        )
        .then(function (response) {
          $scope.wards = response.data.wards;
        });
    }
  };

  $scope.getSelectedProvinces = function (code) {
    code = parseInt(code);
    for (let i = 0; i < $scope.provinces.length; i++) {
      if ($scope.provinces[i].code === code) {
        return $scope.provinces[i].name;
      }
    }
    return "";
  };

  $scope.getSelectedDistricts = function (code) {
    code = parseInt(code);
    for (let i = 0; i < $scope.districts.length; i++) {
      if ($scope.districts[i].code === code) {
        return $scope.districts[i].name;
      }
    }
    return "";
  };

  $scope.getSelectedWards = function (code) {
    code = parseInt(code);
    for (let i = 0; i < $scope.wards.length; i++) {
      if ($scope.wards[i].code === code) {
        return $scope.wards[i].name;
      }
    }
    return "";
  };

  $scope.printResult = function () {
    if (
      $scope.selectedProvince &&
      $scope.selectedDistrict &&
      $scope.selectedWard
    ) {
      $scope.order.address =
        $scope.number +
        "," +
        $scope.getSelectedWards($scope.selectedWard) +
        "," +
        $scope.getSelectedDistricts($scope.selectedDistrict) +
        "," +
        $scope.getSelectedProvinces($scope.selectedProvince);
    }
  };

  function getCart(username) {
    const cartKey = `cart_${username}`;
    const json = localStorage.getItem(cartKey);
    return json
      ? JSON.parse(json)
      : {
          username: username,
          items: [],
        };
  }

  function saveCart(username, cart) {
    let cartKey = `cart_${username}`;
    let json = JSON.stringify(cart);
    localStorage.setItem(cartKey, json);
  }

  function totalPrice() {
    let totalPrice = 0;
    angular.forEach($scope.cart.items, function (item) {
      totalPrice += item.priceProduct_Size * item.qty;
    });
    return totalPrice;
  }

  $scope.cart = {
    username: "",

    items: [],

    add(id, size_id) {
      if (!this.items) {
        this.items = [];
      }


      let sizeID = parseInt(size_id);

      if(sizeID === null || sizeID === undefined || isNaN(sizeID)) {
        sizeID = 1;
      }

      let item = this.items.find((item) =>item.id_product === id && item.id_size === sizeID);


      if (item) {
        item.qty++;
        saveCart(this.username, this);
      } else {
        $http.get(`/rest/products/${id}/${sizeID}`).then((resp) => {
          let newItem = resp.data;
          newItem.qty = 1;
          this.items.push(newItem);
          saveCart(this.username, this);
        });
      } 
    },
    remove(id) {
      let index = this.items.findIndex((item) => item.id_product === id);
      this.items.splice(index, 1);
      saveCart(this.username, this);
    },
    clear() {
      this.items = [];
      saveCart(this.username, this);
    },
    get count() {
      if (this.items && this.items.length > 0) {
        return this.items
          .map((item) => item.qty)
          .reduce((total, qty) => (total += qty), 0);
      } else {
        return 0;
      }
    },
    get amount() {
      return totalPrice();
    },
    saveToLocalStorage() {
      let itemsToSave = this.items.map((item) => {
        const { $$hashKey, ...cleanItem } = item;
        return cleanItem;
      });
      saveCart(this.username, itemsToSave);
    },
    loadFromLocalStorage() {
      let cart = getCart(this.username);
      this.items = cart.items;
      $timeout(function () {
        $scope.$apply();
      });
    },

    totalPrice: totalPrice,
  };

  let username = $("#username").text().trim();
  $scope.cart.username = username;
  $scope.cart.loadFromLocalStorage();

  function getCurrentTime() {
    const currentTime = new Date();
    const formattedTime = currentTime.toTimeString().slice(0, 8);
    return formattedTime;
  }

  //Order Begin
  $scope.order = {
    createDate: new Date(),
    address: "",
    id_account: parseInt($("#id_account").text()),
    note: "",
    status: 1,
    totalAmount: $scope.cart.amount,
    createTime: getCurrentTime(),

    get orderDetails() {
      return $scope.cart.items.map((item) => {
        return {
          id_product: parseInt(item.id_product),
          price: item.priceProduct_Size,
          quantity: item.qty,
        };
      });
    },

    purchaseOrder() {
      let order = angular.copy(this);
      $http
        .post(`/rest/order`, order)
        .then((resp) => {
          $scope.cart.clear();
          // location.href = "/order/detail/" + resp.data.id;
        })
        .catch((error) => {});
    },
  };

  $scope.selectedPaymentMethod = "";

  $scope.handlePaymentMethodChange = function () {
    if ($scope.selectedPaymentMethod === "cash") {
      $scope.order.purchaseOrder();
      $scope.completeButtonClicked();
    } else if ($scope.selectedPaymentMethod === "vnpay") {
      $scope.order.purchaseOrder();
      location.href = "/vnpay";
    } else if ($scope.selectedPaymentMethod === "paypal") {
      location.href = "/paypal";
      $scope.completeButtonClicked();
    }
  };


  let isSuccess = true;



  $scope.completeButtonClicked = function () {
    if (isSuccess) {
      $(".cart-3").show();
      $(".cart-0, .cart-1, .form-buy, .infor-cart").hide();
      $("#number-3").addClass("active");
      $("#line-2").addClass("active-line");
      $("#step-3").addClass("active-stext");
    } else {
      alert("Hoàn thành thất bại. Vui lòng thử lại.");
    }
  };
  //Order End
});
//Cart Control End
