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

    // Back to top button
    $(window).scroll(function () {
        if ($(this).scrollTop() > 300) {
            $('.back-to-top').fadeIn('slow');
        } else {
            $('.back-to-top').fadeOut('slow');
        }
    });

    // Scroll to top when back-to-top button is clicked
    $('.back-to-top').click(function () {
        $('html, body').animate({ scrollTop: 0 }, 1500, 'easeInOutExpo');
        return false;
    });


    // Show .cart-0 if cartCount is less than or equal to 0, otherwise show .cart-1
    var cartCount = parseInt($('#cartCount').text());

    if (cartCount <= 0) {
        $('.cart-0').show();
        $('.cart-1').hide();
    } else {
        $('.cart-0').hide();
        $('.cart-1').show();
    }

    $('#step-1').addClass('active-stext');
    // Show infor-cart and hide cart-0, cart-1 on Buy button click
    $('#buyButton').click(function () {
        $('.infor-cart').show();
        $('.cart-0, .cart-1, .form-buy').hide();

        // Add active class to number-2 and apply animation
        $('#number-2').addClass('active');
        $('#line-1').addClass('active-line');
        $('#step-2').addClass('active-stext');


        // Change color of number-2 to match .step-button.active
        $('#number-2').css({
            'background-color': 'var(--primary)', // Màu xanh lá của bạn
            'color': 'white'
        });
    });

    $('#backButton').click(function () {
        $('.cart-0, .cart-1, .form-buy').show();
        $('.infor-cart').hide();

        // Remove active class from number-2 and remove animation
        $('#number-2').removeClass('active');
        $('#line-1').removeClass('active-line');
        $('#step-2').removeClass('active-stext');
        // Reset color of number-2 to default state
        $('#number-2').css({
            'background-color': 'lightgray', // Màu mặc định của number-2
        });


        if (cartCount <= 0) {
            $('.cart-0').show();
            $('.cart-1').hide();
        } else {
            $('.cart-0').hide();
            $('.cart-1').show();
        }

    });

    $('#completeButton').click(function () {
        $('.cart-3').show();
        $('.cart-0, .cart-1, .form-buy, .infor-cart').hide();
        $('#number-3').addClass('active');
        $('#line-2').addClass('active-line');
        $('#step-3').addClass('active-stext');
    });

})(jQuery);

