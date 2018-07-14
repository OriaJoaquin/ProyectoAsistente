package finanzas;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import atencion.Atencion;

public class DatoFinanza {			//atencion

	//private Atencion siguiente;
	private HashMap<String, String> monedas = new HashMap<String, String>(); 
	
	public DatoFinanza() {
		monedas.put("pesos", "ARS");
		monedas.put("peso", "ARS");
		monedas.put("pesos argentinos", "ARS");
		monedas.put("peso argentino", "ARS");
		monedas.put("dolares", "USD");
		monedas.put("dolar", "USD");
		monedas.put("euros", "EUR");
		monedas.put("euro", "EUR");
		monedas.put("pesos chilenos", "CLP");
		monedas.put("peso chileno", "CLP");
		monedas.put("pesos uruguayos", "UYU");
		monedas.put("peso uruguayo", "UYU");
		monedas.put("yenes", "CNY");
		monedas.put("yen", "CNY");
		monedas.put("rublos", "RUB");
		monedas.put("rublo", "RUB");
		monedas.put("libras", "GBP");
		monedas.put("libra", "GBP");
		monedas.put("reales", "BRL");
		monedas.put("real", "BRL");
	}
	
	/*public void establecerSiguiente(Atencion siguiente) {
		this.siguiente = siguiente;
		
	}*/
	
	public String atender(String mensaje, String nombreAsistente, String nombreUsuario) {
		String consulta = mensaje.toLowerCase();
		final String regex = "(cotizacion|valor) (del|de la|de las acciones de) (.*)";
		final Pattern pattern = Pattern.compile(regex);
		final Matcher matcher = pattern.matcher(consulta);
		
		if(matcher.find()) {
//			System.out.println(matcher.group(1) + " " + matcher.group(2) + " " + matcher.group(3));
			
			//Finanza f = new ActualFinanza();				//CAMBIAR ESTO POR MockFinanza para los tests
			Finanza f = new MockFinanza();
			try {
				if(matcher.group(2).contains("acciones")) {
					Stock s = f.getStock(matcher.group(3));
					return s.toString();
				} else if(matcher.group(2).contains("del") || matcher.group(2).contains("de la")) {
					Exchange e = f.getExchangeRate(monedas.get(matcher.group(3)), "ARS");
					return e.toString();
				}
			} catch(Exception e) {
				return "Error obteniendo la informacion.";
			}
		}
		
		return "no";
		//return siguiente.atender(mensaje, nombreAsistente, nombreUsuario);
	}
}
