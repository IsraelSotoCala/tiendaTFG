package utiles;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import modeloTFG.ProductoVO;

/**
 * Servlet implementation class BorrarProductoCarritp
 */
@WebServlet("/BorrarProductoCarrito")
public class BorrarProductoCarrito extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		//Variables
		
		HttpSession sesion;

		sesion = request.getSession();
		
		int idProducto;
		
		Double total = 0.0;

		//Recogemos el id del producto que enviamos por ajax y cargamos los productos del carrito
		
		idProducto = Integer.parseInt(request.getParameter("id").trim());

		ArrayList<ProductoVO> productos;

		productos = (ArrayList<ProductoVO>) sesion.getAttribute("carrito");
		
		//Si el carrito tiene productos, recorremos el array, si el id de un
		//producto coincide con el id que pasamos por parametro
		//Eliminamos ese producto
		if (!productos.isEmpty()) {
			for (int p = 0; p < productos.size(); p++ ) {
				if (productos.get(p).getId() == idProducto) {
					productos.remove(p);
				}
			}
			//Aqui recalculamos el precio de la compra
			for (int i = 0; i < productos.size(); i++) {
				total += total + productos.get(i).getPrecio();
			}
			//Enviamos el precio para mostrarlo dinamicamente
			response.getWriter().print(Math.round(total * 100.0) / 100.0);
			//Actualizamos el atributo de sesion despues de eliminar
			sesion.setAttribute("carrito", productos);
		} else {
			//Si el carrito esta vacio lo eliminamos para evitar errores
			sesion.removeAttribute("carrito");
		}
		

	}

}
