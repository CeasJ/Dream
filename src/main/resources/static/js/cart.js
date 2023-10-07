const app = angular.module('cart',[]);

app.controller('ctrl',function($scope,$http){
//    begin getCart
    function getCart(username){
        const cartKey = `cart_${username}`;
        const json = localStorage.getItem(cartKey);
        return json ? JSON.parse(json) : {
            username : username,
            items:[]
        };
    }
//    end getCart

//     begin saveCart
    function saveCart(username,cart){
        let cartKey = `cart_${username}`;
        let json = JSON.stringify(cart);
        localStorage.setItem(cartKey, json);
    }
//     end saveCart

//    begin totalPrice
    function totalPrice(){
        let totalPrice = 0;
        angular.forEach($scope.cart.items,function(item){
           totalPrice += item.qty * item.price;
        });
        return totalPrice;
    }

//    end totalPrice


//       begin scope cart
    $scope.cart = {
        username = "",

        items =[],

        add(id){
            if(!this.items){
                this.items = [];
            }
          let item = this.items.find(item=>item.id==id);
          if(item){
            item.qty++;
            saveCart(this.username,this);
          } else {
            $http.get(`/rest/product/${id}`).then(resp=>{
                let newItem = resp.data;
                newItem.qty = 1 ;
                this.items.push(newItem);
                saveCart(this.username,this);
            })
          }
        },

        remove(id) = {
            let index = this.items.findIndex(item => item.id === id);
            this.items.splice(index, 1);
            saveCart(this.username, this);
        },

        clear(){
            this.items = [];
            saveCart(this.username,this);
        },

        getCount(){
            return this.items.map(item => item.qty).reduce((total, qty) => count += qty,0);
        },

        getAmount(){
            return totalPrice();
        },

        saveToLocalStore(){
            let itemToSave = this.items.map(item=>{
               const {$$hashKey,...cleanItem} = item;
               return cleanItem;
            });
            saveCart(this.username,itemToSave);
        },

        loadFromLocalStore(){
            let cart = getCart(this.username);
            this.items = cart.items;
        },

        totalPrice:totalPrice
    };
//       end scope cart
    let totalPrice = $("totalPrice").text.trim();
    let username = $("#username").text().trim();
    $scope.cart.username = username;
    $scope.cart.loadFromLocalStore();


})