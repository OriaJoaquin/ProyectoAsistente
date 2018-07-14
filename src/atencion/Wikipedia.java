package atencion;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import wikipedia.InfoActual;
import wikipedia.InfoGetter;
import wikipedia.InfoMock;


public class Wikipedia implements Atencion {
	private Atencion siguiente;
	
	public void establecerSiguiente(Atencion siguiente) {
		this.siguiente = siguiente;		
	}

	public String atender(String mensaje, String nombreAsistente, String nombreUsuario) {
		String consulta = mensaje.toLowerCase();
		final String regex = "(@" + nombreAsistente + ").*(informaci[o|ó]n|info|qu[e|é] es|quiero saber|necesito saber sobre)(.*)";
		final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
		final Matcher matcher = pattern.matcher(consulta);
		
		if (matcher.find()){
			String infoABuscar = matcher.group(3); 
			InfoGetter info = new InfoMock();
			return info.getInfo(infoABuscar,nombreUsuario);
		}

		return siguiente.atender(mensaje, nombreAsistente, nombreUsuario);
	}
}
