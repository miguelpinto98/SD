package kickstarter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.Scanner;
import java.util.TreeSet;

public class ClienteKickstarter {
    private static String ip = "localhost";
    private static int port = 9999;
    private static Socket s = null;
    
    public static Scanner in = new Scanner(System.in);
    public static HashMap<String, String> hash = new HashMap<>();
    public static Pacote p = null;
    public static ObjectOutputStream o = null;
    
    public static String nick = null; 
    
    public static ObjectInputStream i = null;
    
    public static void main(String args[]) throws IOException, ClassNotFoundException {
        s = new Socket(ip,port);
        int opt;
        
        do {
        	opt = menuInicial();
            hash = null;
            o = null;
        	
        	if(opt==1) {
        		in.nextLine();
        		System.out.println("Nick");
        		String user = in.next();
        		System.out.println("Pass");
                String pw = in.next();
                
                hash = new HashMap<>();
                hash.put(ServidorKickstarter.NOME_USER, user);
                hash.put(ServidorKickstarter.PW_USER, pw);
                p = new Pacote(ServidorKickstarter.REGISTAR,hash);
                
                o = new ObjectOutputStream(s.getOutputStream());
                o.writeObject(p);
                o.flush();
                
        		BufferedReader sktInput = new BufferedReader(new InputStreamReader(s.getInputStream()));
        		System.out.println(sktInput.readLine());
                
        	} else {
        		if(opt == 2) {
        			in.nextLine();
            		System.out.println("Nick");
            		String user = in.next();
            		System.out.println("Pass");
                    String pw = in.next();
                    
                    hash = new HashMap<>();
                    hash.put(ServidorKickstarter.NOME_USER, user);
                    hash.put(ServidorKickstarter.PW_USER, pw);
                    p = new Pacote(ServidorKickstarter.ENTRAR,hash);
                    
                    o = new ObjectOutputStream(s.getOutputStream());
                    o.writeObject(p);
                    o.flush();
                    
            		BufferedReader sktInput = new BufferedReader(new InputStreamReader(s.getInputStream()));
                    String result = null;
                    result=sktInput.readLine();
                    
                    if(result.equals("Entrou")) {
                    	nick = user;
                    	menuPrincipal();
                    }
                    else {
                    	System.err.println("Autenticação falhada");
                    }
                }
        		else {
        			if(opt == 3) {
        				System.exit(3);
        				//falta fazer logout();
        				//nick = null;
        			}
        			else
        				System.out.println("Opção inválida!");
        		}
        	}
        		
        } while(true);

    }
    
    public static void menuPrincipal() throws IOException, ClassNotFoundException {
    	System.out.println(" 1 - Criar projeto");
    	System.out.println(" 2 - Financiar projeto");
    	System.out.println(" 3 - Lista projetos ainda não financiados");
    	System.out.println(" 4 - Lista projetos com financiamento garantido");
    	System.out.println(" 5 - Obter informações projeto");
    	System.out.println(" 6 - Até já");
    	
    	int opt = in.nextInt();
    	
    	do {
    		if(opt == 1 )
    			MenuCriarProjeto();
    		if(opt == 2)
    			MenuFinanciarProjeto();
    		if(opt == 3)
    			MenuListaProjetosNaoFinanciados();
    		if(opt == 4)
    			MenuListaProjetosGarantidos();
    		if(opt == 5)
    			MenuInformacoesProjeto();
    		if(opt == 6)
    			System.exit(0);
    		else
    			System.out.println("Opcão inválida");	
    	} while(opt<6);
    }
    
    private static void MenuInformacoesProjeto() throws IOException, ClassNotFoundException {
    	in.nextLine();
    	System.out.println("Código projeto");
    	String id = in.next();
    	
    	System.out.println("Quantos contribuidores visiveis?");
    	String n = in.next();
    	
    	hash = new HashMap<>();
    	hash.put(ServidorKickstarter.ID, id);
    	hash.put(ServidorKickstarter.NCONTRIBUTOS, n);
    	p = new Pacote(ServidorKickstarter.INF_PROJ, hash);
    	
    	criarObjeto(p);
    	
    	i = new ObjectInputStream(s.getInputStream());
		Projecto proj = (Projecto) i.readObject();
    	
    	System.out.println(proj.toString());
    	
    	int nn = Integer.parseInt(n);
    	if(nn == 0 || nn > proj.getOfertas().size()) {
    		for(Oferta o : proj.getOfertas())
    			System.out.println(o.getNick() + " - " + o.getDoado());
    	} else {
			Iterator<Oferta> it = proj.getOfertas().iterator();
			int i=0;
			while(it.hasNext() && i<nn) {
				Oferta o = it.next();
				System.out.println("Utilizador: "+o.getNick() + " Montante: "+o.getDoado());	
				i++;
			}
				
    	} 
    	
    	menuPrincipal();
	}

	private static void MenuListaProjetosGarantidos() throws IOException, ClassNotFoundException {
		in.nextLine();
        System.out.println("Palavra chave: ");
        String desc = in.next();
        
        hash = new HashMap<>();
        hash.put(ServidorKickstarter.DESC_PROJETO, desc);
        p = new Pacote(ServidorKickstarter.PROJ_FINANCIADOS,hash);
                
        criarObjeto(p);
                
        i = new ObjectInputStream(s.getInputStream());
    	@SuppressWarnings("unchecked")
		HashSet<Projecto> projs = (HashSet<Projecto>) i.readObject();
    	
    	for(Projecto pr : projs)
    		System.out.println("ID: "+pr.getCodigo()+" Nome Projeto: "+pr.getNome()+" Descrição: "+pr.getDescricao());
	
    	menuPrincipal();
	}

	private static void MenuListaProjetosNaoFinanciados() throws IOException, ClassNotFoundException {
		in.nextLine();
        System.out.println("Palavra chave: ");
        String desc = in.next();
        
        hash = new HashMap<>();
        hash.put(ServidorKickstarter.DESC_PROJETO, desc);
        p = new Pacote(ServidorKickstarter.LISTANAOFINANCIADOS,hash);
                
        criarObjeto(p);
                
        i = new ObjectInputStream(s.getInputStream());
    	@SuppressWarnings("unchecked")
		HashSet<Projecto> projs = (HashSet<Projecto>) i.readObject();
    	
    	for(Projecto pr : projs)
    		System.out.println("ID: "+pr.getCodigo()+" Nome Projeto: "+pr.getNome()+" Descrição: "+pr.getDescricao());
    	
    	menuPrincipal();
	}

	private static void MenuFinanciarProjeto() throws IOException, ClassNotFoundException {
		in.nextLine();
		System.out.println("Insira o id do projeto a financiar: ");
		int id = in.nextInt();
		System.out.println("Insira montante que pretende financiar: ");
		double montante = in.nextDouble();
		
		hash = new HashMap<>();
		hash.put(ServidorKickstarter.NOME_USER, nick);
		hash.put(ServidorKickstarter.ID, Integer.toString(id));
		hash.put(ServidorKickstarter.MONTANTE, Double.toString(montante));
		p = new Pacote(ServidorKickstarter.FINANCIAR, hash);
		
		criarObjeto(p);
		
		BufferedReader sktInput = new BufferedReader(new InputStreamReader(s.getInputStream()));
        System.out.println(sktInput.readLine());
        
		
		menuPrincipal();
	}

	private static void MenuCriarProjeto() throws IOException, ClassNotFoundException{
        in.nextLine();
        System.out.println("Nome projeto");
        String nomeProjeto = in.nextLine();
        System.out.println("Descrição projeto");
        String descProjeto = in.nextLine();
        System.out.println("Montante necessário para o projeto");
        String montanteProjeto = in.next();
                    
        hash = new HashMap<>();
        hash.put(ServidorKickstarter.NOME_USER, nick);
        hash.put(ServidorKickstarter.NOME_PROJETO, nomeProjeto);
        hash.put(ServidorKickstarter.DESC_PROJETO, descProjeto);
        hash.put(ServidorKickstarter.MONTANTE_PROJETO,montanteProjeto);
                    
        p = new Pacote(ServidorKickstarter.CRIAR_PROJETO,hash);
                    
        criarObjeto(p);
        
        BufferedReader sktInput = new BufferedReader(new InputStreamReader(s.getInputStream()));
        String ss; 
        do {
        	ss = sktInput.readLine();
        	System.out.println(ss);
        } while(ss.contains("Recebeu"));
        
        menuPrincipal();
	}

	public static int menuInicial() {
    	System.out.println("1 - Registar");
        System.out.println("2 - Entrar");
        System.out.println("3 - Sair");
        
        return in.nextInt();    
    }
	
	public static void criarObjeto(Pacote p) throws IOException {
		o = null;
		o = new ObjectOutputStream(s.getOutputStream());
        o.writeObject(p);
        o.flush();
	}
}
