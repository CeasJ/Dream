const app = angular.module("profile", []);
app.controller("profile_ctrl", function ($scope, $http) {
  $scope.account = {};
  $scope.provinces = [];
  $scope.districts = [];
  $scope.wards = [];
  $scope.selectedProvince = "";
  $scope.selectedDistrict = "";
  $scope.selectedWard = "";
  $scope.result = "";

  $http
    .get("https://provinces.open-api.vn/api/?depth=1")
    .then(function (response) {
      $scope.provinces = response.data;
    });

  $scope.getDistricts = function () {
    if ($scope.selectedProvince) {
      $http
        .get(
          "https://provinces.open-api.vn/api/p/" +
          $scope.selectedProvince +
          "?depth=2"
        )
        .then(function (response) {
          $scope.districts = response.data.districts;
        });
    }
  };

  $scope.getWards = function () {
    if ($scope.selectedDistrict) {
      $http
        .get(
          "https://provinces.open-api.vn/api/d/" +
          $scope.selectedDistrict +
          "?depth=2"
        )
        .then(function (response) {
          $scope.wards = response.data.wards;
        });
    }
  };

  $scope.getSelectedProvinces = function (code) {
    code = parseInt(code);
    for (let i = 0; i < $scope.provinces.length; i++) {
      if ($scope.provinces[i].code === code) {
        return $scope.provinces[i].name;
      }
    }
    return "";
  };

  $scope.getSelectedDistricts = function (code) {
    code = parseInt(code);
    for (let i = 0; i < $scope.districts.length; i++) {
      if ($scope.districts[i].code === code) {
        return $scope.districts[i].name;
      }
    }
    return "";
  };

  $scope.getSelectedWards = function (code) {
    code = parseInt(code);
    for (let i = 0; i < $scope.wards.length; i++) {
      if ($scope.wards[i].code === code) {
        return $scope.wards[i].name;
      }
    }
    return "";
  };
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
        .catch((err) => {});
    };
      $scope.printResult = function () {
        if ($scope.selectedProvince && $scope.selectedDistrict && $scope.selectedWard) {
          $scope.account.address =
            $scope.number + "," +
            $scope.getSelectedWards($scope.selectedWard) + "," +
            $scope.getSelectedDistricts($scope.selectedDistrict) + "," +
            $scope.getSelectedProvinces($scope.selectedProvince);
        }
      };
});


