const app = angular.module("discount-app", []);
app.controller("discount-ctrl", function ($scope, $http, $location) {

  $scope.discounts = [];
  $scope.intialize = function () {
    $http.get(`/rest/discount`).then((resp) => {
      $scope.discounts = resp.data;
         $scope.discounts.forEach((count) => {
              count.activeDate = new Date(count.activeDate);
                count.expiredDate = new Date(count.expiredDate);
         });
    });
  };
  $scope.intialize();
    $scope.reset = function () {
       $scope.form = {
       };
     };
     $scope.edit = function (count) {
       $scope.form = angular.copy(count);
     };
   $scope.create = function () {
       let discount = angular.copy($scope.form);
       $http
         .post(`/rest/discount`, discount)
         .then((resp) => {
           resp.data.activeDate = new Date(resp.data.activeDate);
            resp.data.expiredDate = new Date(resp.data.expiredDate);
           $scope.discounts.push(resp.data);
           $scope.reset();
           alert("Create Success");
         })
         .catch((err) => { });
     };
      $scope.update = function () {
        let discount = angular.copy($scope.form);
        $http.put(`/rest/discount/${discount.id}`, discount).then(resp => {
          let index = $scope.discounts.findIndex(p => p.id == discount.id);
          $scope.discounts[index] = discount;
          $scope.reset();
          alert("Update Success");
        }).catch(err => {
        })
      };
       $scope.delete = function (count) {
          $http.delete(`/rest/discount/${count.id}`).then(resp => {
            let index = $scope.discounts.findIndex(p => p.id == count.id);
            $scope.discounts.splice(index, 1);
            $scope.reset();
            alert("Delete Success")
          }).catch(err => {
          })
        };
});
