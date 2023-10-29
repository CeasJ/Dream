const app = angular.module('app', []);

app.controller('ctrl', function($scope, $http) {
	let username = $("#username").text().trim();
    $scope.account = {};  // Khởi tạo biến account

    $scope.updateAccount = function() {
        // Tạo một bản sao của dữ liệu tài khoản để tránh thay đổi gốc
        let updatedAccount = angular.copy($scope.account);

        $http.put(`/api/profile/${updatedAccount.username}`, updatedAccount)
            .then(function(response) {
                // Xử lý thành công
                $scope.account = angular.copy(updatedAccount);  // Cập nhật biến $scope.account
                alert("Cập nhật thành công");
            })
            .catch(function(error) {
                alert("Cập nhật thất bại");
            });
    }
});
