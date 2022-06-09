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
 * Servlet implementation class AnadirAlCarrito
 */
@WebServlet("/AnadirAlCarrito")
public class AnadirAlCarrito extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		//Variables

		HttpSession sesion; 
		
		sesion = request.getSession();
		
		ArrayList<ProductoVO> carrito;
		
		ProductoVO producto;
		
		int idProducto;
		
		producto = (ProductoVO) sesion.getAttribute("producto");
		
		//Cargamos el producto a añadir, si existe continuamos
		
		if (producto != null) {
			
			idProducto = producto.getId();
			
			// Si no existia el carrito creamos uno nuevo si no lo cogemos del atributo de
			// sesion carrito

			carrito = (ArrayList<ProductoVO>) sesion.getAttribute("carrito");
			
			if(carrito == null) {
				carrito = new ArrayList<ProductoVO>();
			}

			//Comprobamos que el producto no este añadido en el carrito
			//Si esta el booleano se queda como falso y no se añade
			//Si no esta se cambia y el producto se añade al carrito
			
			boolean productoAnanido;

			productoAnanido = false;
			
			if (carrito.size() > 0) {
				for (int i = 0; i < carrito.size(); i++) {
					if (idProducto == carrito.get(i).getId()) {
						productoAnanido = true;
						break;
					}
				}
			}

			if (!productoAnanido) {
				carrito.add(producto);
			}
			
			sesion.removeAttribute("producto");

			sesion.setAttribute("carrito", carrito);
			
			sesion.setAttribute("productoAnanido", "Producto anadido con exito");
	
			response.sendRedirect("ListaProductos");
		} else {
			//Si existe un fallo al añadir mostramos un error
			sesion.setAttribute("productoAnanido", "Error al anadir producto");
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
