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
      }).catch(err => {
      });
    }
  }

});
