let voucherApp = angular.module("voucher_app", []);

voucherApp.controller("voucher_ctrl", function ($scope, $http, $window) {
  $scope.vouchers = [];
  $scope.status = [];
  $scope.types = [];
  $scope.defaultStatus = "";
  $scope.selectedStatus = $scope.defaultStatus;
  $scope.searchText = "";

  $scope.voucher = {
    type: 1,
    status:1,
  };

  $scope.initialize = function () {
  $http.get("/rest/vouchers/all").then(
    function (response) {
      $scope.vouchers = response.data;
    },
    function (error) {
      console.error("Error fetching vouchers:", error);
    }
  );

  $http.get("/rest/vouchers/voucherstatus/all").then(
    function (response) {
      $scope.status = response.data;
    },
    function (error) {
      console.error("Error fetching voucher status:", error);
    }
  );

  $http.get("/rest/vouchers/type/all").then(
    function (response) {
      $scope.types = response.data;
    },
    function (error) {
      console.error("Error fetching voucher status:", error);
    }
  );
};
$scope.initialize();  

  $scope.filterVouchers = function () {
    if ($scope.selectedStatus === "") {
      $http.get("/rest/vouchers/all").then(
        function (response) {
          $scope.vouchers = response.data;
        },
        function (error) {
          console.error("Error fetching vouchers:", error);
        }
      );
    } else {
      $http.get("/rest/vouchers/filterByStatus/" + $scope.selectedStatus).then(
        function (response) {
          $scope.vouchers = response.data;
        },
        function (error) {
          console.error("Error fetching filtered vouchers:", error);
        }
      );
    }
  };

  $scope.searchVouchers = function () {
    // Gọi API để lấy danh sách vouchers dựa trên giá trị tìm kiếm (searchText)
    $http.get("/rest/vouchers/search?name=" + $scope.searchText).then(
      function (response) {
        $scope.vouchers = response.data;
      },
      function (error) {
        console.error("Error fetching vouchers:", error);
      }
    );
  };

  $scope.setVoucherToDelete = function (voucher) {
    $scope.voucherToDelete = voucher;
  };

  $scope.reset = function () {
    $scope.form = {
      icon:"bi bi-cup-straw"
    };
    $("#myModal").show("hide");
    // $scope.selectedIconClass
  };


  $scope.voucher = {
    name:"",
    number:"",
    createDate: new Date(),
    expiredDate:"",
    percent:"",
    condition:"",
    icon:"",
    status:"",
    id_account: "",
    type:"",

    createVoucher() {
      let voucher = angular.copy(this);
      console.log(voucher);
      $http
      .post(`/rest/vouchers`, voucher)
      .then((resp) => {
        $scope.vouchers.push(resp.data);
        $scope.reset();
        toastr.success("Create Success");
        setTimeout(() => {
            location.reload();
          }, 1000);
        })
        .catch((err) => {
          if (err.data && err.data.errors) {
            $("#discountModal").modal("hide");
            err.data.errors.forEach(function(error, index) {
              toastr.error(`Error ${index + 1}: ${error}`);
            });
          } 
        });
      }
  };

  $scope.editVoucher = function (voucher) {
    $scope.voucher = angular.copy(voucher);
    $scope.voucher.expiredDate  = new Date(voucher.expiredDate);
  };

  $scope.updateVoucher = function () {
   let voucher = angular.copy($scope.voucher);
    $http
      .put(`/rest/vouchers/${voucher.id}`, voucher)
      .then((resp) => {
        let index = $scope.vouchers.findIndex((v) => v.id == voucher.id);
        $scope.vouchers[index] = voucher;
        $scope.reset();
        toastr.success("Update Success");
        setTimeout(()=>{
            location.reload();
        },1000);
      })
      .catch((err) => {
        if (err.data && err.data.errors) {
          $("#discountModal").modal("hide");
          err.data.errors.forEach(function(error, index) {
            toastr.error(`Error ${index + 1}: ${error}`);
          });
        } 
      });
  };

  $scope.updateListVoucher = function () {
   let voucher= angular.copy($scope.voucher);
    $http
      .put(`/rest/vouchers/${voucher.name}/${voucher.type}`, voucher)
      .then((resp) => {
        let index = $scope.vouchers.findIndex((v) => v.name === voucher.name && v.type === voucher.type);
        $scope.vouchers[index] = voucher;
        $scope.reset();
        toastr.success("Update Success");
        setTimeout(()=>{
            location.reload();
        },1000);
      })
      .catch((err) => {
        toastr.error("Update Fail");
      });
  };

  $scope.confirmDelete = function () {
    if ($scope.voucherToDelete) {
      $http.delete("/rest/vouchers/" + $scope.voucherToDelete.id).then(
        function (response) {
        },
        function (error) {
          console.error("Error deleting voucher:", error);
        }
      );
    }
  };

  // Delete voucher
  $scope.deleteVoucher = function (voucherId) {
    if ($window.confirm("Bạn có chắc muốn xóa voucher này không?")) {
      $http
        .delete("/rest/vouchers/" + voucherId)
        .then(function (response) {
          $scope.vouchers = $scope.vouchers.filter(function (voucher) {
            return voucher.id !== voucherId;
          });

          toastr.success("Xóa voucher thành công");

          setTimeout(() => {
            location.reload();
          }, 1000);
        })
        .catch((error) => {});
    }
  };

  $scope.deleteListVoucherByNameAndIdType = function (number, idType) {
      $http
        .delete("/rest/vouchers/" + number + "/" + idType)
        .then(function (response) {
          $scope.vouchers = $scope.vouchers.filter(function (voucher) {
            return voucher.number !== number && voucher.type !== type;
          });

          toastr.success("Xóa voucher thành công");

          setTimeout(() => {
            location.reload();
          }, 1000);
        })
        .catch((error) => {});
  };
});
