package paginas;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import modeloTFG.ClienteVO;
import modeloTFG.PedidoDAO;
import modeloTFG.PedidoVO;
import modeloTFG.PedidosEfectuadosDAO;
import modeloTFG.PedidosEfectuadosVO;
import modeloTFG.ProductoVO;

/**
 * Servlet implementation class ConfirmarCompra
 */
@WebServlet("/ConfirmarCompra")
public class ConfirmarCompra extends HttpServlet {
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

		ClienteVO cliente;

		cliente = (ClienteVO) sesion.getAttribute("cliente");

		if (cliente != null) {

			ProductoVO producto;

			int idProducto;

			producto = (ProductoVO) sesion.getAttribute("producto");

			if (producto != null) {

				idProducto = producto.getId();

				boolean productoAnanido;

				productoAnanido = false;

				// Creamos un array list de productos que contendrá los productos del carrito

				ArrayList<ProductoVO> productosCarrito;

				productosCarrito = (ArrayList<ProductoVO>) sesion.getAttribute("carrito");

				// Si no existia el carrito creamos uno nuevo si no lo cogemos del atributo de
				// sesion carrito

				if (productosCarrito == null) {
					productosCarrito = new ArrayList<ProductoVO>();
				}

				if (productosCarrito.size() > 0) {
					for (ProductoVO p : productosCarrito) {
						if (idProducto == p.getId()) {
							productoAnanido = true;
							break;
						}
					}
				}

				if (!productoAnanido) {
					productosCarrito.add(producto);
				}

				sesion.setAttribute("carrito", productosCarrito);
				request.getRequestDispatcher("WEB-INF/ConfirmarCompra.jsp").forward(request, response);
			} else {
				request.getRequestDispatcher("WEB-INF/ConfirmarCompra.jsp").forward(request, response);
			}
		} else {
			sesion.setAttribute("inicioSesion", "Debe iniciar sesion para realizar una compra");
			response.sendRedirect("Carrito");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		//Variables
		
		ByteArrayOutputStream baos;

		Document documento;

		Paragraph titulo;

		sesion = request.getSession();

		PedidoVO pedido;

		PedidoVO pedidoRealizado;

		PedidoDAO pedDAO;

		ClienteVO cliente;

		String clienteNombre;

		String correoCliente;

		Double precioTotal = 0.0;

		String infoProd = "";

		ArrayList<ProductoVO> productos;

		int idCliente;

		Date fechaEntrega;

		PedidosEfectuadosVO pedidoEfectuado = null;

		PedidosEfectuadosDAO pfDAO;
		
		//Cargamos el atributo cliente para recoger la informacion que necesitamos

		cliente = (ClienteVO) sesion.getAttribute("cliente");

		correoCliente = cliente.getCorreo();

		clienteNombre = cliente.getNombre();

		idCliente = cliente.getId();

		//Definimos la fecha de entrega, y calculamos el precio de la compra
		
		fechaEntrega = new Date(System.currentTimeMillis() + 259200000);

		productos = (ArrayList<ProductoVO>) sesion.getAttribute("carrito");

		for (int i = 0; i < productos.size(); i++) {
			precioTotal = precioTotal + productos.get(i).getPrecio();
		}

		//Variables del pdf
		
		documento = new Document();

		baos = new ByteArrayOutputStream();

		titulo = new Paragraph("Factura");

		// Creamos el pdf

		try {
			PdfWriter.getInstance(documento, baos);
			documento.open();
			documento.add(titulo);

			documento.add(new Paragraph("Cliente: " + clienteNombre));

			documento.add(Chunk.NEWLINE);

			Paragraph texto;

			for (int i = 0; i < productos.size(); i++) {

				infoProd += ("El producto " + productos.get(i).getNombre() + " por: " + productos.get(i).getPrecio()
						+ "€ \n");

			}

			texto = new Paragraph("Usted ha comprado los siguientes productos por un valor de: " + precioTotal + "€ \n"
					+ "Los siguientes productos: \n" + infoProd);

			texto.setAlignment(Element.ALIGN_JUSTIFIED);

			documento.add(texto);

			documento.close();

			System.out.println("PDF creado");

			//Este trozo de codigo comentado hace que se te descargue el pdf 
			
			// response.setContentType("application/pdf");
			// the contentlength
//			response.setContentLength(baos.size());
//			// write ByteArrayOutputStream to the ServletOutputStream
//			OutputStream os = response.getOutputStream();
//			baos.writeTo(os);
//			os.flush();
//			os.close();

		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Propiedades del correo y conexion al correo de yahoo

		final String from = "lamazmorradelsopas@yahoo.com";

		String host = "smtp.mail.yahoo.com";
		Properties properties = System.getProperties();

		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.auth", "true");
		properties.setProperty("mail.imap.partialfetch", "false");
		properties.put("mail.smtp.ssl.trust", "smtp.mail.yahoo.com");

		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("lamazmorradelsopas", "vjdhdnhumyiibwgp");
			}
		});

		try {

			// Texto
			MimeBodyPart texto = new MimeBodyPart();

			texto.setText(
					"Usted ha realizado una compra en nuestra tienda online. \n" + "Aqui tiene usted su factura: ");

			byte[] bytes = baos.toByteArray();

			baos.write(bytes, 0, bytes.length);

			DataSource dataSource = new ByteArrayDataSource(bytes, "application/pdf");
			MimeBodyPart contenidoPDF = new MimeBodyPart();
			contenidoPDF.setDataHandler(new DataHandler(dataSource));
			contenidoPDF.setFileName("factura.pdf");

			// Construimos el cuerpo del mensaje con el texto y el pdf
			MimeMultipart mimeMultipart = new MimeMultipart();
			mimeMultipart.addBodyPart(texto);
			mimeMultipart.addBodyPart(contenidoPDF);

			// Construimos el mensaje
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(correoCliente));
			message.setSubject("Factura");
			message.setContent(mimeMultipart);

			System.out.println("enviando...");
			Transport.send(message);
			System.out.println("Mensaje enviado con exito....");
			try {
				baos.flush();
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			try {
				baos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
		
		//Creamos el pedido y lo añadimos

		pedido = new PedidoVO(fechaEntrega, "En camino", idCliente);

		pedDAO = new PedidoDAO();
		
		//Si se guarda el pedido, guardamos los productos del pedido, limpiamos el carrito y enviamos a los pedidos

		if (pedDAO.guardarPedido(pedido) == true) {

			pedidoRealizado = pedDAO.consultarPedido(idCliente);

			pfDAO = new PedidosEfectuadosDAO();

			for (int i = 0; i < productos.size(); i++) {

				pedidoEfectuado = new PedidosEfectuadosVO(productos.get(i).getId(), pedidoRealizado.getId(), idCliente);

				pfDAO.guardarPedidoEfectuados(pedidoEfectuado);
			}

			productos.clear();

			sesion.setAttribute("carrito", productos);

			response.sendRedirect("DetallesPedido");

		} else {
			//Si no mostramos error
			response.sendRedirect("ConfirmarCompra");
			sesion.setAttribute("compraRealizada", "Ha ocurrido un error al procesar su compra");
		}

	}

}
