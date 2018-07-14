package wikipedia;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class InfoActual extends InfoGetter{

	public String getInfo(String infoABuscar,String nombreUsuario){

	String encoding = "UTF-8";

			try {
	
				Document google = Jsoup.connect("https://www.google.com.ar/search?q=" + URLEncoder.encode(infoABuscar + " wikipedia", encoding)).userAgent("Mozilla/5.0").get();

				String wikipediaURL = google.getElementsByTag("cite").get(0).text();

				String wikipediaApiJSON = "https://es.wikipedia.org/w/api.php?format=json&action=query&prop=extracts&exintro=&explaintext=&titles="
						+ URLEncoder.encode(wikipediaURL.substring(wikipediaURL.lastIndexOf("/") + 1, wikipediaURL.length()), encoding);
				
				HttpURLConnection httpcon = (HttpURLConnection) new URL(wikipediaApiJSON).openConnection();
				httpcon.addRequestProperty("User-Agent", "Mozilla/5.0");
				BufferedReader in = new BufferedReader(new InputStreamReader(httpcon.getInputStream()));
				
				String responseSB = in.lines().collect(Collectors.joining());
				in.close();
				
				if(!responseSB.contains("extract")) {
					google = Jsoup.connect("https://www.google.com.ar/search?q=" + URLEncoder.encode(infoABuscar, encoding)).userAgent("Mozilla/5.0").get();
					String googleURL = google.getElementsByTag("cite").get(0).text();
					respuesta = nombreUsuario + " encontré información en Google\n" + googleURL;	
				}
				else {
					String result = "\n";
					result += responseSB.split("extract\":\"")[1];
					result = result.replace("\\n"," ");
					result = result.replace("\\u00e1","á");
					result = result.replace("\\u00e9","é");
					result = result.replace("\\u00ed","í");
					result = result.replace("\\u00f3","ó");
					result = result.replace("\\u00fa","ú");
					result = result.replace("\\u00f1","ñ");
					result = result.replace("\\u00fc","u");
					result = result.replace("\\u200b","");
					result = result.replace("\"}}}}","");
					result = result.replace("\\u00ab","\"");
					result = result.replace("\\u00bb","\"");
					result = result.replace("\\u00ba","º");
					result = result.replace("\\u2014","-");
					respuesta = nombreUsuario + " encontré información en Wikipedia\n" + wikipediaURL + result;

				}

			} catch (Exception ex) {
				ex.printStackTrace();

			}	
			return respuesta;
	}	
	
}
