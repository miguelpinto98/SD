package CHAT;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
	private String host;
	private int porta;
	
	public Cliente(String string, int i) {
		this.host = string;
		this.porta = i;
	}

	public static void main(String[] args) throws IOException {
		new Cliente("localhost",9898).executa();
	}

	private void executa() throws IOException {
		Socket cliente = new Socket(this.host, this.porta);
	    System.out.println("Cliente Ligado!");
	 
	     Recebedor r = new Recebedor(cliente.getInputStream());
	     new Thread(r).start();
	     
	     Scanner teclado = new Scanner(System.in);
	     PrintStream saida = new PrintStream(cliente.getOutputStream());
	     
	     while (teclado.hasNextLine()) {
	       saida.println(teclado.nextLine());
	     }
	     
	     saida.close();
	     teclado.close();
	     cliente.close();
	}
}
