package kickstarter;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashSet;
import java.util.TreeSet;

public class Handler extends Thread {
	private Socket s;
	private Kickstarter sk;
	private ObjectInputStream sInput;
	private PrintWriter sOutput;

	public Handler(Socket s, Kickstarter k) throws InterruptedException {
        this.s= s;
        this.sk = k;
        this.sInput = null;
        this.sOutput = null;
    }
	
	public PrintWriter getSOutPut() {
		return this.sOutput;
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

						boolean emSessao = false;
						if(sk.getUtilizadores().containsKey(nick))
							emSessao =  sk.getUtilizadores().get(nick).isAtivo();
						
                        System.out.println("USER: " + nick + pw);
						boolean existe = sk.validaUser(nick, pw);
						
						if(existe && !emSessao)
							sOutput.println("Entrou");
						else sOutput.println("NaoEntrou");
						sOutput.flush();
					} else {
                        if (pacote.getAccao().equals(ServidorKickstarter.CRIAR_PROJETO)){
                            System.err.println("Pacote CriarProjeto");
                            String nome = pacote.getArgumentos().get(ServidorKickstarter.NOME_PROJETO);
                            String desc = pacote.getArgumentos().get(ServidorKickstarter.DESC_PROJETO);
                            String montante = pacote.getArgumentos().get(ServidorKickstarter.MONTANTE_PROJETO);
                            String nick = pacote.getArgumentos().get(ServidorKickstarter.NOME_USER); 
                            
                            int id = sk.novoProjeto(this,nome, desc, Double.parseDouble(montante), nick);
						
                            if(id != -1)
                                sOutput.println("Projeto acabou de garantir financiamento :)");
                            else 
                                sOutput.println("Projeto não criado");
						
                            sOutput.flush();
                        } else {
                        	if(pacote.getAccao().equals(ServidorKickstarter.LISTANAOFINANCIADOS)) {
                                System.err.println("Pacote Lista Nao Financiados");
                                String desc = pacote.getArgumentos().get(ServidorKickstarter.DESC_PROJETO);
                                System.out.println("Pesquisa: "+desc);
                                
                                HashSet<Projecto> res = sk.devolveProjetosAtivos(desc);
                                
                                ObjectOutputStream out = null;
                				out = new ObjectOutputStream(s.getOutputStream());
                				out.writeObject(res);
                				out.flush();
                            }
                        	else {
                        		if(pacote.getAccao().equals(ServidorKickstarter.FINANCIAR)) {
                        			System.err.println("Pacote Financiar Projeto");
                        			String user = pacote.getArgumentos().get(ServidorKickstarter.NOME_USER);
                        			String sid = pacote.getArgumentos().get(ServidorKickstarter.ID);
                        			String smon = pacote.getArgumentos().get(ServidorKickstarter.MONTANTE);
                        			
                        			int id = Integer.parseInt(sid);
                        			double montante = Double.parseDouble(smon);
                        			
                        			System.out.println(id+" "+montante+"€");
                        			
                        			if(sk.getProjectos().containsKey(id)) {
                        				boolean res = sk.ajudarProjeto(user, id, montante);
	                        			if(res)
	                        				sOutput.println("Financiado com sucesso");
	                        			else
	                        				sOutput.println("Projeto já tem financiamento assegurado");
	                        		} else {
	                        			sOutput.println("#   O código do projeto inserido não existe");
	                        		}
                        			sOutput.flush();
                        		} else {
                        			if(pacote.getAccao().equals(ServidorKickstarter.PROJ_FINANCIADOS)) {
                        				System.err.println("Pacote Listar Projetos Financiados");
                        				String desc = pacote.getArgumentos().get(ServidorKickstarter.DESC_PROJETO);
                                        System.out.println("Pesquisa: "+desc);
                        				
                        				HashSet<Projecto> res = sk.devolveProjetoTerminado(desc);
                        				
                        				ObjectOutputStream out = null;
                        				out = new ObjectOutputStream(s.getOutputStream());
                        				out.writeObject(res);
                        				out.flush();
                        			}
                        			else {
                        				if(pacote.getAccao().equals(ServidorKickstarter.INF_PROJ)) {
                        					System.err.println("Pacote Informações Projeto");
                        					String sid = pacote.getArgumentos().get(ServidorKickstarter.ID);
                            			
                        					int id = Integer.parseInt(sid);
                        					System.out.println(id);
                            				
                        					Projecto res = sk.getProjectos().get(id);
                        				
                        					ObjectOutputStream out = null;
                        					out = new ObjectOutputStream(s.getOutputStream());
                        					out.writeObject(res);
                        					out.flush();
                        				} else {
                        					System.err.println("Pacote Sair");
                        					String nuser = pacote.getArgumentos().get(ServidorKickstarter.NOME_USER);
                        					sk.logout(nuser);
                        				}
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
