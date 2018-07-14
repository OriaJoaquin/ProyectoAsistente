package finanzas;

public class Main {

	public static void main(String[] args) {

		//Finanza f = new ActualFinanza();
		DatoFinanza df = new DatoFinanza();
		
		//System.out.println(f.getStock("MSFT"));
		//System.out.println(f.getExchangeRate("GBP", "ARS"));
		
		System.out.println(df.atender("Cotizacion de las acciones de MSFT", "@jenkins", "@daniel"));
		
	}

}
