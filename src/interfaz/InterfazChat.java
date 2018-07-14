package interfaz;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JTextPane;

import cliente.Cliente;
import server.Mensaje;

public class InterfazChat {

	private JFrame frame;
	private JTextField tfMensaje;
	private String usuarioDestinatario;
	private String usuarioEmisor;
	private Cliente cliente;
	private JTextPane tpChat;
	private List<String> listaDestinatarios;
	private Modo modo;
	private String sala;
	
	private enum Modo {
		PRIVADO, SALA
	}

	public InterfazChat(String usuarioDestinatario, String usuarioEmisor, Cliente cliente) {
		this.usuarioDestinatario = usuarioDestinatario;
		this.usuarioEmisor = usuarioEmisor;
		this.cliente = cliente;
		this.modo = Modo.PRIVADO;
		initialize();
	}
	
	public InterfazChat(List<String> listaDestinatarios, String usuarioEmisor,String sala, Cliente cliente) {
		this.listaDestinatarios=listaDestinatarios;
		this.usuarioEmisor =usuarioEmisor;
		this.cliente = cliente;
		this.modo = modo.SALA;
		this.sala = sala;
		
		if(this.listaDestinatarios.size()>0)
			this.listaDestinatarios.remove(usuarioEmisor);
		initialize();
	}

	private void initialize() {
		frame = new JFrame("Chat con " + usuarioDestinatario);
		frame.setBounds(100, 100, 400, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		tfMensaje = new JTextField();
		tfMensaje.setBounds(10, 515, 266, 35);
		frame.getContentPane().add(tfMensaje);
		tfMensaje.setColumns(10);

		JButton btnEnviar = new JButton("Enviar");
		btnEnviar.setBounds(286, 515, 89, 35);
		frame.getContentPane().add(btnEnviar);

		tpChat = new JTextPane();
		tpChat.setBounds(10, 11, 364, 493);
		frame.getContentPane().add(tpChat);

		btnEnviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!tfMensaje.getText().trim().equals("")) {
					String textoOriginal = tpChat.getText();
					String mensajeNuevo = usuarioEmisor + ": " + tfMensaje.getText() + "\n";
					tpChat.setText(textoOriginal + mensajeNuevo);
					tfMensaje.setText("");
					
					if(modo==Modo.PRIVADO)
						enviarMensaje(mensajeNuevo);
					else if(modo==Modo.SALA) 
						enviarMensajeSala(mensajeNuevo);
				}
			}

			private void enviarMensajeSala(String mensajeNuevo) {
				Mensaje m = new Mensaje();
				
				m.setContenido(mensajeNuevo);
				m.setOrigen(usuarioEmisor);
				m.setTipo(8);
				m.setSala(sala);
				
				for(String dest : listaDestinatarios) {
					m.setDestino(dest);
					cliente.enviar(m);	
				}
			}

			private void enviarMensaje(String mensajeNuevo) {
				Mensaje m = new Mensaje();
				
				m.setContenido(mensajeNuevo);
				m.setOrigen(usuarioEmisor);
				m.setDestino(usuarioDestinatario);
				m.setTipo(3);
								
				cliente.enviar(m);
			}
		});
		
		tpChat.setEditable(false);
		
		frame.getRootPane().setDefaultButton(btnEnviar);

		frame.setVisible(true);
	}
	
	public void agregarUsuario(String destinatario) {
		if(this.modo == Modo.SALA)
			this.listaDestinatarios.add(destinatario);
	}
	
	public void recibirMensaje(String mensajeNuevo) {
		String textoOriginal = tpChat.getText();
		tpChat.setText(textoOriginal + mensajeNuevo);
	}
}
