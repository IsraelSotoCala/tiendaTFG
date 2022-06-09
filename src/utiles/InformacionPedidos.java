package utiles;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;

import modeloTFG.ProductoVO;

public class InformacionPedidos implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	ArrayList<ProductoVO> productos;
	
	String estado;
	
	Date fechaEntrega;

	public InformacionPedidos() {
	}

	public InformacionPedidos(ArrayList<ProductoVO> productos, String estado, Date fechaEntrega) {
		this.productos = productos;
		this.estado = estado;
		this.fechaEntrega = fechaEntrega;
	}

	public ArrayList<ProductoVO> getProductos() {
		return productos;
	}

	public void setProductos(ArrayList<ProductoVO> productos) {
		this.productos = productos;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFechaEntrega() {
		return fechaEntrega;
	}

	public void setFechaEntrega(Date fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

	@Override
	public String toString() {
		return "InformacionPedidos [productos=" + productos + ", estado=" + estado + ", fechaEntrega=" + fechaEntrega
				+ "]";
	}
	
}
