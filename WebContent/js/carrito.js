/**
 * 
 */

$(function() {
	$('.borrarProducto').click(function(e) {
		e.preventDefault();
		let producto = $(this);
		let idProducto = producto.parent().find('.idProducto').text().trim();
		$.ajax ({
			url : 'BorrarProductoCarrito',
			type : 'POST',
			data : {id : idProducto},
			success : function(r) {
				producto.parent().remove();
				let productoCarrito = $('.producto-carrito');
				console.log(productoCarrito.length);
				if(productoCarrito.length < 1) {
					$('.lista-carrito').append("<h4>No hay articulos en el carro</h4>");
				}
				
				$ ('.precioTotal').text(r);
				console.log(productoCarrito.length);
			}
		})
	})	
});