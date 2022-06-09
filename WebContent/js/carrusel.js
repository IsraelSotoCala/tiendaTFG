var owl = $(".owl-carousel");

owl.owlCarousel({
    loop: true,
    margin: 5,
    nav: false,
    navText: ["<img src='/tiendaTFG/assets/img/flechaI.png' alt='Previous' width='25px'>", "<img src='/tiendaTFG/assets/img/flechaD.png' alt='Next' width='25px'>"],
    dots: false,
    dotsEach: 1,
    items: 3,
    responsive: {
        0: {
            items: 1,
        },
        600: {
            items: 3,
        },
        1000: {
            items: 5,
        }
    }
});