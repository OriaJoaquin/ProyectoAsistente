package finanzas;

public class Exchange {
	
	private String monedaDesde;
	@SuppressWarnings("unused")
	private String monedaA;
	private float cotizacion;
	
	public Exchange(String monedaDesde, String monedaA) {
		this.monedaDesde = monedaDesde;
		this.monedaA = monedaA;
	}
	
	public String getMonedaDesde() {
		return this.monedaDesde;
	}
	
	public void setCotizacion(float cotizacion) {
		this.cotizacion = cotizacion;
	}
	
	@Override
	public String toString() {
		return "Cotizacion del " + this.monedaDesde + ": " + this.cotizacion + " " + this.monedaA + "\n";
	}
	
}
