$('.desplegar').click(function() {
    $('.menuDesplegable').addClass('active')
})

document.addEventListener("click", (e) => {
    if (!e.target.classList.contains('desplegar')) {
        $('.menuDesplegable').removeClass('active')
    }
})