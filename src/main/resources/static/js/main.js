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


// Get combobox value arcording to the chosen option
var urlParams = new URLSearchParams(window.location.search);
var sortOption = urlParams.get("sortOption");

if (sortOption !== null) {
    var selectElement = document.getElementById("sortByPrice");
    if (selectElement) {
        selectElement.value = sortOption;
    }
}

// Get click search button event
document.addEventListener("DOMContentLoaded", function () {
    var searchButton = document.getElementById("searchButton");
    var productNameSearch = document.getElementById("productNameSearch");

    if (searchButton && productNameSearch) {
        searchButton.addEventListener("click", function () {
            var searchValue = productNameSearch.value;
            window.location.href = "/search?productName=" + searchValue + "&page=0";
        });
    }
});

// Category combobox listener event

    var urlParams = new URLSearchParams(window.location.search);
    var categoryId = urlParams.get("categoryId");

    var productGroupSelect = document.getElementById("productGroup");

    if (categoryId) {
        productGroupSelect.value = categoryId;
    }

    productGroupSelect.addEventListener("change", function() {
        var selectedCategoryId = this.value;


        localStorage.setItem("selectedCategoryId", selectedCategoryId);


        window.location.href = "/store?categoryId=" + selectedCategoryId + "&page=0";

    });

//sort by price function
function updateCategoryIdAndSubmit() {

        var selectedSortOption = document.getElementById("sortByPrice").value;


        var selectedCategoryId = document.getElementById("productGroup").value;


        var newUrl = "/store?categoryId=" + selectedCategoryId;


        if (selectedSortOption !== 'none') {
            newUrl += "&sortOption=" + selectedSortOption;
        }


        window.location.href = newUrl;
    }

