package utiles;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import modeloTFG.ClienteVO;
import modeloTFG.ProductoVO;

/**
 * Servlet implementation class CargarImagenProducto
 */
@WebServlet("/CargarImagenProducto")
public class CargarImagenProducto extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		// Variables

		HttpSession sesion;

		sesion = request.getSession();

		// Cargamos el producto para coger la imagen y cargarla

		ProductoVO producto;

		producto = (ProductoVO) sesion.getAttribute("producto");

		if (producto != null) {

			byte[] buf = null;

			byte[] imagen;

			imagen = producto.getImagen();

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
		// TODO Auto-generated method stub
	}

}
