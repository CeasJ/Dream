const app = angular.module("size-app", []);
app.controller("size-ctrl", function ($scope, $http, $location) {
  $scope.sizes = [];
  $scope.products = [];
  $scope.productsize = [];
  $scope.priceInput = {};

  $scope.intialize = function () {
    $http.get(`/rest/size`).then((resp) => {
      $scope.sizes = resp.data;
    })

    $http.get(`/rest/products`).then((resp) => {
      $scope.products = resp.data;
    })

    $http.get(`/rest/productsizes`).then((resp) => {
      $scope.productsize = resp.data;
    })
      .catch((error) => {
        $location.path(`/security/login/unauthoried`);
      });
  };
  $scope.intialize();

  $scope.size_of = function (name, size) {
    if ($scope.productsize) {
      return $scope.productsize.find(
        (ur) => ur.id_product == name.id && ur.id_size == size.id
      );
    }
  };


  $scope.size_changed = function (name, size) {
    let productSize = $scope.size_of(name, size);
    if (productSize) {
      $scope.revoke_size(productSize);
    } else {
      productSize = { id_product: name.id, id_size: size.id};
       $scope.grant_size(productSize);
      $scope.showInput(name.id, size.id, true);
    }
       // Khởi tạo priceInput nếu chưa tồn tại
        if (!$scope.priceInput[name.id]) {
            $scope.priceInput[name.id] = {};
        }
  };

  $scope.grant_size = function (productSize) {
    $http.post(`/rest/productsizes`, productSize)
      .then((resp) => {
        $scope.productsize.push(resp.data);
      })
      .catch((error) => {
      });
  };
  $scope.revoke_size = function (productSize) {
    $http.delete(`/rest/productsizes/${productSize.id}`, productSize)
      .then((resp) => {
        let index = $scope.productsize.findIndex((a) => a.id == productSize.id);
        $scope.productsize.splice(index, 1);
        alert("Xoá Size  thanh cong");
      })
      .catch((error) => {
      });
  };
     $scope.savePrice = function (id_product, id_size, priceProduct_Size) {
         var priceProduct_Size = $scope.priceInput[id_product] ? $scope.priceInput[id_product][id_size] : null;
         if (priceProduct_Size !== null && priceProduct_Size !== undefined) {
             $scope.showInput(id_product, id_size, false);
           var productSize = {
                id_product: id_product,
                id_size: id_size,
                priceProduct_Size: priceProduct_Size
           };
            $scope.grant_size(productSize);
             alert('Thêm giá thành công cho size ' + id_size + ' của sản phẩm ' + id_product);
         } else {
             alert('Vui lòng nhập giá trước khi lưu.');
         }
     };

     $scope.showInput = function (id_product, id_size) {
         return $scope.priceInput[id_product] && $scope.priceInput[id_product][id_size];
     };
});

//document.addEventListener('DOMContentLoaded', function () {
//  Promise.all([
//    fetch('/rest/size'),
//    fetch('/rest/products'),
//    fetch('/rest/productsizes')
//  ])
//    .then(responses => Promise.all(responses.map(response => response.json())))
//    .then(data => {
//      let sizeTables = document.querySelectorAll('.size-datatable');
//      sizeTables.forEach(table => {
//        new simpleDatatables.DataTable(table);
//      });
//    })
//    .catch(error => console.error("Error loading data:", error));
//
//});