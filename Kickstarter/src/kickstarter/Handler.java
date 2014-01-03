package kickstarter;

import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashSet;
import java.util.TreeSet;

public class Handler extends Thread {
	private Socket s;
	private Kickstarter sk;
	private ObjectInputStream sInput;
	private PrintWriter sOutput;

	public Handler(Socket s, Kickstarter k) {
        this.s= s;
        this.sk = k;
        this.sInput = null;
        this.sOutput = null;
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
                                System.err.println("Pacote Lista Nao Financiados");
                                String desc = pacote.getArgumentos().get(ServidorKickstarter.DESC_PROJETO);
                                System.out.println(desc);
                                HashSet<Projecto> projetos = sk.devolveProjetosAtivos(desc);
                                
                                sOutput.println(projetos); //FALTA "LEVAR" O OBJECTO
                                sOutput.flush();
                            }
                        	else {
                        		if(pacote.getAccao().equals(ServidorKickstarter.FINANCIAR)) {
                        			System.err.println("Pacote Financiar Projeto");
                        			String user = pacote.getArgumentos().get(ServidorKickstarter.NOME_USER);
                        			String sid = pacote.getArgumentos().get(ServidorKickstarter.ID);
                        			String smon = pacote.getArgumentos().get(ServidorKickstarter.MONTANTE);
                        			
                        			int id = Integer.parseInt(sid);
                        			double montante = Double.parseDouble(smon);
                        			sk.ajudarProjeto(user, id, montante);
                        		} else {
                        			if(pacote.getAccao().equals(ServidorKickstarter.PROJ_FINANCIADOS)) {
                        				System.err.println("Pacote Listar Projetos Financiados");
                        				String desc = pacote.getArgumentos().get(ServidorKickstarter.DESC_PROJETO);
                        				System.out.println(desc);
                        				
                        				HashSet<Projecto> res = sk.devolveProjetoTerminado(desc);	
                        			}
                        			else {
                        				System.err.println("Pacote Informações Projeto");
                        				
                            			String sid = pacote.getArgumentos().get(ServidorKickstarter.ID);
                            			String sn = pacote.getArgumentos().get(ServidorKickstarter.NCONTRIBUTOS);
                            			
                            			int id = Integer.parseInt(sid);
                            			int n = Integer.parseInt(sn);
                        				TreeSet<Oferta> res = sk.devolveProjectoContribuidores(id, n);
                        			}
                        		}
                        	}
                        }
                    }
				}
			} while (true);
		} catch (Exception e) {
		}
	}
}
