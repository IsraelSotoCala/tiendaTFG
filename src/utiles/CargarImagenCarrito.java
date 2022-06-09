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
 * Servlet implementation class CargarImagenCarrito
 */
@WebServlet("/CargarImagenCarrito")
public class CargarImagenCarrito extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		//Variables
		
		HttpSession sesion;
		
		sesion = request.getSession();
		
		//Recogemos los productos del carrito
		
		ArrayList<ProductoVO> productos;
		
		productos = (ArrayList<ProductoVO>) sesion.getAttribute("carrito");
		
		if(productos!=null) {
		
		byte [] buf = null;
		
		byte [] imagen = null;
		
		//Recogemos la imagen de cada carrito en especifico y la cargamos
		
		imagen = productos.get(Integer.parseInt(request.getParameter("prod"))).getImagen();
		
		buf = imagen;
		
		OutputStream out;
		
		response.setContentType("image /");
		
		out = response.getOutputStream();
		
		if(buf!=null) {
			out.write(buf);
		}
		
		out.flush();
		} else {
			System.out.println("Producto del carrito sin foto");
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

}
