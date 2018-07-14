package finanzas;

public abstract class Finanza {
	
	public abstract Stock getStock(String simbolo);
	public abstract Exchange getExchangeRate(String monedaDesde, String monedaA);
}
