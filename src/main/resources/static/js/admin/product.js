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
    let checkNameProduct = $scope.items.find(product => product.name === item.name);
    if(checkNameProduct) {
       $("#myModal").modal("hide");
       toastr.error("Name already exists");
    } else {
    $http
      .post(`/rest/products`, item)
      .then((resp) => {
        resp.data.createDate = new Date(resp.data.createDate);
        $scope.items.push(resp.data);
        $scope.reset();
        $("#myModal").modal("hide");
        toastr.success("Create Success");
        setTimeout(()=>{
         location.reload();
        },1000);
      })
      .catch((err) => { 
        if (err.data && err.data.errors) {
          $("#myModal").modal("hide");
          err.data.errors.forEach(function(error, index) {
            toastr.error(`Error ${index + 1}: ${error}`);
          });
        } 
      });
    }
  };

  $scope.update = function () {
    let item = angular.copy($scope.form);
    $http.put(`/rest/products/${item.id}`, item).then(resp => {
      let index = $scope.items.findIndex(p => p.id == item.id);
      $scope.items[index] = item;
      $scope.reset();
      $("#myModal").modal("hide");
      toastr.success("Create Success");
      setTimeout(()=>{
       location.reload();
      },1000);
    }).catch(err => {
      if (err.data && err.data.errors) {
        $("#myModal").modal("hide");
        err.data.errors.forEach(function(error, index) {
          toastr.error(`Error ${index + 1}: ${error}`);
        });
      } 
    });
  };

  $scope.delete = function (item) {
    $http.delete(`/rest/products/${item.id}`).then(resp => {
      let index = $scope.items.findIndex(p => p.id == item.id);
      $scope.items.splice(index, 1);
      $scope.reset();
      toastr.success("Delete Success");
    }).catch(err => {
      toastr.error("Delete Fail");
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
      .catch((err) => { 
        toastr.error("Select Image Fail");
      });
  };

});