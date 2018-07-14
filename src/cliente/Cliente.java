package cliente;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.*;

import server.Mensaje;

public class Cliente extends Thread {
	public static final int ESPERANDO_LOGIN = 0;
	public static final int LOGGEADO = 1;
	public static final int USUARIO_EN_USO = 2;

	boolean escuchando = true;
	private Socket cliente;
	private DataInputStream in;
	private DataOutputStream out;
	private String usuario;
	public HashMap<String, Sala> salas;
	public List<String> usuarios;
	public int estado;

	private HashMap<String, List<String>> mensajesPrivados;
	private HashMap<String, List<String>> mensajesSala;
	
	private HashMap<String, List<String>> integrantesSalas;

	public Cliente(String host, int puerto) throws UnknownHostException, IOException {
		this.cliente = new Socket(host, puerto);
		this.in = new DataInputStream(new BufferedInputStream(this.cliente.getInputStream()));
		this.out = new DataOutputStream(new BufferedOutputStream(this.cliente.getOutputStream()));
		usuarios = new LinkedList<String>();
		salas = new HashMap<String, Sala>();
		this.estado = ESPERANDO_LOGIN;
		this.mensajesPrivados = new HashMap<String, List<String>>();
		this.mensajesSala = new HashMap<String, List<String>>();
		this.integrantesSalas = new HashMap<String, List<String>>(); 
	}

	// Escucha mensajes del servidor
	@Override
	public void run() {

		while (escuchando) {
			try {
				Mensaje msg = new Mensaje(in.readUTF());
				System.out.println("msj escuchado");
				procesar(msg);
			} catch (IOException e) {
			}
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
			}

		}
	}

	private void procesar(Mensaje msg) {
		if (estado == ESPERANDO_LOGIN) {
			login(msg);
		} else {
			switch (msg.getTipo()) {
			case Mensaje.BROADCAST:
				recibirBroadcast(msg);
				break;
			case Mensaje.MENSAJE_PRIVADO:
				mensajePrivado(msg);
				break;
			case Mensaje.DESCONECTAR:
				desconectar(msg);
				break;
			case Mensaje.ACTUALIZAR:
				actualizarUsuarios(msg);
				break;
			case Mensaje.ACTUALIZAR_SALAS:
				actualizarSalas(msg);
				break;
			case Mensaje.MENSAJE_SALA:
				mensajeSala(msg);
				break;
			case Mensaje.NUEVO_INTEGRANTE_SALA:
				nuevoIntegrante(msg);
				break;
			default:
				break;
			}
		}
	}

	private void nuevoIntegrante(Mensaje msg) {
		String sala = msg.getContenido();
		String nuevoIntegrante = msg.getOrigen();
		
		List<String> integrantes = integrantesSalas.get(sala);
		integrantes.add(nuevoIntegrante);
	}

	private void actualizarSalas(Mensaje msg) {
		salas = new HashMap<String, Sala>();
		String[] salasMensaje = msg.getContenido().split(",");
		String[] sala;
		for (String salaMensaje : salasMensaje) {
			if (!salaMensaje.isEmpty()) {
				sala = salaMensaje.split("&");
				salas.put(sala[1], new Sala(sala[1]));
				if(!integrantesSalas.containsKey(sala[1]))
					integrantesSalas.put(sala[1], new ArrayList<String>());
			}
		}
		// Actualizar en la ventana las salas disponibles
	}

	private void actualizarUsuarios(Mensaje msg) {
		usuarios = new LinkedList<String>();
		String[] usuariosMensaje = msg.getContenido().split(",");
		for (String usuario : usuariosMensaje) {
			usuarios.add(usuario);
		}
		// Actualizar en la ventan los usuarios conectados

	}

	private void recibirBroadcast(Mensaje msg) {
		for (String sala : salas.keySet()) {
			if (msg.getSala() == sala) {
				salas.get(sala).recibirMensaje(msg);
				return;
			}
		}
	}

	private void mensajePrivado(Mensaje msg) {
		String usuarioEmisor = msg.getOrigen();
		List<String> mensajes;

		if (mensajesPrivados.containsKey(usuarioEmisor)) {
			mensajes = mensajesPrivados.get(usuarioEmisor);
			mensajes.add(msg.getContenido());
		} else {
			mensajesPrivados.put(usuarioEmisor, new ArrayList<String>());
			mensajes = mensajesPrivados.get(usuarioEmisor);
			mensajes.add(msg.getContenido());
		}
	}
	
	public HashMap<String, List<String>> getIntegrantesSalas(){
		return this.integrantesSalas;
	}

	private void mensajeSala(Mensaje msg) {
		String sala = msg.getSala();
		String usuarioEmisor = msg.getOrigen();

		List<String> mensajes;

		if (mensajesSala.containsKey(sala)) {
			mensajes = mensajesSala.get(sala);
			mensajes.add(msg.getContenido());
		} else {
			mensajesSala.put(sala, new ArrayList<String>());
			mensajes = mensajesSala.get(sala);
			mensajes.add(msg.getContenido());
		}
	}
	
	public HashMap<String, List<String>> getMensajesSala(){
		return this.mensajesSala;
	}

	public HashMap<String, List<String>> getMensajesPrivados() {
		return mensajesPrivados;
	}

	public void limpiarMensajesPrivados() {
		this.mensajesPrivados = new HashMap<String, List<String>>();
	}
	
	public void limpiarMensajesSala() {
		this.mensajesSala = new HashMap<String, List<String>>();
	}

	private void desconectar(Mensaje msg) {
		// desconectado por el servidor
		escuchando = false;
		try {
			cliente.close();
		} catch (IOException e) {
		}
	}

	private void login(Mensaje msg) {
		System.out.println("en login");
		if (msg.getTipo() == 7) {
			this.estado = USUARIO_EN_USO;
		} else {
			this.usuario = msg.getContenido();
			estado = LOGGEADO;

		}
	}

	private void crearSala(String topico) {
		Mensaje msg = new Mensaje();
		msg.setOrigen(usuario);
		msg.setContenido(topico);
		msg.setTipo(Mensaje.NUEVA_SALA);
		enviar(msg);

	}

	public void enviar(Mensaje msg) {
		Gson gson = new GsonBuilder().create();
		String mensaje = gson.toJson(msg);
		System.out.println(mensaje);
		try {
			out.writeUTF(mensaje);
			out.flush();
		} catch (IOException e) {
		}
	}

	public String getUsuario() {
		return this.usuario;
	}
}