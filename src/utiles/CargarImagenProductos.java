package utiles;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import modeloTFG.ProductoVO;

/**
 * Servlet implementation class CargarImagenProductos
 */
@WebServlet("/CargarImagenProductos")
public class CargarImagenProductos extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//Variables

		HttpSession sesion;

		sesion = request.getSession();
		
		//Cargamos la lista de productos para coger la imagen y cargarla

		ArrayList<ProductoVO> productos;

		productos = (ArrayList<ProductoVO>) sesion.getAttribute("productos");

		if (productos != null) {
			byte[] buf = null;

			byte[] imagen = null;

			imagen = productos.get(Integer.parseInt(request.getParameter("prod"))).getImagen();

			buf = imagen;

			OutputStream out;

			response.setContentType("image /");

			out = response.getOutputStream();

			if (buf != null) {
				out.write(buf);
			}

			out.flush();
		} else {
			System.out.println("Producto sin imagen");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}
