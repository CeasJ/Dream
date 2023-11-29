const app = angular.module("authority-app", []);
app.controller("authority-ctrl", function ($scope, $http, $location) {
  $scope.roles = [];
  $scope.listStaff = [];
  $scope.authorities = [];

  $scope.initialize = function () {
    $http.get(`/rest/roles`).then((resp) => {
      $scope.roles = resp.data;
    });


    $http.get(`/rest/staff/admin?admin=true`).then((resp) => {
      $scope.listStaff = resp.data;
    });

    $http.get(`/rest/authorities?admin=true`).then((resp) => {
      $scope.authorities = resp.data;
    })
      .catch((error) => {
        $location.path(`/security/login/unauthoried`);
      });


      $scope.avatar = "default.png";
  };
  $scope.initialize();

  $scope.authority_of = function (acc, role) {
    if ($scope.authorities) {
      return $scope.authorities.find(
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
        $scope.authorities.push(resp.data);
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
        let index = $scope.authorities.findIndex((a) => a.id == authority.id);
        $scope.authorities.splice(index, 1);
        toastr.success("Permissions revoked successfully");
      })
      .catch((err) => {
        toastr.error('Permissions revoked Fail');

      });
  };


  $scope.clearForm = function () {
     $scope.username = "";
     $scope.firstname = "" ;
     $scope.lastname = "";
     $scope.password =""; 
     $scope.email =""; 
     $scope.phone ="";
     $scope.avatar = "default.png";
     $scope.address ="";
  };

  $scope.saveAccount = function () {
    let account = {
      username: $scope.username,
      fullname: $scope.firstname + ' ' +$scope.lastname,
      password: $scope.password,
      email: $scope.email,
      phone: $scope.phone,
      firstname: $scope.firstname,
      lastname: $scope.lastname,
      avatar: $scope.avatar,
      address: $scope.address
    };
    let existUsername = $scope.listStaff.find(function (staff) {
      return staff.username === account.username;
    });
    if (existUsername) {
      alert("Username is exist");
    } else {
      $http
        .post(`/rest/staff/add`, account)
        .then(function (response) {
          $scope.listStaff.push(response.data);
          $scope.clearForm();
          toastr.success("Create Successful");
          setTimeout(()=>{
            location.reload();
          },1000);
        
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
      .post(`/rest/upload/img/avatar`, data, {
        transformRequest: angular.identity,
        headers: { "Content-Type": undefined },
      })
      .then((resp) => {
        $scope.avatar = resp.data.name;
      })
      .catch((err) => {
        toastr.error("Select image Fail");
        console.log(err);
      });
  };

  // Staff Pagination
    $scope.currentPage = 1;
    $scope.pageSize = 5; // Số nhân viên hiển thị mỗi trang

    $scope.totalPages = function () {
      return Math.ceil($scope.listStaff.length / $scope.pageSize);
    };

    $scope.setPage = function (page) {
      if (page >= 1 && page <= $scope.totalPages()) {
        $scope.currentPage = page;
      }
    };

$scope.paginatedList = function () {
  const begin = ($scope.currentPage - 1) * $scope.pageSize;
  const end = begin + $scope.pageSize;

  return $scope.listStaff.slice(begin, end);
};

// Pagination Authorization
$scope.currentPageAuth = 1;
$scope.pageSizeAuth = 5; // Số quyền hiển thị mỗi trang

$scope.totalPagesAuth = function () {
  return Math.ceil($scope.listStaff.length / $scope.pageSizeAuth);
};

$scope.setPageAuth = function (page) {
  if (page >= 1 && page <= $scope.totalPagesAuth()) {
    $scope.currentPageAuth = page;
  }
};

$scope.paginatedListAuth = function () {
  const begin = ($scope.currentPageAuth - 1) * $scope.pageSizeAuth;
  const end = begin + $scope.pageSizeAuth;

  return $scope.listStaff.slice(begin, end);
};


});