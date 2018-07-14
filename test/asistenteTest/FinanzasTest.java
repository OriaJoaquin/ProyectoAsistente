package asistenteTest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import asistente.Asistente;

public class FinanzasTest {

	public final static String USUARIO = "delucas";

	Asistente jenkins;

	@Before
	public void setup() {
		jenkins = new Asistente("jenkins");
	}

	@Test
	public void finanzaInexistente() {
		Assert.assertEquals("Disculpa... no entiendo el pedido, @delucas ¿podrías repetirlo?", jenkins.escuchar("@jenkins cocina"));
	}

	@Test
	public void queElMockDeExchangeFunciona() {
		Assert.assertEquals("Cotizacion del USD: 30.0 ARS\n", jenkins.escuchar("@jenkins cotizacion del dolar"));
	}
	
	@Test
	public void queElMockDeStockFunciona() {
		Assert.assertEquals("Acciones de msft:\nValor de la accion: 100.0 USD\nVariacion: 10.5%\n", jenkins.escuchar("@jenkins cotizacion de las acciones de MSFT"));
	}
}
