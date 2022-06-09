package paginas;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
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
import miBibliotecaGeneral.Encriptado;
import modeloTFG.ClienteDAO;
import modeloTFG.ClienteVO;

/**
 * Servlet implementation class EditarPerfil
 */
@WebServlet("/EditarPerfil")
public class EditarPerfil extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	HttpSession sesion;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		sesion = request.getSession();
		
		ClienteVO cliente;
		
		cliente = (ClienteVO) sesion.getAttribute("cliente");
		
		if(cliente != null) {
			request.getRequestDispatcher("WEB-INF/EditarPerfil.jsp").forward(request, response);
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
		
		Encriptado encriptar;
		
		encriptar = new Encriptado();
		
		ClienteDAO clientes;
		
		ClienteVO cliente;
		
		cliente = (ClienteVO) sesion.getAttribute("cliente");
		
		ClienteVO clienteEditado;
		
		int id; 
		
		id = cliente.getId();
		
		//Definimos los campos del formulario
		
		String nombre = "";
		
		String direccion = "";
		
		String localidad = "";
		
		String provincia = "";
		
		String codigoPostal = "";
		
		String correo = "";
		
		String contrasenia = "";
		
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
	    			
	    			if(fi.getFieldName().equals("nombre")) { 
	    				nombre = fi.getString("UTF-8");
	    				
	    				if(nombre.equals("")) {
	    					nombre = cliente.getNombre();
	    				}
	    			}
	    			
	    			if(fi.getFieldName().equals("direccion")) {
	    				direccion = fi.getString("UTF-8");
	    				
	    				if(direccion.equals("")) {
	    					direccion = cliente.getDireccion();
	    				}
	    			}
	    			
	    			if(fi.getFieldName().equals("localidad")) {
	    				localidad = fi.getString("UTF-8");
	    				
	    				if(localidad.equals("")) {
	    					localidad = cliente.getLocalidad();
	    				}
	    			}
	    			
	    			if(fi.getFieldName().equals("provincia")) {
	    				provincia = fi.getString("UTF-8");
	    				
	    				if(provincia.equals("")) {
	    					provincia = cliente.getProvincia();
	    				}
	    			}
	    			
	    			if(fi.getFieldName().equals("codigopostal")) {
	    				codigoPostal = fi.getString("UTF-8");
	    				
	    				if(codigoPostal.equals("")) {
	    					codigoPostal = cliente.getCodigoPostal();
	    				}
	    			}
	    			
	    			if(fi.getFieldName().equals("correo")) { 
	    				correo = fi.getString("UTF-8");
	    				
	    				if(correo.equals("")) {
	    					correo = cliente.getCorreo();
	    				}
	    			}
	    			
	    			if(fi.getFieldName().equals("contrasenia")) {
	    				contrasenia = fi.getString("UTF-8");
	    				
	    				if(contrasenia.equals("")) {
	    					contrasenia = cliente.getPassword();
	    				} else {
	    					contrasenia = encriptar.generar(fi.getString("UTF-8"));
	    				}
	    			}
	    			
	    			if(fi.getFieldName().equals("telefono")) {
	    				telefono = fi.getString("UTF-8");
	    				
	    				if(telefono.equals("")) {
	    					telefono = cliente.getTelefono();
	    				}
	    			}
	    			
	    			
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
	    
	    //Creamos un nuevo clienteVO que será el que pasemos a la funcion de editar
	    
	    clienteEditado = new ClienteVO(nombre, direccion, localidad, provincia, codigoPostal, correo, contrasenia, telefono, imagen);
	    
	    clientes = new ClienteDAO();
	    
	    if(clientes.modificarCliente(clienteEditado, id) == true) {
	    	//Una vez modificado el cliente se le envia de vuelta la página principal
	    	response.sendRedirect("PaginaPrincipal");
	    } else {
	    	sesion.setAttribute("perfilEditado", "Fallo al editar los datos del perfil");
	    	response.sendRedirect("EditarPerfil");
	    }
		
	}

}
