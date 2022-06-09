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

import modeloTFG.ProductoDAO;
import modeloTFG.ProductoVO;

/**
 * Servlet implementation class CargarImagenPedidos
 */
@WebServlet("/CargarImagenPedidos")
public class CargarImagenPedidos extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		//Variables
		
		HttpSession sesion;
		
		sesion = request.getSession();
		
		int idProducto;
		
		ProductoDAO pDAO;
		
		pDAO = new ProductoDAO();
		
		byte [] buf = null;
		
		byte [] imagen = null;
		
		//Recogemos el id del producto y cargamos el producto con ese id para coger la imagen y cargarla
		
		idProducto = Integer.parseInt(request.getParameter("prod"));
		
		imagen = pDAO.consultar(idProducto).getImagen();
		
		if(imagen !=null) {
	
		buf = imagen;
		
		OutputStream out;
		
		response.setContentType("image /");
		
		out = response.getOutputStream();
		
		if(buf!=null) {
			out.write(buf);
		}
		
		out.flush();
		} else {
			System.out.println("Producto de pedido sin imagen");
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
