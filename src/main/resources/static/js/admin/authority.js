const app = angular.module("authority-app", []);
app.controller("authority-ctrl", function ($scope, $http, $location) {
  $scope.roles = [];
  $scope.admins = [];
  $scope.authories = [];

  $scope.intialize = function () {
    $http.get(`/rest/roles`).then((resp) => {
      $scope.roles = resp.data;
    });


    $http.get(`/rest/profile/admin?admin=true`).then((resp) => {
      $scope.admins = resp.data;
    });

    $http.get(`/rest/authorities?admin=true`).then((resp) => {
      $scope.authories = resp.data;
    })
      .catch((error) => {
        $location.path(`/security/login/unauthoried`);
      });
  };
  $scope.intialize();

  $scope.authority_of = function (acc, role) {
    if ($scope.authories) {
      return $scope.authories.find(
        (ur) => ur.account.username == acc.username && ur.role.id == role.id
      );
    }
  };

  $scope.authority_changed = function (acc, role) {
    let authority = $scope.authority_of(acc, role);
    if (authority) {
      $scope.revoke_authority(authority);
    } else {
      authority = { account: acc, role: role };
      $scope.grant_authority(authority);
    }
  };

  $scope.grant_authority = function (authority) {
    $http
      .post(`/rest/authorities`, authority)
      .then((resp) => {
        $scope.authories.push(resp.data);
        toastr.success("Authorization successful");
      })
      .catch((error) => {
        toastr.error('Authorization Fail');
      });
  };

  $scope.revoke_authority = function (authority) {
    $http
      .delete(`/rest/authorities/${authority.id}`, authority)
      .then((resp) => {
        let index = $scope.authories.findIndex((a) => a.id == authority.id);
        $scope.authories.splice(index, 1);
        toastr.success("Permissions revoked successfully");
      })
      .catch((err) => {
        toastr.error('Permissions revoked Fail');

      });
  };


  $scope.edit = function (acc) {
    $scope.form = angular.copy(acc);
  };

  $scope.clearForm = function () {
    $scope.form = {}
  };

  $scope.saveAccount = function () {
    let account = angular.copy($scope.form);
    let existUsername = $scope.admins.find(function (admin) {
      return admin.username === account.username;
    });
    if (existUsername) {
      alert("Username is exist");
    } else {
      $http
        .post(`/rest/account/add`, account)
        .then(function (response) {
          $scope.clearForm();
          location.href("/admin/authority")
          toastr.success("Create Successful");
        })
        .catch(function (err) {
          toastr.error("Create Fail");
        });
    }
  };

  $scope.selectedImage = null;
  $scope.selectImage = function () {
    document.getElementById("avatar").click();
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
        $scope.form.avatar = resp.data.name;
      })
      .catch((err) => {
        toastr.error("Select image Fail");
      });
  };

  $scope.update = function () {
    let account = angular.copy($scope.form);
    $http
      .put(`/rest/profile/update/${account.id}`, account)
      .then((resp) => {
        let index = $scope.admins.findIndex(
          (a) => a.username === account.username
        );
        $scope.admins[index] = account;
        toastr.success("Update Successful");
        $scope.clearForm();
      })
      .catch((err) => {
        toastr.error("Update Successful");
        console.log(err);
      });
  }
});