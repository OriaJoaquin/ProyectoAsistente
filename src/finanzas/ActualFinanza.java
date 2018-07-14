package finanzas;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

public class ActualFinanza extends Finanza {
		
	@Override
	public Stock getStock(String simbolo) {
		
		Stock stock = new Stock(simbolo);
		
		URL dirStock = null;
		try {
			dirStock = new URL("https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=" + simbolo + "&interval=15min&outputsize=compact&apikey=XNH1KBXXQCRQ4BHN&datatype=csv");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		Scanner s = null;
		try {
			s = new Scanner(dirStock.openStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		List<String> datos = new ArrayList<String>();
		int campo = 0;
		while(s.hasNextLine() && campo < 3) {
			datos.add(s.nextLine());
			campo++;
		}
		s.close();
		
		String[] datosHoy = datos.get(1).split(","); 
		String[] datosAyer = datos.get(2).split(","); 
		
		stock.setValorAccion(Float.parseFloat(datosHoy[4]));
		stock.setVariacion((Float.parseFloat(datosHoy[4]) - Float.parseFloat(datosAyer[4])) / Float.parseFloat(datosAyer[4]) * 100);
		
		return stock;
	}

	@Override
	public Exchange getExchangeRate(String monedaDesde, String monedaA) {
		
		Exchange ex = new Exchange(monedaDesde, monedaA);
		
		URL dirExchange = null;
		try {
			dirExchange = new URL("https://www.alphavantage.co/query?function=CURRENCY_EXCHANGE_RATE&from_currency=" + monedaDesde + "&to_currency=" + monedaA + "&apikey=XNH1KBXXQCRQ4BHN");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		Scanner s = null;
		try {
			s = new Scanner(dirExchange.openStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String datos = "";
		int campo = 0;
		while(s.hasNextLine()) {
			String aux = s.nextLine();
			if(campo != 1 && campo != 9)
				datos += aux + "\n";
			campo++;
		}
		s.close();
		
		InputStream targetStream = new ByteArrayInputStream(datos.getBytes());
		JsonNode rootNode = null;
		try {
			rootNode = new ObjectMapper().readTree(new JsonFactory().createJsonParser(new InputStreamReader(targetStream)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ex.setCotizacion(Float.parseFloat(rootNode.path("5. Exchange Rate").getTextValue()));
		
		return ex;
	}

}


































