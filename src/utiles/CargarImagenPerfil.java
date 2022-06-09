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

/**
 * Servlet implementation class CargarImagenPerfil
 */
@WebServlet("/CargarImagenPerfil")
public class CargarImagenPerfil extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		//Variables
		
		HttpSession sesion;
		
		sesion = request.getSession();
		
		//Recogemos el cliente por el atributo de sesion
		
		ClienteVO cliente;
		
		byte [] buf = null;
		
		byte [] imagen;
		
		cliente = (ClienteVO) sesion.getAttribute("cliente");
		
		if(cliente!=null) {
		
		//Cargamos su imagen
		
		imagen = cliente.getImagen();
		
		buf = imagen;
		
		OutputStream out;
		
		response.setContentType("image /");
		
		out = response.getOutputStream();
		
		if(buf!=null) {
			out.write(buf);
		}
		
		out.flush();
		} else {
			//Si no tiene imagen o no esta logueado controlamos el error controlamos el error
			System.out.println("Cliente sin imagen o no logueado");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
