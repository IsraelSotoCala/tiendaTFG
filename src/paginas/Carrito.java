package paginas;

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
 * Servlet implementation class Carrito
 */
@WebServlet("/Carrito")
public class Carrito extends HttpServlet {
	private static final long serialVersionUID = 1L;

	HttpSession sesion;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		sesion = request.getSession();
		
		ArrayList<ProductoVO> productosCarrito;
		
		//Cargamos los productos del carrito
		
		productosCarrito = (ArrayList<ProductoVO>) sesion.getAttribute("carrito");

		//Si hay productos en el carrito los mostramos si no mostramos un mensaje
		
		if (productosCarrito !=null) {
			sesion.setAttribute("carrito", productosCarrito);
			request.getRequestDispatcher("WEB-INF/Carrito.jsp").forward(request, response);
		} else {
			sesion.setAttribute("carritoVacio", "El carrito esta vacio");
			request.getRequestDispatcher("WEB-INF/Carrito.jsp").forward(request, response);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		response.sendRedirect("ConfirmarComprar");
	}

}
