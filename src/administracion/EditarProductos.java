package administracion;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import miBibliotecaGeneral.ConversionArchivo;
import modeloTFG.AdministradorVO;
import modeloTFG.ProductoDAO;
import modeloTFG.ProductoVO;

/**
 * Servlet implementation class EditarProductos
 */
@WebServlet("/EditarProductos")
public class EditarProductos extends HttpServlet {
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

		AdministradorVO admin;

		admin = (AdministradorVO) sesion.getAttribute("admin");

		// Solo se puede acceder a la página si eres admin y del rol de productos
		if (admin != null && admin.getRol().equals("productos")) {
			
			//Hacemos que los valores anteriores aparezcan en el formulario
			
			ArrayList<ProductoVO> productos;
			
			productos = (ArrayList<ProductoVO>) sesion.getAttribute("productos");
			
			sesion.setAttribute("nombre", productos.get(Integer.parseInt(request.getParameter("prod"))).getNombre());
			
			sesion.setAttribute("descripcion", productos.get(Integer.parseInt(request.getParameter("prod"))).getDescripcion());
			
			sesion.setAttribute("fechaLanzamiento", productos.get(Integer.parseInt(request.getParameter("prod"))).getFechaLanzamiento());
			
			sesion.setAttribute("precio", productos.get(Integer.parseInt(request.getParameter("prod"))).getPrecio());
			
			sesion.setAttribute("categoria", productos.get(Integer.parseInt(request.getParameter("prod"))).getIdCategoria());
			
			request.getRequestDispatcher("WEB-INF/EditarProductos.jsp").forward(request, response);
		} else {
			response.sendRedirect("LoginEmpleados");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		sesion = request.getSession();

		ProductoDAO pDAO;

		ProductoVO producto;

		ProductoVO productoEditado;
		
		ArrayList<ProductoVO> productos;
		
		productos = (ArrayList<ProductoVO>) sesion.getAttribute("productos");
		
		AdministradorVO admin;

		admin = (AdministradorVO) sesion.getAttribute("admin");

		// Definimos los campos del formulario del que extraeremos los datos

		int id = productos.get(Integer.parseInt(request.getParameter("prod"))).getId();

		String nombre = "";

		String descripcion = "";

		Date fechaLanzamiento;
		
		fechaLanzamiento = productos.get(Integer.parseInt(request.getParameter("prod"))).getFechaLanzamiento();
		
		Date fechaIntroducida = null;

		Double precio = 0.0;

		int categoria = 0;

		int idAdministrador = admin.getId();

		byte[] imagen = null;

		// Recogemos los valores del formulario

		File file = null;
		int maxFileSize = 5000 * 1024;
		int maxMemSize = 5000 * 1024;
		ServletContext servletContext;
		String filePath;
		String contentType;
		PrintWriter out;
		FileItem fi;
		List<FileItem> fileItems;
		DiskFileItemFactory factory;
		String fileName = null;
		boolean isInMemory;
		String fieldName;
		long sizeInBytes;

		ConversionArchivo convertirArchivo;

		convertirArchivo = new ConversionArchivo();

		servletContext = getServletContext();
		filePath = this.getServletContext().getRealPath("/WEB-INF/data/");
		out = response.getWriter();
		contentType = request.getContentType();

		if ((contentType.indexOf("multipart/form-data") >= 0)) {

			factory = new DiskFileItemFactory();
			factory.setSizeThreshold(maxMemSize);
			factory.setRepository(new File("."));

			ServletFileUpload upload = new ServletFileUpload(factory);

			upload.setSizeMax(maxFileSize);

			try {

				fileItems = upload.parseRequest(request);
				Iterator<FileItem> i = fileItems.iterator();

				while (i.hasNext()) {
					fi = (FileItem) i.next();

					if (fi.getFieldName().equals("nombre"))
						nombre = fi.getString("UTF-8");
					if (fi.getFieldName().equals("descripcion"))
						descripcion = fi.getString("UTF-8");
					if(fi.getFieldName().equals("precio"))
						precio = Double.parseDouble(fi.getString("UTF-8"));
					if (fi.getFieldName().equals("categoria"))
						categoria = Integer.parseInt(fi.getString("UTF-8"));
					if (fi.getFieldName().equals("fechaLanzamiento"))
						fechaIntroducida = Date.valueOf(fi.getString("UTF-8"));
					if (!fi.isFormField()) {
						fieldName = fi.getFieldName();
						fileName = fi.getName();
						isInMemory = fi.isInMemory();
						sizeInBytes = fi.getSize();

						if (fileName.lastIndexOf("/") >= 0) {
							file = new File(filePath + fileName.substring(fileName.lastIndexOf("/")));
						} else
							file = new File(filePath + "/" + fileName.substring(fileName.lastIndexOf("/") + 1));

						fi.write(file);

						imagen = convertirArchivo.convertirArchivoABytes(file);

					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		pDAO = new ProductoDAO();

		producto = pDAO.consultar(id);

		if (nombre.equals("")) {
			nombre = producto.getNombre();
		}
		
		if (descripcion.equals("")) {
			descripcion = producto.getDescripcion();
		}

		if (fechaIntroducida != null) {
			fechaLanzamiento = fechaIntroducida;
		} else {
			
		}
			
		if (precio == 0.0) {
			precio = producto.getPrecio();
		}

		if (categoria == 0 || categoria < 0) {
			categoria = producto.getIdCategoria();
		}

		if (idAdministrador == 0 || idAdministrador < 0) {
			idAdministrador = producto.getIdAdministrador();
		}

		if (imagen == null) {
			imagen = producto.getImagen();
		}

		// Editamos el producto

		productoEditado = new ProductoVO(nombre, descripcion, fechaLanzamiento, precio, imagen, categoria,
				idAdministrador);

		if (pDAO.modificarProducto(productoEditado, id) == true) {
			sesion.setAttribute("productoEditado", "Producto editado con éxito");
			response.sendRedirect("ListarProductos");
		} else {
			sesion.setAttribute("productoEditado", "Fallo al editar el producto");
			response.sendRedirect("EditarProductos");
		}
	}

}
