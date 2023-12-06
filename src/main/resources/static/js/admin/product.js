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
      console.log($scope.items);
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
        toastr.success("Create Success");
      })
      .catch((err) => { 
        toastr.error("Create Fail");
      });
  };

  $scope.update = function () {
    let item = angular.copy($scope.form);
    $http.put(`/rest/products/${item.id}`, item).then(resp => {
      let index = $scope.items.findIndex(p => p.id == item.id);
      $scope.items[index] = item;
      $scope.reset();
      toastr.success("Update Success");
    }).catch(err => {
      toastr.error("Update Fail");
    })
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
// Thêm phương thức để xuất dữ liệu sang Excel
$scope.exportToExcel = function () {
  $http.get('/export/excel')
    .then(function(response) {
    let blob = new Blob([response.data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
          let downloadLink = document.createElement('a');
          downloadLink.href = window.URL.createObjectURL(blob);
          downloadLink.download = 'product.xlsx';
          downloadLink.click();
        toastr.success("Download Success");
    })
    .catch(function(error) {
        console.error('Error exporting to Excel:', error);
    });
};

});