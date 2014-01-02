package CHAT;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Servidor {
	private ArrayList<PrintStream> clientes;
	
	public Servidor() {
		this.clientes = new ArrayList<>();
	}
	
	public void executaServidor(Servidor s) throws IOException {
		ServerSocket servidor = new ServerSocket(9898);
		System.out.println("Porta 9898 aberta!");
		
		while(true) {
			Socket cliente = servidor.accept();
			System.out.println("Nova conexão com o cliente " + cliente.getInetAddress().getHostAddress());
			
			PrintStream ps = new PrintStream(cliente.getOutputStream());
			this.clientes.add(ps);
			
			TrataCliente tc = new TrataCliente(cliente.getInputStream(),this);
			new Thread(tc).start();
		}
	}
	
	public void distribuiMensagem(String msg) {
		for(PrintStream cliente : this.clientes)
			cliente.println(msg);
	}
	
	public static void main(String[] args) throws IOException {
		Servidor ss = new Servidor();
		
		ss.executaServidor(ss);
	}
}
