package finanzas;

public class MockFinanza extends Finanza {

	@Override
	public Stock getStock(String simbolo) {
		
		Stock stock = new Stock(simbolo);
		
		stock.setValorAccion(100);
		stock.setVariacion((float) 10.5);
		
		return stock;
	}

	@Override
	public Exchange getExchangeRate(String monedaDesde, String monedaA) {
		
		Exchange ex = new Exchange(monedaDesde, monedaA);
		
		ex.setCotizacion(30);
		
		return ex;
	}

}
