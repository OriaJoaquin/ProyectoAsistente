package finanzas;

public class Stock {
	
	private String simbolo;
	private float valorAccion;
	private float variacion;
	
	public Stock(String simbolo) {
		this.simbolo = simbolo;
	}
	
	public String getSimbolo() {
		return this.simbolo;
	}
	
	public void setValorAccion(float valorAccion) {
		this.valorAccion = valorAccion;
	}
	
	public void setVariacion(float variacion) {
		this.variacion = variacion;
	}

	@Override
	public String toString() {
		return "Acciones de " + this.simbolo + ":\n" + "Valor de la accion: " + this.valorAccion + " USD\nVariacion: " + this.variacion + "%\n";
	}
 
	
 }
