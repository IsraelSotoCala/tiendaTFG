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

import modeloTFG.ClienteVO;
import modeloTFG.ProductoVO;

/**
 * Servlet implementation class CargarImagenClientes
 */
@WebServlet("/CargarImagenClientes")
public class CargarImagenClientes extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		//Variables
		
		HttpSession sesion;
		
		sesion = request.getSession();
		
		//Recogemos la lista de clientes
		
		ArrayList<ClienteVO> clientes;
		
		clientes = (ArrayList<ClienteVO>) sesion.getAttribute("clientes");
		
		if(clientes!=null) {
		
		byte [] buf = null;
		
		byte [] imagen = null;
		
		//Sacamos la imagen individual y la cargamos
		
		imagen = clientes.get(Integer.parseInt(request.getParameter("cli"))).getImagen();
		
		buf = imagen;
		
		OutputStream out;
		
		response.setContentType("image /");
		
		out = response.getOutputStream();
		
		if(buf!=null) {
			out.write(buf);
		}
		
		out.flush();
		} else {
			System.out.println("Cliente sin imagen");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
