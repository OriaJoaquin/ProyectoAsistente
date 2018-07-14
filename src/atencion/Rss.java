package atencion;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rss.*;

public class Rss implements Atencion{
	
	private Atencion siguiente;

	@Override
	public void establecerSiguiente(Atencion siguiente) {
		this.siguiente = siguiente;
	}

	@Override
	public String atender(String mensaje, String nombreAsistente, String nombreUsuario) {

		String consulta = mensaje.toLowerCase();
		final String regex = "(@" + nombreAsistente + ") revisar rss";
		final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
		final Matcher matcher = pattern.matcher(consulta);
		
		if(matcher.find()) {
			return new PostBlog().atender(mensaje, nombreAsistente, nombreUsuario);
		}
		
		return siguiente.atender(mensaje, nombreAsistente, nombreUsuario);
	}

}
