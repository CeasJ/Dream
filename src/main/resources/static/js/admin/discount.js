const app = angular.module("discount-app", []);
app.controller("discount-ctrl", function ($scope, $http) {

  $scope.discounts = [];
  $scope.cates = [];

  $scope.initialize = function () {
    $http.get(`/rest/discount`).then((resp) => {
      $scope.discounts = resp.data;
         $scope.discounts.forEach((count) => {
              count.activeDate = new Date(count.activeDate);
              count.expiredDate = new Date(count.expiredDate);
         });
    });
    $http.get(`/rest/category`).then((resp) => {
      $scope.cates = resp.data;
      console.log(resp.data);
    });

    $scope.form = {
      active:true,
      activeDate: new Date()
     };
  };

  $scope.initialize();

    $scope.reset = function () {
       $scope.form = {
        active:true,
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
           $("#discountModal").modal("hide");
           toastr.success("Create Success");
           setTimeout(()=>{
            location.reload();
           },1000);
         })
         .catch((err) => { 
          toastr.error("Create Fail");
         });
     };
      $scope.update = function () {
        let discount = angular.copy($scope.form);
        $http.put(`/rest/discount/${discount.id}`, discount).then(resp => {
          let index = $scope.discounts.findIndex(p => p.id === discount.id);
          $scope.discounts[index] = discount;
          $scope.reset();
          $("#discountModal").modal("hide");
          toastr.success("Update Success");
          setTimeout(()=>{
           location.reload();
          },1000);
        }).catch(err => {
          toastr.error("Create Fail");
        })
      };
       $scope.delete = function (count) {
          $http.delete(`/rest/discount/${count.id}`).then(resp => {
            let index = $scope.discounts.findIndex(p => p.id === count.id);
            $scope.discounts.splice(index, 1);
            $scope.reset();
            $("#discountModal").modal("hide");
            toastr.success("Delete Success");
            setTimeout(()=>{
           location.reload();
          },1000);
          }).catch(err => {
            toastr.error("Create Fail");
          })
        };

        $scope.updateApplyDiscount = function(categoryID, discountID,categoryName) {
          console.log(categoryID);
          console.log(discountID);
          let category = {
            id: categoryID,
            id_discount: discountID,
            name: categoryName,
          };
          console.log(category);
          $http.put(`/rest/category/update/${categoryID}`, category).then(resp => {
            let index = $scope.cates.findIndex((cate) => cate.id === categoryID);
            $scope.cates[index] = category;
            $scope.reset();
            toastr.success("Update Success");
            setTimeout(()=>{
             location.reload();
            },1000);
          }).catch(err => {
            console.log(err);
            toastr.error("Upate Fail");
          })
       };

        $scope.updateNotApplyDiscount = function(categoryID, categoryName) {
          console.log(categoryID);
          let category = {
            id: categoryID,
            id_discount: '',
            name: categoryName,
          };
          console.log(category);
          $http.put(`/rest/category/update/${categoryID}`, category).then(resp => {
            let index = $scope.cates.findIndex((cate) => cate.id === categoryID);
            $scope.cates[index] = category;
            $scope.reset();
            toastr.success("Update Success");
            setTimeout(()=>{
             location.reload();
            },1000);
          }).catch(err => {
            console.log(err);
            toastr.error("Upate Fail");
          })
       };
      
       $scope.createCategory = function (name) {
        let checkNameCategory = $scope.cates.find(c => c.name === name);

        if(checkNameCategory){
          $("#createModal").modal("hide");
          toastr.warning("Name already exist");
        } else {
          let category = {
            name: name,
          };
          $http
            .post(`/rest/category`, category)
            .then((resp) => {
              $scope.cates.push(resp.data);
              $("#createModal").modal("hide");
              toastr.success("Create Success");
              setTimeout(()=>{
               location.reload();
              },1000);
            })
            .catch((err) => { 
             toastr.error("Create Fail");
            });
        };

      };
});
