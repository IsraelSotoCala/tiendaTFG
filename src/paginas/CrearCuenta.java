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

import miBibliotecaGeneral.CadenaAleatoria;
import miBibliotecaGeneral.ConversionArchivo;
import miBibliotecaGeneral.Correo;
import miBibliotecaGeneral.Encriptado;
import modeloTFG.ClienteDAO;
import modeloTFG.ClienteVO;

/**
 * Servlet implementation class CrearCuenta
 */
@WebServlet("/CrearCuenta")
public class CrearCuenta extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("WEB-INF/CrearCuenta.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		HttpSession sesion;
		
		sesion = request.getSession();
		
		Correo enviarCorreo;
		
		enviarCorreo = new Correo();
		
		CadenaAleatoria codigoAleatorio;
		
		codigoAleatorio = new CadenaAleatoria();
		
		String codigoEnvio;
		
		Encriptado encriptar;
		
		encriptar = new Encriptado();
		
		ClienteDAO clientes;
		
		clientes = new ClienteDAO();
		
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
	    			
	    			if(fi.getFieldName().equals("nombre")) nombre = fi.getString("UTF-8");
	    			if(fi.getFieldName().equals("direccion")) direccion = fi.getString("UTF-8");
	    			if(fi.getFieldName().equals("localidad")) localidad = fi.getString("UTF-8");
	    			if(fi.getFieldName().equals("provincia")) provincia = fi.getString("UTF-8");
	    			if(fi.getFieldName().equals("codigopostal")) codigoPostal = fi.getString("UTF-8");
	    			if(fi.getFieldName().equals("correo")) correo = fi.getString("UTF-8");
	    			if(fi.getFieldName().equals("contrasenia")) contrasenia = encriptar.generar(fi.getString("UTF-8"));
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
	    
	    //Consultamos en la bd para saber si ya existe un usuario con el correo introducido
	    
	    ClienteVO cliente;
	    
	    cliente = clientes.consultar(correo);
	    
	    //Si existe un cliente con ese correo damos error
	    if(cliente != null) {
	    	sesion.setAttribute("creada", "Error fallo al crear la cuenta");
	    	response.sendRedirect("CrearCuenta");
	    } else {
	    	//Creamos un clienteVO para introducirlo en la bd
	    	cliente = new ClienteVO(nombre,direccion,localidad,provincia,codigoPostal,correo,contrasenia,telefono,imagen);
	    	
	    	if(clientes.guardarCliente(cliente) == true) {
	    		//Si se crea correctamente,enviamos el email con el codigo
	    		codigoEnvio = codigoAleatorio.generar();
	    		enviarCorreo.enviar(correo, codigoEnvio);
	    		clientes.guardarCodigo(correo, codigoEnvio);
	    		response.sendRedirect("LoginClientes");
	    	} else {
	    		sesion.setAttribute("creada", "Fallo al crear la cuenta");
	    		response.sendRedirect("CrearCuenta");
	    	}
	    }
	    
	}

}
