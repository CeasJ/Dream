var voucherApp = angular.module('voucher_app', []);

voucherApp.controller('voucher_ctrl', function($scope, $http, $window, $timeout) {
    $scope.vouchers = [];
    $scope.status = [];
    $scope.defaultStatus = "";
    $scope.selectedStatus = $scope.defaultStatus;
    $scope.searchText = '';

    // Display all vouchers
    $http.get('/api/vouchers/all')
        .then(function(response) {
            $scope.vouchers = response.data;
        }, function(error) {
            console.error('Error fetching vouchers:', error);
        });

    // Display voucher status on combobox
    $http.get('/api/vouchers/voucherstatus/all')
            .then(function(response) {
                $scope.status = response.data;
            }, function(error) {
                console.error('Error fetching voucher status:', error);
            });

    // Filter voucher based on status
    $scope.filterVouchers = function() {
        if ($scope.selectedStatus === "") {
            $http.get('/api/vouchers/all')
                .then(function(response) {
                    $scope.vouchers = response.data;
                }, function(error) {
                    console.error('Error fetching vouchers:', error);
                });
        } else {
            $http.get('/api/vouchers/filterByStatus/' + $scope.selectedStatus)
                .then(function(response) {
                    $scope.vouchers = response.data;
                }, function(error) {
                    console.error('Error fetching filtered vouchers:', error);
                });
        }
    };

    // Searching features
    $scope.searchVouchers = function() {
        // Gọi API để lấy danh sách vouchers dựa trên giá trị tìm kiếm (searchText)
        $http.get('/api/vouchers/search?name=' + $scope.searchText)
            .then(function(response) {
                $scope.vouchers = response.data;
            }, function(error) {
                console.error('Error fetching vouchers:', error);
            });
    };

//   Delete voucher
$scope.setVoucherToDelete = function(voucher) {
    $scope.voucherToDelete = voucher;
};

    $scope.confirmDelete = function() {
        if ($scope.voucherToDelete) {
            $http.delete('/api/vouchers/' + $scope.voucherToDelete.id)
                .then(function(response) {
                    // Xóa voucher thành công
                    // Không cần thêm bất kỳ hành động nào ở đây nếu bạn chỉ muốn xóa voucher mà không làm gì khác
                }, function(error) {
                    console.error('Error deleting voucher:', error);
                });
        }
    };

    // Delete voucher
     $scope.deleteVoucher = function(voucherId) {
         if ($window.confirm('Bạn có chắc muốn xóa voucher này không?')) {
             $http.delete('/api/vouchers/' + voucherId)
                 .then(function(response) {
                     $scope.vouchers = $scope.vouchers.filter(function(voucher) {
                         return voucher.id !== voucherId;
                     });

                     toastr.success('Xóa voucher thành công');

                     setTimeout(() => {
                       location.reload();
                     }, 1000);

                 })
                 .catch((error) => {});
         }
     };


});
