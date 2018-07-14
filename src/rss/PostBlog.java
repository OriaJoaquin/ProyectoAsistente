package rss;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import atencion.Atencion;

public class PostBlog {				//atencion

	//private Atencion siguiente;
	
	/*public void establecerSiguiente(Atencion siguiente) {
		this.siguiente = siguiente;
	}*/
	
	public String atender(String mensaje, String nombreAsistente, String nombreUsuario) {
		String consulta = mensaje.toLowerCase();
		
		if(consulta.contains("agregar blog") == true || consulta.contains("agregar rss") == true) {
			final String regex = "(?:blog|rss) (.*), (.*)";
			final Pattern pattern = Pattern.compile(regex);
			final Matcher matcher = pattern.matcher(consulta);
			
			if(matcher.find()) {
				PrintWriter pw = null;
				try {
					pw = new PrintWriter(new FileOutputStream(new File("blogs\\info.txt"),true));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				String data = matcher.group(1) + ", " + matcher.group(2);
				pw.append(data + "\n");
				pw.close();
			}
		} else if(consulta.contains("revisar blogs") == true || consulta.contains("revisar rss") == true) {
			PostGetter pg = new PostMock();		//CAMBIAR POR PostMock() PARA LOS TESTS
			Post p = pg.getPost();
			
			return p.toString();
		}
		return "no";
		//return siguiente.atender(mensaje, nombreAsistente, nombreUsuario);
	}
}
