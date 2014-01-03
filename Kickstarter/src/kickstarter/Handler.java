package kickstarter;

import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashSet;

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
		try {
			do {
				this.sInput = new ObjectInputStream(s.getInputStream());
				this.sOutput = new PrintWriter(s.getOutputStream());
				
				Pacote pacote = (Pacote) sInput.readObject();

				if (pacote.getAccao().equals(ServidorKickstarter.REGISTAR)) {
					System.err.println("Recebeu pacote Registar");
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
					} else {
                        if (pacote.getAccao().equals(ServidorKickstarter.CRIAR_PROJETO)){
                            System.err.println("Pacote CriarProjeto");
                            String nome = pacote.getArgumentos().get(ServidorKickstarter.NOME_PROJETO);
                            String desc = pacote.getArgumentos().get(ServidorKickstarter.DESC_PROJETO);
                            String montante = pacote.getArgumentos().get(ServidorKickstarter.MONTANTE_PROJETO);
                            String nick = pacote.getArgumentos().get(ServidorKickstarter.NOME_USER); 
                            
                            boolean existe = sk.novoProjeto(sk.getUtilizadores().get(nick),nome, desc, Double.parseDouble(montante));
                            System.out.println(existe);
						
                            if(existe)
                                sOutput.println("Projecto criado com sucesso");
                            else 
                                sOutput.println("Projeto nao criado");
						
                            sOutput.flush();
                        } else {
                        	if(pacote.getAccao().equals(ServidorKickstarter.LISTANAOFINANCIADOS)) {
                                System.err.println("Pacote Listar");
                                String desc = pacote.getArgumentos().get(ServidorKickstarter.DESC_PROJETO);
                                System.out.println(desc);
                                HashSet<Projecto> projetos = sk.devolveProjetosAtivos(desc);
                                
                                sOutput.println(projetos); //FALTA "LEVAR" O OBJECTO
                                sOutput.flush();
                            }
                        	else
                        		;
                        }
                    }
				}
			} while (true);
		} catch (Exception e) {
		}
	}
}
