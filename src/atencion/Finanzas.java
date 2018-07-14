package atencion;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import finanzas.*;

public class Finanzas implements Atencion{
	
	private Atencion siguiente;

	@Override
	public void establecerSiguiente(Atencion siguiente) {
		this.siguiente = siguiente;
	}

	@Override
	public String atender(String mensaje, String nombreAsistente, String nombreUsuario) {
		
		String consulta = mensaje.toLowerCase();
		final String regex = "(cotizacion|valor) (del|de la|de las acciones de) (.*)";
		final Pattern pattern = Pattern.compile(regex);
		final Matcher matcher = pattern.matcher(consulta);
		
		if(matcher.find()) {
			return new DatoFinanza().atender(mensaje, nombreAsistente, nombreUsuario);
		}
		else
			return siguiente.atender(mensaje, nombreAsistente, nombreUsuario);
		
	}

}
