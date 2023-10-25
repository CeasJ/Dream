(function ($) {
    "use strict";

    // Spinner
    var spinner = function () {
        setTimeout(function () {
            if ($('#spinner').length > 0) {
                $('#spinner').removeClass('show');
            }
        }, 1);
    };
    spinner();


    // Initiate the wowjs
    new WOW().init();


    // Sticky Navbar
    $(window).scroll(function () {
        if ($(this).scrollTop() > 300) {
            $('.sticky-top').addClass('shadow-sm').css('top', '0px');
        } else {
            $('.sticky-top').removeClass('shadow-sm').css('top', '-150px');
        }
    });


    // Back to top button
    $(window).scroll(function () {
        if ($(this).scrollTop() > 300) {
            $('.back-to-top').fadeIn('slow');
        } else {
            $('.back-to-top').fadeOut('slow');
        }
    });
    $('.back-to-top').click(function () {
        $('html, body').animate({scrollTop: 0}, 1500, 'easeInOutExpo');
        return false;
    });


    // Modal Video
    var $videoSrc;
    $('.btn-play').click(function () {
        $videoSrc = $(this).data("src");
    });
    console.log($videoSrc);
    $('#videoModal').on('shown.bs.modal', function (e) {
        $("#video").attr('src', $videoSrc + "?autoplay=1&amp;modestbranding=1&amp;showinfo=0");
    })
    $('#videoModal').on('hide.bs.modal', function (e) {
        $("#video").attr('src', $videoSrc);
    })


    // Product carousel
    $(".product-carousel").owlCarousel({
        autoplay: true,
        smartSpeed: 1000,
        margin: 25,
        loop: true,
        center: true,
        dots: false,
        nav: true,
        navText : [
            '<i class="bi bi-chevron-left"></i>',
            '<i class="bi bi-chevron-right"></i>'
        ],
        responsive: {
			0:{
                items:1
            },
            576:{
                items:1
            },
            768:{
                items:2
            },
            992:{
                items:3
            }
        }
    });


    // Testimonial carousel
    $(".testimonial-carousel").owlCarousel({
        autoplay: true,
        smartSpeed: 1000,
        items: 1,
        loop: true,
        dots: true,
        nav: false,
    });

})(jQuery);



// Change the price according to the chosen price of the product


// Select first child of size radio buttons
document.addEventListener("DOMContentLoaded", function () {
    var sizeRadioButtons = document.querySelectorAll('input[type=radio][name=selectedSize]');

    if (sizeRadioButtons.length > 0) {
        sizeRadioButtons[0].checked = true;
    }
});



// Change price according to the chosen size
//document.addEventListener("DOMContentLoaded", function () {
//    var sizeRadioButtons = document.querySelectorAll('input[type=radio][name=selectedSize]');
//    var priceElement = document.querySelector('.product-price');
//    var priceDiscountedElement = document.querySelector('.product-discountedPrice');
//    var discountedPriceText = priceDiscountedElement.textContent.replace(',', ''); // Loại bỏ dấu phẩy
//    var originalPrice = parseFloat(priceElement.getAttribute('data-original-price'));
//
//    sizeRadioButtons.forEach(function (radio) {
//        radio.addEventListener('change', function () {
//            updatePrice();
//        });
//    });
//
//    function updatePrice() {
//        var selectedSize = document.querySelector('input[type=radio][name=selectedSize]:checked');
//        var isDiscounted = '${product.isDiscounted}'; // Lấy giá trị isDiscounted từ Thymeleaf
//
//        if (selectedSize) {
//            var sizeValue = selectedSize.getAttribute('data-size-value');
//            var newSizePrice = originalPrice;
//
//            if (sizeValue === 'M') {
//                newSizePrice += 5000;
//            } else if (sizeValue === 'L') {
//                newSizePrice += 10000;
//            }
//
//            // Cập nhật giá theo kích thước cho priceElement
//            priceElement.textContent = formatPrice(newSizePrice);
//            console.log(priceElement.textContent);
//            console.log(newSizePrice);
//            if (isDiscounted) {
//                // Nếu sản phẩm được giảm giá, cập nhật giá giảm giá
//                if (priceDiscountedElement) {
//                    // Giá giảm giá phải cộng thêm số tiền,
//                    var discountedPrice = (newSizePrice - parseFloat(discountedPriceText));
//                    console.log(originalPrice);
//                    console.log(parseFloat(discountedPriceText));
//                    console.log(newSizePrice);
//                    console.log(discountedPrice);
//
//                    priceDiscountedElement.textContent = formatPrice(discountedPrice);
//                }
//            }
//        }
//    }
//
//    function formatPrice(price) {
//        return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(price);
//    }
//});
//