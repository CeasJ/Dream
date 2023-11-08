const app = angular.module("product_app", []);
app.controller("product_ctrl", function ($scope, $http) {
  $scope.items = [];
  $scope.cates = [];
  $scope.productsizes = [];
  $scope.form = {
    createDate: new Date(),
    image: "icloud-upload.png",
    active: true,
  };
  $scope.initialize = function () {
    $http.get(`/rest/products`).then((resp) => {
      $scope.items = resp.data;
      $scope.items.forEach((item) => {
        item.createDate = new Date(item.createDate);
      });
    });
    $http.get(`/rest/category`).then((resp) => {
      $scope.cates = resp.data;
    });
    $http.get(`/rest/productsizes`).then((resp) => {
      $scope.productsizes = resp.data;
    });
  };

  $scope.initialize();

  $scope.reset = function () {
    $scope.form = {
      createDate: new Date(),
      image: "icloud-upload.png",
      active: true,
    };
  };
  $scope.edit = function (item) {
    $scope.form = angular.copy(item);
  };
  $scope.create = function () {
    let item = angular.copy($scope.form);
    $http
      .post(`/rest/products`, item)
      .then((resp) => {
        resp.data.createDate = new Date(resp.data.createDate);
        $scope.items.push(resp.data);
        $scope.reset();
        alert("Create Success");
      })
      .catch((err) => { });
  };

  $scope.update = function () {
    let item = angular.copy($scope.form);
    $http.put(`/rest/products/${item.id}`, item).then(resp => {
      let index = $scope.items.findIndex(p => p.id == item.id);
      $scope.items[index] = item;
      $scope.reset();
      alert("Update Success");
    }).catch(err => {
    })
  };

  $scope.delete = function (item) {
    $http.delete(`/rest/products/${item.id}`).then(resp => {
      let index = $scope.items.findIndex(p => p.id == item.id);
      $scope.items.splice(index, 1);
      $scope.reset();
      alert("Delete Success")
    }).catch(err => {
    })
  };
  $scope.selectedImage = null;

  $scope.selectImage = function () {
    document.getElementById("hiddenImageInput").click();
  };
  $scope.imageChanged = function (files) {
    let data = new FormData();
    data.append("file", files[0]);
    $http
      .post(`/rest/upload/img/gallery`, data, {
        transformRequest: angular.identity,
        headers: { "Content-Type": undefined },
      })
      .then((resp) => {
        $scope.form.image = resp.data.name;
      })
      .catch((err) => { });
  };

});

document.addEventListener('DOMContentLoaded', function () {
  Promise.all([
    fetch('/rest/products'),
    fetch('/rest/category'),
    fetch('/rest/productsizes')
  ])
    .then(responses => Promise.all(responses.map(response => response.json())))
    .then(data => {
      let productTables = document.querySelectorAll('.product-datatable');
      productTables.forEach(table => {
        new simpleDatatables.DataTable(table);
      });
    })
    .catch(error => console.error("Error loading data:", error));

  // Các phần logic khác của bạn trong file product.js có thể được viết ở đây
});
