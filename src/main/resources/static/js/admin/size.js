const app = angular.module("size-app", []);
app.controller("size-ctrl", function ($scope, $http, $location) {
  $scope.sizes = [];
  $scope.products = [];
  $scope.productsize = [];

  $scope.intialize = function () {
    $http.get(`/rest/size`).then((resp) => {
      $scope.sizes = resp.data;
    })

    $http.get(`/rest/products`).then((resp) => {
      $scope.products = resp.data;
    })

    $http.get(`/rest/productsizes`).then((resp) => {
      $scope.productsize = resp.data;
    })
      .catch((error) => {
        $location.path(`/security/login/unauthoried`);
      });
  };
  $scope.intialize();

  $scope.size_of = function (name, size) {
    if ($scope.productsize) {
      return $scope.productsize.find(
        (ur) => ur.id_product == name.id && ur.id_size == size.id
      );
    }
  };


  $scope.size_changed = function (name, size) {
    let productSize = $scope.size_of(name, size);
    if (productSize) {
      $scope.revoke_size(productSize);
    } else {

      //        productSize = { product: name, size: size};
      productSize = { id_product: name.id, id_size: size.id };
      console.log(productSize);
      $scope.grant_size(productSize);
    }
  };

  $scope.grant_size = function (productSize) {
    $http.post(`/rest/productsizes`, productSize)
      .then((resp) => {
        $scope.productsize.push(resp.data);
        alert("Them Size thành công");
      })
      .catch((error) => {
      });
  };

  $scope.revoke_size = function (productSize) {
    $http.delete(`/rest/productsizes/${productSize.id}`, productSize)
      .then((resp) => {
        let index = $scope.productsize.findIndex((a) => a.id == productSize.id);
        $scope.productsize.splice(index, 1);
        alert("Xoá Size  thanh cong");
      })
      .catch((error) => {
      });
  };
});

document.addEventListener('DOMContentLoaded', function () {
  Promise.all([
    fetch('/rest/size'),
    fetch('/rest/products'),
    fetch('/rest/productsizes')
  ])
    .then(responses => Promise.all(responses.map(response => response.json())))
    .then(data => {
      let sizeTables = document.querySelectorAll('.size-datatable');
      sizeTables.forEach(table => {
        new simpleDatatables.DataTable(table);
      });
    })
    .catch(error => console.error("Error loading data:", error));

  // Các phần logic khác của bạn trong file product.js có thể được viết ở đây
});