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

  // Validation
  $scope.validateProduct = function (form) {
          var isValid = true;

          // Validate Name
          if (!/^[a-zA-Z\s]*$/.test(form.name)) {
              toastr.warning("The product name cannot be empty and should only contain letters!");
              isValid = false;
          }

          // Validate Price
          if (form.price <= 0 || isNaN(form.price)) {
              toastr.warning("The product price must be greater than 0 and can only be a number!");
              isValid = false;
          }

          // Validate Category
          if (!form.id_category || form.id_category === "") {
              toastr.warning("Please select a category!");
              isValid = false;
          }

          return isValid;
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
    if ($scope.validateProduct($scope.form)){
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
    }
  };

  $scope.update = function () {
   if ($scope.validateProduct($scope.form)){
        let item = angular.copy($scope.form);
        $http.put(`/rest/products/${item.id}`, item).then(resp => {
          let index = $scope.items.findIndex(p => p.id == item.id);
          $scope.items[index] = item;
          $scope.reset();
          toastr.success("Update Success");
        }).catch(err => {
          toastr.error("Update Fail");
        })
    }
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

  // Pagination
  $scope.pagedItems = []; // Danh sách sản phẩm được phân trang
      $scope.pagination = {
          currentPage: 1,
          pageSize: 5, // Số sản phẩm trên mỗi trang
          totalPages: 0,
          startIndex: 0,
          endIndex: 0,
          pages: []
      };

      $scope.firstPage = function () {
          if ($scope.pagination.currentPage !== 1) {
              $scope.pagination.currentPage = 1;
              paginateItems();
          }
      };

      $scope.lastPage = function () {
          if ($scope.pagination.currentPage !== $scope.pagination.totalPages) {
              $scope.pagination.currentPage = $scope.pagination.totalPages;
              paginateItems();
          }
      };

     $scope.getPagerNumbers = function () {
         let totalPages = $scope.pagination.totalPages;
         let currentPage = $scope.pagination.currentPage;

         if (totalPages <= 5) {
             return Array.from({ length: totalPages }, (_, i) => i + 1);
         } else {
             let startPage = Math.max(1, currentPage - 2);
             let endPage = Math.min(currentPage + 2, totalPages);

             if (endPage - startPage < 4) {
                 startPage = Math.max(1, endPage - 4);
             }

             return Array.from({ length: 5 }, (_, i) => startPage + i);
         }
     };

      function paginateItems() {
          var begin = (($scope.pagination.currentPage - 1) * $scope.pagination.pageSize);
          var end = begin + $scope.pagination.pageSize;

          $scope.pagedItems = $scope.items.slice(begin, end);
          $scope.pagination.startIndex = begin;
          $scope.pagination.endIndex = end > $scope.items.length ? $scope.items.length : end;

          $scope.pagination.pages = $scope.getPagerNumbers();
      }

      $scope.$watch('items', function () {
          $scope.pagination.totalPages = Math.ceil($scope.items.length / $scope.pagination.pageSize);
          paginateItems();
      });

      $scope.setPage = function (page) {
          if (page < 1 || page > $scope.pagination.totalPages) {
              return;
          }
          $scope.pagination.currentPage = page;
          paginateItems();
      };

      $scope.nextPage = function () {
          if ($scope.pagination.currentPage < $scope.pagination.totalPages) {
              $scope.pagination.currentPage++;
              paginateItems();
          }
      };

      $scope.prevPage = function () {
          if ($scope.pagination.currentPage > 1) {
              $scope.pagination.currentPage--;
              paginateItems();
          }
      };

});