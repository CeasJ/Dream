const app = angular.module("authority-app", []);
app.controller("authority-ctrl", function ($scope, $http, $location) {
  $scope.roles = [];
  $scope.admins = [];
  $scope.authories = [];

  $scope.intialize = function () {
    $http.get(`/rest/roles`).then((resp) => {
      $scope.roles = resp.data;
    });


    $http.get(`/rest/account/admin?admin=true`).then((resp) => {
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
        alert("Cap quyen thanh cong");
      })
      .catch((error) => {
      });
  };

  $scope.revoke_authority = function (authority) {
    $http
      .delete(`/rest/authorities/${authority.id}`, authority)
      .then((resp) => {
        let index = $scope.authories.findIndex((a) => a.id == authority.id);
        $scope.authories.splice(index, 1);
        alert("Thu hoi quyen thanh cong");
      })
      .catch((error) => {
      });
  };
   $scope.edit = function (acc) {
       $scope.avatar = acc.avatar;
      $scope.username = acc.username;
      $scope.fullname = acc.fullname;
      $scope.password = acc.password;
      $scope.email = acc.email;
      $scope.phone = acc.phone;
      $scope.firstname = acc.firstname;
      $scope.lastname = acc.lastname;
     	$(".nav-pills a:eq(1)").tab('show');
    };
});