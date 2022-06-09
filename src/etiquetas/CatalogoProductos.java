package etiquetas;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class CatalogoProductos extends SimpleTagSupport{
	
	String nombre;
	
	String descripcion;
	
	Double precio;
	
	Date fechaLanzamiento;
	
	int posicion;
	
	@Override
	public void doTag() throws JspException, IOException {
		JspWriter out = getJspContext().getOut();
		
		out.print("<li class='list-group-item'>");
		out.print("<img alt='Imagen de producto' src='CargarImagenProductos?prod="+getPosicion()+"' width='100px'>");
		out.print("<div class='container-fluid'>");
		out.print("<span><b>Nombre:</b> " + getNombre() + "</span>");
		out.print("<span><b>Descripcion:</b> " + getDescripcion() + "</span>");
		out.print("<span><b>Precio:</b> " + getPrecio() + "$ </span>");
		out.print("<span><b>Fecha de lanzamiento:</b> " + getFechaLanzamiento() + " </span>");
		
		out.print("<a href='DetallesProducto?prod="+getPosicion()+"'>Ver Detalles</a>");
		out.print("</div>");
		out.print("</li>");
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public Date getFechaLanzamiento() {
		return fechaLanzamiento;
	}

	public void setFechaLanzamiento(Date fechaLanzamiento) {
		this.fechaLanzamiento = fechaLanzamiento;
	}

	public int getPosicion() {
		return posicion;
	}

	public void setPosicion(int posicion) {
		this.posicion = posicion;
	}
}
