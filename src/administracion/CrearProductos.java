package administracion;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.text.DateFormatter;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import miBibliotecaGeneral.ConversionArchivo;
import modeloTFG.AdministradorVO;
import modeloTFG.ProductoDAO;
import modeloTFG.ProductoVO;

/**
 * Servlet implementation class CrearProductos
 */
@WebServlet("/CrearProductos")
public class CrearProductos extends HttpServlet {
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
			request.getRequestDispatcher("WEB-INF/CrearProductos.jsp").forward(request, response);
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

		pDAO = new ProductoDAO();

		// Definimos los campos del formulario

		String nombre = "";

		String descripcion = "";

		Date fechaLanzamiento;
		
		fechaLanzamiento = null;

		Double precio = null;

		byte[] imagen = null;

		int categoria;

		categoria = 0;

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
					if (fi.getFieldName().equals("fechaLanzamiento"))
						fechaLanzamiento = Date.valueOf(fi.getString("UTF-8"));
					if (fi.getFieldName().equals("precio"))
						precio = new Double(fi.getString("UTF-8"));
					if (fi.getFieldName().equals("categoria"))
						categoria = Integer.parseInt(fi.getString("UTF-8"));
					

	
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
		
		//Creamos el producto
		
		ProductoVO producto;
		
		producto = new ProductoVO(nombre, descripcion, fechaLanzamiento, precio, imagen, categoria);
		
		if(pDAO.guardarProducto(producto) == true) {
			sesion.setAttribute("productoCreado", "Producto creado con éxito");
			response.sendRedirect("CrearProductos");
		} else {
			sesion.setAttribute("productoCreado", "Fallo al crear el producto,asegurese que la categoría exista");
			response.sendRedirect("CrearProductos");
		}

	}

}
