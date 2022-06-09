package paginas;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import modeloTFG.ClienteVO;
import modeloTFG.PedidoDAO;
import modeloTFG.PedidoVO;
import modeloTFG.ProductoDAO;
import modeloTFG.ProductoVO;
import utiles.InformacionPedidos;

/**
 * Servlet implementation class DetallesPedido
 */
@WebServlet("/DetallesPedido")
public class DetallesPedido extends HttpServlet {
	private static final long serialVersionUID = 1L;

	HttpSession sesion;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		// Variables

		sesion = request.getSession();

		ClienteVO cliente;

		int idCliente;

		ProductoDAO prodDAO;

		ArrayList<ProductoVO> productos;

		PedidoDAO pedDAO;

		ArrayList<PedidoVO> pedidos;

		ArrayList<InformacionPedidos> infoPedidos;

		InformacionPedidos info;

		// Cogemos el atributo de sesion de cliente y cargamos sus pedidos

		infoPedidos = new ArrayList<InformacionPedidos>();

		cliente = (ClienteVO) sesion.getAttribute("cliente");

		if (cliente != null) {
			
			idCliente = cliente.getId();

			prodDAO = new ProductoDAO();

			pedDAO = new PedidoDAO();

			pedidos = pedDAO.consultarPedidosCliente(idCliente);

			// Añadimos un nuevo objeto de la clase que tenemos para mostrar los pedidos

			for (int i = 0; i < pedidos.size(); i++) {
				info = new InformacionPedidos(prodDAO.consultarProductosPedidos(idCliente, pedidos.get(i).getId()),
						pedidos.get(i).getEstado(), pedidos.get(i).getFechaEntrega());
				infoPedidos.add(info);
			}

			// Si hay pedidos los enviamos para mostrar y si no enviamos un mensaje

			if (!infoPedidos.isEmpty()) {
				sesion.setAttribute("productosPedido", infoPedidos);
				request.getRequestDispatcher("WEB-INF/DetallesPedido.jsp").forward(request, response);
			} else {
				sesion.setAttribute("dataPedidos", "No tiene ningún pedido encargado ahora mismo");
				request.getRequestDispatcher("WEB-INF/DetallesPedido.jsp").forward(request, response);
			}
		} else {
			response.sendRedirect("PaginaPrincipal");
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
