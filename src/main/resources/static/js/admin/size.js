const app = angular.module("size-app", []);
app.controller("size-ctrl", function ($scope, $http, $location) {
  $scope.sizes = [];
  $scope.products = [];
  $scope.productSize = [];

  $scope.intialize = function () {
    $http.get(`/rest/size`).then((resp) => {
      $scope.sizes = resp.data;
    });

    $http.get(`/rest/products`).then((resp) => {
      $scope.products = resp.data;
    });

    $http
      .get(`/rest/productsizes`)
      .then((resp) => {
        $scope.productSize = resp.data;
      })
      .catch((error) => {
        $location.path(`/security/login/unauthorized`);
      });
  };
  $scope.intialize();

$scope.getPrice = function(productId, sizeId) {
       const productSize = $scope.productSize.find(ps => ps.id_product === productId && ps.id_size === sizeId);

       return productSize ? productSize.price : null;
   };

  $scope.size_of = function (id_product, id_size) {
    if ($scope.productSize) {
      return $scope.productSize.find(
        (ur) => ur.id_product === id_product && ur.id_size === id_size
      );
    }
  };

   $scope.getPrice = function(productId, sizeId) {
       const productSize = $scope.productSize.find(ps => ps.id_product === productId && ps.id_size === sizeId);

       return productSize ? productSize.price : null;
   };

  $scope.size_changed = function (id_product, id_size) {
    let productSize = $scope.size_of(id_product, id_size);
    if (productSize) {
      $scope.revoke_size(productSize);
    } else {
      let price = $scope.updateProductSizePrice();
      productSize = { id_product: id_product, id_size: id_size, price: price };
      $scope.grant_size(productSize);
    }
  };

  $scope.grant_size = function (productSize) {
    $scope.id_product = productSize.id_product;

    $scope.id_size = productSize.id_size;

    $("#priceModal").modal("show");

    $scope.selectRadioBasedOnIdSize(productSize.id_size);
  };

  $scope.add = function () {
      let id_product = $scope.id_product;
      let id_size = $scope.id_size;
      let price = $scope.updateProductSizePrice();

      if (!isValidPrice(price)) {
          toastr.error("Price must be a number greater than 0");
          return;
      }

      if (!isValidSizePrice(id_size, price, $scope.productSize)) {
          toastr.error("Invalid size price. Please ensure S < M < L.");
          return;
      }

      if (id_size) {
          let productSize = {
              id_product: id_product,
              id_size: id_size,
              price: price,
          };
          $http
              .post(`/rest/productsizes`, productSize)
              .then((resp) => {
                  $scope.productSize.push(resp.data);
                  $("#priceModal").modal("hide");
                  toastr.success("Add size success");
                  setTimeout(() => {
                      location.reload();
                  }, 1000);
              })
              .catch((error) => {});
      } else {
          toastr.error("Please select a size");
      }
  };

  $scope.showModal = function(id_product){
    $scope.id_product = id_product;
    $("#priceModal").modal("show");
  };

 $scope.updatePriceSize = function () {
     let id_product = $scope.id_product;
     let id_size = $scope.id_size;
     let price = $scope.updateProductSizePrice();

     if (!isValidPrice(price)) {
         toastr.error("Price must be a number greater than 0");
         return;
     }

     if (!isValidSizePrice(id_size, price, $scope.productSize)) {
         toastr.error("Invalid size price. Please ensure S < M < L.");
         return;
     }

     if (id_size) {
         let productSize = {
             id_product: id_product,
             id_size: id_size,
             price: price,
         };
         $http.put(`/rest/productsizes/${id_product}`, productSize)
             .then((resp) => {
                 let index = $scope.productSize.findIndex((product) => product.id === product.id_product);
                 $scope.productSize[index] = productSize;
                 $("#priceModal").modal("hide");
                 toastr.success("Update price size success");
                 setTimeout(() => {
                     location.reload();
                 }, 1000);
             })
             .catch((error) => {});
     } else {
         toastr.error("Please select a size");
     }
 };

  $scope.selectSizeProduct = function(id_size){
    return $scope.id_size = id_size;
  };

  $scope.revoke_size = function (productSize) {
    $http
      .delete(`/rest/productsizes/${productSize.id}`, productSize)
      .then((resp) => {
        let index = $scope.productSize.findIndex(
          (product) => product.id == productSize.id_product
        );
        $scope.productSize.splice(index, 1);
        toastr.success("Delete success");
        setTimeout(() => {
          location.reload();
        }, 1000);
      })
      .catch((error) => {});
  };

  $scope.updateProductSizePrice = function () {
    return $scope.price;
  };

  $scope.selectRadioBasedOnIdSize = function (id_size) {
    switch (id_size) {
      case 1:
        $scope.selectedSize = "S";
        $scope.radioBlocked = {
          S: false,
          M: true,
          L: true,
        };
        break;
      case 2:
        $scope.selectedSize = "M";
        $scope.radioBlocked = {
          S: true,
          M: false,
          L: true,
        };
        break;
      case 3:
        $scope.selectedSize = "L";
        $scope.radioBlocked = {
          S: true,
          M: true,
          L: false,
        };
        break;
      default:
        break;
    }
  };


// Pagination
    $scope.currentPage = 1;
    $scope.pageSize = 5;
    $scope.totalPages = 0;
    $scope.totalPagesArray = [];

    $scope.getFilteredProducts = function () {
        var begin = ($scope.currentPage - 1) * $scope.pageSize;
        var end = begin + $scope.pageSize;
        $scope.filteredProducts = $scope.products.slice(begin, end);
    };

    $scope.calculateTotalPages = function () {
        $scope.totalPages = Math.ceil($scope.products.length / $scope.pageSize);
        $scope.totalPagesArray = [];
        for (var i = 1; i <= $scope.totalPages; i++) {
            $scope.totalPagesArray.push(i);
        }
    };

    $scope.firstPage = function () {
        if ($scope.currentPage !== 1) {
            $scope.currentPage = 1;
            $scope.getFilteredProducts();
        }
    };

    $scope.lastPage = function () {
        if ($scope.currentPage !== $scope.totalPages) {
            $scope.currentPage = $scope.totalPages;
            $scope.getFilteredProducts();
        }
    };

    $scope.getPagerNumbers = function () {
        let totalPages = $scope.totalPages;
        let currentPage = $scope.currentPage;

        if (totalPages <= 5) {
            return Array.from({ length: totalPages }, (_, i) => i + 1);
        } else {
            let startPage = Math.max(1, currentPage - 2);
            let endPage = Math.min(currentPage + 2, totalPages);

            if (endPage - startPage < 4) {
                startPage = Math.max(1, endPage - 4);
            }

            return Array.from({ length: 5 }, (_, i) => startPage + i);
        }
    };

    $scope.setPage = function (page) {
        if (page < 1 || page > $scope.totalPages) {
            return;
        }
        $scope.currentPage = page;
        $scope.getFilteredProducts();
    };

    $scope.nextPage = function () {
        if ($scope.currentPage < $scope.totalPages) {
            $scope.currentPage++;
            $scope.getFilteredProducts();
        }
    };

    $scope.prevPage = function () {
        if ($scope.currentPage > 1) {
            $scope.currentPage--;
            $scope.getFilteredProducts();
        }
    };

    $scope.$watch("products", function () {
        $scope.calculateTotalPages();
        $scope.getFilteredProducts();
    });


    // Searching features
    $scope.searchProduct = function () {
        if ($scope.searchProductName && $scope.searchProductName !== "") {
          $http.get(`/rest/productsizes/search?name=${$scope.searchProductName}`)
            .then(function (response) {
              $scope.products = response.data;
            })
            .catch(function (error) {
              console.error("Error fetching products:", error);
            });
        } else {
          $scope.intialize();
        }
      };

      // Gọi phương thức tìm kiếm khi người dùng nhập vào input
      $scope.$watch('searchProductName', function (newVal, oldVal) {
        if (newVal !== oldVal) {
          $scope.searchProduct();
        }
      });

      // Size checking and comparison
      function isValidPrice(price) {
          return !isNaN(price) && price > 0;
      }

      function isValidSizePrice(id_size, price, productSize) {
          const priceS = getPriceForSize(1, productSize);
          const priceM = getPriceForSize(2, productSize);
          const priceL = getPriceForSize(3, productSize);

          switch (id_size) {
              case 1:
                  return (price < priceM && (!priceL || priceM < priceL));
              case 2:
                  return (priceS && priceS < price && (!priceL || price < priceL));
              case 3:
                  return (priceS && priceM && priceS < priceM && priceM < price);
              default:
                  return false;
          }
      }

      function getPriceForSize(sizeId, productSize) {
          const size = productSize.find(ps => ps.id_size === sizeId);
          return size ? size.price : null;
      }


});