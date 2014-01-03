/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kickstarter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 
 * @author serafim
 */
public class Handler extends Thread {
	private Socket s;
	private Kickstarter sk;
	private ObjectInputStream sInput;
	private PrintWriter sOutput;

	public Handler(Socket s, Kickstarter k) {
        this.s= s;
        this.sk = k;
        
        
    }

	public void run() {
		String result = new String();
		
		try {
			do {
				this.sInput = new ObjectInputStream(s.getInputStream());
				this.sOutput = new PrintWriter(s.getOutputStream());
				
				Pacote pacote = (Pacote) sInput.readObject();

				if (pacote.getAccao().equals(ServidorKickstarter.REGISTAR)) {
					System.err.println("Recebeu pacote Registar"+"[2J");
					String nick = pacote.getArgumentos().get(ServidorKickstarter.NOME_USER);
					String pw = pacote.getArgumentos().get(ServidorKickstarter.PW_USER);

					System.out.println("USER: " + nick + pw);
					boolean existe = sk.registaUtilizador(nick, pw);
					System.out.println("Registado? "+existe);
					
					if(existe)
						sOutput.println("Inserido com Sucesso");
					else
						sOutput.println("Utilizador já existe");
					
					sOutput.flush();
				} else {
					if (pacote.getAccao().equals(ServidorKickstarter.ENTRAR)) {
						System.err.println("Pacote Entrar");
						String nick = pacote.getArgumentos().get(ServidorKickstarter.NOME_USER);
						String pw = pacote.getArgumentos().get(ServidorKickstarter.PW_USER);

						System.out.println(nick + pw);
						boolean existe = sk.validaUser(nick, pw);
						System.out.println(existe);
						
						if(existe)
							sOutput.println("Entrou");
						sOutput.flush();
					}
				}
			} while (true);
		} catch (Exception e) {
		}
	}
}
