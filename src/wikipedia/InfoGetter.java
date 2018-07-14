package wikipedia;

public abstract class InfoGetter {
	protected String respuesta;
	
	public abstract String getInfo(String infoABuscar,String nombreUsuario);
}
