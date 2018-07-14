package wikipedia;


public class InfoMock extends InfoGetter {
	
	public String getInfo(String infoABuscar,String nombreUsuario){
	
	respuesta = nombreUsuario + " encontré información en Wikipedia\n"+
			"https://es.wikipedia.org/wiki/Felis_silvestris_catus\n"+
			"El gato o gato doméstico[1][2] (Felis silvestris catus) y coloquialmente llamado minino,[3] michino,[4] micho,[5] mizo,[6] miz,[7] morrongo[8] o morroño;[9] es una subespecie de mamífero carnívoro de la familia Felidae. El gato está en convivencia cercana al ser humano desde hace unos 9500 años,[10] periodo superior al estimado anteriormente, que oscilaba entre 3500 y 8000 años. En las lenguas romances los nombres actuales más generalizados derivan del latín vulgar catus, palabra que aludía especialmente a los gatos salvajes en contraposición a los gatos domésticos que, en latín, eran llamados felis. Hay docenas de razas, algunas sin pelo o incluso sin cola, como resultado de mutaciones genéticas y años de selección artificial, y existen en una amplia variedad de colores. Son depredadores por naturaleza, siendo sus presas potenciales más de cien especies diferentes de animales. Son capaces de asimilar algunos conceptos, y ciertos ejemplares han sido entrenados para manipular mecanismos simples. Se comunican principalmente a través del maullido; también con gemidos, gruñidos y con diferentes vocalizaciones,[11] además del lenguaje corporal. Se creía que el gato salvaje africano (Felis silvestris lybica) era su ancestro más inmediato,[12]pero evidencias genéticas recientes señalan que los gatos domésticos actuales comparten una procedencia directa con los gatos salvajes de Oriente Medio. Sin embargo, al tratarse de una subespecie puede intercambiar -y de hecho lo hace- material genético con otras subespecies de Felis silvestris. Se ha detectado hibridación con el gato montés europeo.[13] Esta hibridación masiva se considera la principal amenaza para la conservación de las variantes salvajes. Está incluido en la lista 100 de las especies exóticas invasoras más dañinas del mundo[14] de la Unión Internacional para la Conservación de la Naturaleza. También, de forma excepcional, se han obtenido híbridos fértiles con gatos salvajes fuera de la especie F. silvestris; en la década de 1960, la criadora Jean Mill comenzó un programa de cría cruzando gatos domésticos con un ejemplar hembra de Prionailurus bengalensis y obtuvo tras diversos cruces la actual raza de gato bengalí.[15] El gato, junto con el perro, es la mascota o animal de compañía más popular del mundo.";
	return respuesta;
	}


}
