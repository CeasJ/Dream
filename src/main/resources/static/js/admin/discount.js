const app = angular.module("discount-app", []);
app.controller("discount-ctrl", function ($scope, $http) {

  $scope.discounts = [];
  $scope.cates = [];

  $scope.initialize = function () {
    $http.get(`/rest/discount`).then((resp) => {
      $scope.discounts = resp.data;
      $scope.discounts.forEach((count) => {
        count.activeDate = new Date(count.activeDate);
        count.expiredDate = new Date(count.expiredDate);
      });
    });
    $http.get(`/rest/category`).then((resp) => {
      $scope.cates = resp.data;
    });

    $scope.form = {
      active: true,
      activeDate: new Date()
    };
  };

  $scope.initialize();

  $scope.reset = function () {
    $scope.form = {
      active: true,
    };
  };
  $scope.edit = function (count) {
    $scope.form = angular.copy(count);
  };
  $scope.create = function () {
    let discount = angular.copy($scope.form);
    let checkNameDiscount = $scope.discounts.find(dis => dis.name === discount.name);
    if (checkNameDiscount) {
      $("#discountModal").modal("hide");
      toastr.error("Name already exists");
    } else {
      $http
        .post(`/rest/discount`, discount)
        .then((resp) => {
          resp.data.activeDate = new Date(resp.data.activeDate);
          resp.data.expiredDate = new Date(resp.data.expiredDate);
          $scope.discounts.push(resp.data);
          $scope.reset();
          $("#discountModal").modal("hide");
          toastr.success("Create Success");
          setTimeout(() => {
            location.reload();
          }, 1000);
        })
        .catch((err) => {
          if (err.data && err.data.errors) {
            $("#discountModal").modal("hide");
            err.data.errors.forEach(function (error, index) {
              toastr.error(`Error ${index + 1}: ${error}`);
            });
          }
        });
    }
  };
  $scope.update = function () {
    let discount = angular.copy($scope.form);
    let checkNameDiscount = $scope.discounts.find(dis => dis.name === discount.name);
    if (checkNameDiscount) {
      $("#discountModal").modal("hide");
      toastr.error("Name already exists");
    } else {
      $http.put(`/rest/discount/${discount.id}`, discount).then(resp => {
        let index = $scope.discounts.findIndex(p => p.id === discount.id);
        $scope.discounts[index] = discount;
        $scope.reset();
        $("#discountModal").modal("hide");
        toastr.success("Update Success");
        setTimeout(() => {
          location.reload();
        }, 1000);
      }).catch(err => {
        $("#discountModal").modal("hide");
        err.data.errors.forEach(function (error, index) {
          toastr.error(`Error ${index + 1}: ${error}`);
        });
      })
    }
  };
  $scope.delete = function (count) {
    $http.delete(`/rest/discount/${count.id}`).then(resp => {
      let index = $scope.discounts.findIndex(p => p.id === count.id);
      $scope.discounts.splice(index, 1);
      $scope.reset();
      $("#discountModal").modal("hide");
      toastr.success("Delete Success");
      setTimeout(() => {
        location.reload();
      }, 1000);
    }).catch(err => {
      toastr.error("Create Fail");
    })
  };

  $scope.updateApplyDiscount = function (categoryID, discountID, categoryName) {
    let category = {
      id: categoryID,
      id_discount: discountID,
      name: categoryName,
    };
    $http.put(`/rest/category/update/${categoryID}`, category).then(resp => {
      let index = $scope.cates.findIndex((cate) => cate.id === categoryID);
      $scope.cates[index] = category;
      $scope.reset();
      toastr.success("Update Success");
      setTimeout(() => {
        location.reload();
      }, 1000);
    }).catch(err => {
      toastr.error("Update Fail");
    })
  };

  $scope.firstPageDiscount = function () {
    if ($scope.currentPageDiscount !== 1) {
      $scope.currentPageDiscount = 1;
    }
  };

  $scope.lastPageDiscount = function () {
    if ($scope.currentPageDiscount !== $scope.totalPagesDiscount()) {
      $scope.currentPageDiscount = $scope.totalPagesDiscount();
    }
  };

  $scope.getPagerDiscount = function () {
    const totalPages = $scope.totalPagesDiscount(); // Tổng số trang
    const maxPagesToShow = 5; // Số trang tối đa cần hiển thị

    let startPage = 1;
    let endPage = totalPages;

    if (totalPages > maxPagesToShow) {
      const maxPagesBeforeCurrentPage = Math.floor(maxPagesToShow / 2);
      const maxPagesAfterCurrentPage = Math.ceil(maxPagesToShow / 2) - 1;

      if ($scope.currentPageDiscount <= maxPagesBeforeCurrentPage) {
        startPage = 1;
        endPage = maxPagesToShow;
      } else if ($scope.currentPageDiscount + maxPagesAfterCurrentPage >= totalPages) {
        startPage = totalPages - maxPagesToShow + 1;
        endPage = totalPages;
      } else {
        startPage = $scope.currentPageDiscount - maxPagesBeforeCurrentPage;
        endPage = $scope.currentPageDiscount + maxPagesAfterCurrentPage;
      }
    }

    return Array.from({ length: endPage - startPage + 1 }, (_, i) => startPage + i);
  };

  $scope.paginatedListDiscount = function () {
    const begin = ($scope.currentPageDiscount - 1) * $scope.pageSizeDiscount;
    const end = begin + $scope.pageSizeDiscount;
    return $scope.discounts.slice(begin, end);
  };


  $scope.searchDiscount = function () {
    if ($scope.searchText) {
      $http.get("/rest/discount/search?name=" + $scope.searchText)
        .then(function (response) {
          $scope.discounts = response.data;
        })
        .catch(function (error) {
          console.error("Error fetching discounts:", error);
        });
    } else {
      $scope.initialize();
    }
  };

  $scope.$watch('searchText', function (newVal, oldVal) {
    if (newVal !== oldVal) {
      $scope.searchDiscount();
    }
  });
});

document.addEventListener("DOMContentLoaded", function () {
  var notificationIcon = document.querySelector(".notification-icon");
  var notificationDropdown = document.querySelector(".notification-dropdown");

  notificationIcon.addEventListener("click", function () {
    // Toggle the display of the dropdown
    if (notificationDropdown.style.display === "block") {
      notificationDropdown.style.display = "none";
    } else {
      notificationDropdown.style.display = "block";
    }
  });
});