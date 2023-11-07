const app = angular.module("profile", []);
app.controller("profile_ctrl", function ($scope, $http) {
  $scope.account = {};

  let idString = $("#id_account").text().trim();
  let id_account = parseInt(idString);
  $scope.initialize = function () {
    console.log(id_account);
    $http.get(`/rest/profile/${id_account}`).then(resp => {
      $scope.account = resp.data;
      console.log($scope.account)
    });
  }

  $scope.initialize();

  $scope.updateAccount = function () {
    let account = angular.copy($scope.account);
    if (account.id) {
      $http.put(`/rest/profile/${id_account}`, account).then(resp => {
        $scope.account = angular.copy(account);
        alert("Update success");
        location.href = "/profile";
      }).catch(err => {
      });
    }
  }
  $scope.authenticationPass = function () {
    let oldPassword = document.getElementById("oldPassword").value;
    $http.post(`/rest/profile/authenticate/${id_account}`, { password: oldPassword }).then(resp => {
      if (resp.data) {
        document.getElementById("checkOldPassword").style.display = "none"; // Ẩn section checkOldPassword
        document.getElementById("formConfirmPass").style.display = "block"; // Hiển thị section formConfirmPass
      } else {
        alert("Wrong password ");
      }
    });
  }

  $scope.updatePassword = function () {
    let newPassword = document.getElementById("newPassword").value;
    let confirmNewPassword = document.getElementById("confirmNewPassword").value;
    if (newPassword === confirmNewPassword) {
      $http.put(`/rest/profile/changePassword/${id_account}`, { password: newPassword }).then(resp => {
        location.href = "/updatePasswordSuccess";
      }).catch(err => {
        console.log(err);
      })
    } else {
      alert("Wrong password ");
    }
  }
  $scope.selectedImage = null;

  $scope.selectImage = function () {
    document.getElementById("image").click();
  };
  $scope.imageChanged = function (files) {
    let data = new FormData();
    data.append("file", files[0]);
    $http
      .post(`/rest/upload/img/avatar`, data, {
        headers: { "Content-Type": undefined },
      })
      .then((resp) => {
        $scope.account.avatar = resp.data.name;
      })
      .catch((err) => { });
  };
});


