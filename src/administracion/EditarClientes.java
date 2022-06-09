package administracion;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
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
import modeloTFG.ClienteDAO;
import modeloTFG.ClienteVO;

/**
 * Servlet implementation class EditarClientes
 */
@WebServlet("/EditarClientes")
public class EditarClientes extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	HttpSession sesion;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		sesion = request.getSession();
		
		AdministradorVO admin;
		
		admin = (AdministradorVO) sesion.getAttribute("admin");
		
		// Solo se puede acceder a la página si eres admin y tienes rol de clientes
		
		if(admin != null && admin.getRol().equals("clientes")) {
			
			//Hacemos que los valores aparezcan en el formulario
			
			ArrayList<ClienteVO> clientes;
			
			clientes = (ArrayList<ClienteVO>) sesion.getAttribute("clientes");
			
			sesion.setAttribute("nombre", clientes.get(Integer.parseInt(request.getParameter("cli"))).getNombre());
			
			sesion.setAttribute("direccion", clientes.get(Integer.parseInt(request.getParameter("cli"))).getDireccion());
			
			sesion.setAttribute("localidad", clientes.get(Integer.parseInt(request.getParameter("cli"))).getLocalidad());
			
			sesion.setAttribute("provincia", clientes.get(Integer.parseInt(request.getParameter("cli"))).getProvincia());
			
			sesion.setAttribute("codigoPostal", clientes.get(Integer.parseInt(request.getParameter("cli"))).getCodigoPostal());
			
			sesion.setAttribute("telefono", clientes.get(Integer.parseInt(request.getParameter("cli"))).getTelefono());
			
			request.getRequestDispatcher("WEB-INF/EditarClientes.jsp").forward(request, response);
		} else {
			response.sendRedirect("LoginClientes");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		sesion = request.getSession();
		
		ClienteDAO cDAO;
		
		ClienteVO cliente;
		
		ClienteVO clienteEditado;
		
		ArrayList<ClienteVO> clientes;
		
		clientes = (ArrayList<ClienteVO>) sesion.getAttribute("clientes");
		
		AdministradorVO admin;
		
		admin = (AdministradorVO) sesion.getAttribute("admin");
		
		//Definimos los campos del formulario del que extraeremos los datos
		
		int id = clientes.get(Integer.parseInt(request.getParameter("cli"))).getId();
		
		String nombre = "";
		
		String direccion = "";
		
		String localidad = "";
		
		String provincia = "";
		
		String codigoPostal = "";
		
		String correo = clientes.get(Integer.parseInt(request.getParameter("cli"))).getCorreo();
		
		String contrasenia = clientes.get(Integer.parseInt(request.getParameter("cli"))).getPassword();
		
		String telefono = "";
		
		byte[] imagen = null;
		
		//Obtenemos los campos del formulario
		
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
			    
			    if((contentType.indexOf("multipart/form-data") >= 0 )) {
			    	
			    	factory = new DiskFileItemFactory();
			    	factory.setSizeThreshold(maxMemSize);
			    	factory.setRepository(new File("."));
			    	

			    	ServletFileUpload upload = new ServletFileUpload(factory);
			    	
			    	upload.setSizeMax(maxFileSize);
			    	
			    	try {
			    		
			    		fileItems = upload.parseRequest(request);
			    		Iterator<FileItem> i = fileItems.iterator();
			    		
			    		while(i.hasNext()) {
			    			fi = (FileItem) i.next();
			    			
			    			if(fi.getFieldName().equals("nombre")) nombre = fi.getString("UTF-8");
			    			if(fi.getFieldName().equals("direccion")) direccion = fi.getString("UTF-8");
			    			if(fi.getFieldName().equals("localidad")) localidad = fi.getString("UTF-8");
			    			if(fi.getFieldName().equals("provincia")) provincia = fi.getString("UTF-8");
			    			if(fi.getFieldName().equals("codigopostal")) codigoPostal = fi.getString("UTF-8");
			    			if(fi.getFieldName().equals("telefono")) telefono = fi.getString("UTF-8");
			    			
			    			
			    			if(!fi.isFormField()) {
			    				fieldName = fi.getFieldName();
			    				fileName = fi.getName();
			    				isInMemory = fi.isInMemory();
			    				sizeInBytes = fi.getSize();
			    				
			    				if(fileName.lastIndexOf("/") >= 0) {
			    					file = new File(filePath+fileName.substring(fileName.lastIndexOf("/")));
			    				}else file = new File(filePath+"/"+fileName.substring(fileName.lastIndexOf("/")+1));
			    				
			    				fi.write(file);
			    				
			    				imagen = convertirArchivo.convertirArchivoABytes(file);
			    				
			    				
			    			}
			    		}
			    	}
			    	catch(Exception e) {
			    		e.printStackTrace();
			    	}
			    }
		
		
		
		cDAO = new ClienteDAO();
		
		cliente = cDAO.consultar(correo);
		
		if(nombre.equals("")) {
			nombre = cliente.getNombre();
		}
		
		if(direccion.equals("")) {
			direccion = cliente.getDireccion();
		}
		
		if(localidad.equals("")) {
			localidad = cliente.getLocalidad();
		}
		
		if(provincia.equals("")) {
			provincia = cliente.getProvincia();
		}
		
		if(codigoPostal.equals("")) {
			codigoPostal = cliente.getCodigoPostal();
		}
	
		if(telefono.equals("")) {
			telefono = cliente.getTelefono();
		}
		
		//Editamos al cliente
		
		clienteEditado = new ClienteVO(nombre, direccion, localidad, provincia, codigoPostal, correo, contrasenia, telefono, imagen);
		
		if(cDAO.modificarCliente(clienteEditado, id) == true) {
			sesion.setAttribute("clienteEditado", "Cliente editado con éxito");
			response.sendRedirect("ListarClientes");
		} else {
			sesion.setAttribute("clienteEditado", "Fallo al editar cliente");
			response.sendRedirect("EditarClientes");
		}
		
	}

}
