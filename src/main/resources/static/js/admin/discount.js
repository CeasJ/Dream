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
       let checkNameDiscount = $scope.discounts.find(dis => dis.name === discount.name);
       if(checkNameDiscount) {
          $("#discountModal").modal("hide");
          toastr.error("Name already exists");
       } else {
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
          if (err.data && err.data.errors) {
            $("#discountModal").modal("hide");
            err.data.errors.forEach(function(error, index) {
              toastr.error(`Error ${index + 1}: ${error}`);
            });
          } 
         });
        }
     };
      $scope.update = function () {
        let discount = angular.copy($scope.form);
        let checkNameDiscount = $scope.discounts.find(dis => dis.name === discount.name);
        if(checkNameDiscount) {
           $("#discountModal").modal("hide");
           toastr.error("Name already exists");
        } else {
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
          $("#discountModal").modal("hide");
          err.data.errors.forEach(function(error, index) {
            toastr.error(`Error ${index + 1}: ${error}`);
          });
        })
        }
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
          let category = {
            id: categoryID,
            id_discount: discountID,
            name: categoryName,
          };
          $http.put(`/rest/category/update/${categoryID}`, category).then(resp => {
            let index = $scope.cates.findIndex((cate) => cate.id === categoryID);
            $scope.cates[index] = category;
            $scope.reset();
            toastr.success("Update Success");
            setTimeout(()=>{
             location.reload();
            },1000);
          }).catch(err => {
            toastr.error("Update Fail");
          })
       };

        $scope.updateNotApplyDiscount = function(categoryID, categoryName) {
          let category = {
            id: categoryID,
            id_discount: '',
            name: categoryName,
          };
          $http.put(`/rest/category/update/${categoryID}`, category).then(resp => {
            let index = $scope.cates.findIndex((cate) => cate.id === categoryID);
            $scope.cates[index] = category;
            $scope.reset();
            toastr.success("Update Success");
            setTimeout(()=>{
             location.reload();
            },1000);
          }).catch(err => {
            toastr.error("Update Fail");
          })
       };
      
       $scope.createCategory = function (name) {
        let checkNameCategory = $scope.cates.find(c => c.name === name);

        if(checkNameCategory){
          $("#createModal").modal("hide");
          toastr.error("Name already exist");
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
              if (err.data && err.data.errors) {
                $("#createModal").modal("hide");
                toastr.error(err.data.errors);
              } 
            });
        }

      };
});
