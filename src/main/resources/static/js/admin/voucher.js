let voucherApp = angular.module("voucher_app", []);

voucherApp.controller("voucher_ctrl", function ($scope, $http, $window) {
  $scope.vouchers = [];
  $scope.status = [];
  $scope.defaultStatus = "";
  $scope.selectedStatus = $scope.defaultStatus;
  $scope.searchText = "";
  // Display all vouchers
  $http.get("/rest/vouchers/all").then(
    function (response) {
      $scope.vouchers = response.data;
    },
    function (error) {
      console.error("Error fetching vouchers:", error);
    }
  );

  // Display voucher status on combobox
  $http.get("/rest/vouchers/voucherstatus/all").then(
    function (response) {
      $scope.status = response.data;
    },
    function (error) {
      console.error("Error fetching voucher status:", error);
    }
  );

  // Filter voucher based on status
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

  // Searching features
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

  //   Delete voucher
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
    id_account: null,
    type:parseInt(1),

    createVoucher() {
    if (parseInt($scope.voucher.id_account) === 1) {
        this.id_account = parseInt(1);  
    } else {
        this.id_account = parseInt(2);
    }

      let voucher = angular.copy(this);
      console.log(voucher);
      $http
      .post(`/rest/vouchers`, voucher)
      .then((resp) => {
        $scope.vouchers.push(resp.data);
        $scope.reset();
        toastr.success("Create Success");
        // setTimeout(() => {
          //   location.reload();
          // }, 1000);
        })
        .catch((err) => {
          toastr.error("Create Fail");
        });
      }
  };

  $scope.editVoucher = function (voucher) {
  
  };

  $scope.updateVoucher = function () {
   
    console.log(voucher.id);
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
        toastr.error("Update Fail");
      });
  };

  $scope.confirmDelete = function () {
    if ($scope.voucherToDelete) {
      $http.delete("/rest/vouchers/" + $scope.voucherToDelete.id).then(
        function (response) {
          // Xóa voucher thành công
          // Không cần thêm bất kỳ hành động nào ở đây nếu bạn chỉ muốn xóa voucher mà không làm gì khác
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
});
