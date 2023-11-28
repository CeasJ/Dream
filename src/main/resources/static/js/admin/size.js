const app = angular.module("size-app", []);
app.controller("size-ctrl", function ($scope, $http, $location) {
  $scope.sizes = [];
  $scope.products = [];
  $scope.productSize = [];

  $scope.intialize = function () {
    $http.get(`/rest/size`).then((resp) => {
      $scope.sizes = resp.data;
    });

    $http.get(`/rest/products`).then((resp) => {
      $scope.products = resp.data;
    });

    $http
      .get(`/rest/productsizes`)
      .then((resp) => {
        $scope.productSize = resp.data;
      })
      .catch((error) => {
        $location.path(`/security/login/unauthorized`);
      });
  };
  $scope.intialize();

$scope.getPrice = function(productId, sizeId) {
       const productSize = $scope.productSize.find(ps => ps.id_product === productId && ps.id_size === sizeId);

       return productSize ? productSize.price : null;
   };

  $scope.size_of = function (id_product, id_size) {
    if ($scope.productSize) {
      return $scope.productSize.find(
        (ur) => ur.id_product === id_product && ur.id_size === id_size
      );
    }
  };

   $scope.getPrice = function(productId, sizeId) {
       const productSize = $scope.productSize.find(ps => ps.id_product === productId && ps.id_size === sizeId);

       return productSize ? productSize.price : null;
   };

  $scope.size_changed = function (id_product, id_size) {
    let productSize = $scope.size_of(id_product, id_size);
    if (productSize) {
      $scope.revoke_size(productSize);
    } else {
      let price = $scope.updateProductSizePrice();
      productSize = { id_product: id_product, id_size: id_size, price: price };
      $scope.grant_size(productSize);
    }
  };

  $scope.grant_size = function (productSize) {
    $scope.id_product = productSize.id_product;

    $scope.id_size = productSize.id_size;

    $("#priceModal").modal("show");

    $scope.selectRadioBasedOnIdSize(productSize.id_size);
  };

  $scope.add = function () {
    let id_product = $scope.id_product;
    let id_size = $scope.id_size;
    let price = $scope.updateProductSizePrice();
    let productSize = {
      id_product: id_product,
      id_size: id_size,
      price: price,
    };
    $http
      .post(`/rest/productsizes`, productSize)
      .then((resp) => {
        $scope.productSize.push(resp.data);
        $("#priceModal").modal("hide");
        toastr.success("Add size success");
        setTimeout(() => {
          location.reload();
        }, 1000);
      })
      .catch((error) => {});
  };

  $scope.showModal = function(id_product){
    $scope.id_product = id_product;
    $("#priceModal").modal("show");
  };

  $scope.updatePriceSize = function(){
    let id_product = $scope.id_product;
    let id_size = $scope.id_size;
    let price = $scope.updateProductSizePrice();

    let productSize = {
      id_product: id_product,
      id_size: id_size,
      price: price,
    };
    $http.put(`/rest/productsizes/${id_product}`,productSize)
    .then((resp) => {
      let index = $scope.productSize.findIndex((product) => product.id === product.id_product);
      $scope.productSize[index] = productSize;
      $("#priceModal").modal("hide");
      toastr.success("Update price size success");
      setTimeout(() => {
        location.reload();
      }, 1000);
    })
    .catch((error) => {});
  };

  $scope.selectSizeProduct = function(id_size){
    return $scope.id_size = id_size;
  };

  $scope.revoke_size = function (productSize) {
    $http
      .delete(`/rest/productsizes/${productSize.id}`, productSize)
      .then((resp) => {
        let index = $scope.productSize.findIndex(
          (product) => product.id == productSize.id_product
        );
        $scope.productSize.splice(index, 1);
        toastr.success("Delete success");
        setTimeout(() => {
          location.reload();
        }, 1000);
      })
      .catch((error) => {});
  };

  $scope.updateProductSizePrice = function () {
    return $scope.price;
  };

  $scope.selectRadioBasedOnIdSize = function (id_size) {
    switch (id_size) {
      case 1:
        $scope.selectedSize = "S";
        $scope.radioBlocked = {
          S: false,
          M: true,
          L: true,
        };
        break;
      case 2:
        $scope.selectedSize = "M";
        $scope.radioBlocked = {
          S: true,
          M: false,
          L: true,
        };
        break;
      case 3:
        $scope.selectedSize = "L";
        $scope.radioBlocked = {
          S: true,
          M: true,
          L: false,
        };
        break;
      default:
        break;
    }
  };
});
