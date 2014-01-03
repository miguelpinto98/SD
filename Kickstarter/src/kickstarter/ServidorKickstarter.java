package kickstarter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeSet;

public class ServidorKickstarter {
    
    public static HashMap<String,Utilizador> utilizadores;
    public static HashMap<Integer,Projecto> projectos;
    
	public static int CODIGO=1;
    public static int PORTA=9999;
    public static String REGISTAR = "RegistarUser";
    public static String NOME_USER = "NomeUser";
    public static String PW_USER = "PwUser";
	
    public static void main(String args[]) throws IOException, ClassNotFoundException {
        ServerSocket sv = new ServerSocket(PORTA);
        
        while(true) {
            Socket cliente = sv.accept();
            System.out.println("Registar");
            
            Handler thread = new Handler(cliente);
            thread.start();
        }
    }
    
    
	public ServidorKickstarter() {
		this.utilizadores = new HashMap<>();
		this.projectos = new HashMap<>();
	}
	
	public boolean novoProjeto(Utilizador u, String nProj, String desc, double montante) throws InterruptedException {
		boolean res = false;
		Projecto p = new Projecto(nProj, desc, montante, u);
		
		if(!this.projectos.containsKey(p.getCodigo())) {
			this.projectos.put(p.getCodigo(), p);
			res = true;
			wait(); //Coloca utilizador/cliente em wait até obter financiamento
		}		
		return res;
	}
	
	public void ajudarProjeto(String nick, int idproj, double montante) {
		synchronized (this.projectos.get(idproj)) {
			this.projectos.get(idproj).ajudarFinanciamento(nick, montante);
		
			
		}
	}
    
    
    public HashSet<Projecto> devolveProjetosAtivos(String desc) {
    	HashSet<Projecto> res = new HashSet<>();
    	
    	for(Projecto p : this.projectos.values())
    		if(!p.isTerminado() && p.getDescricao().contains(desc))
    			res.add(p);
    	
    	return res;
    }
    
    public HashSet<Projecto> devolveProjetoTerminado(String desc) {
    	HashSet<Projecto> res = new HashSet<>();
    	
    	for(Projecto p : this.projectos.values())
    		if(p.isTerminado() && p.getDescricao().contains(desc))
    			res.add(p);
    	
    	return res;
    }
    
    public TreeSet<Oferta> devolveProjectoContribuidores(int id, int n) {
    	TreeSet<Oferta> res = new TreeSet<>();
    	TreeSet<Oferta> ts = this.projectos.get(id).getOfertas();
    	int i=0;
    	
    	Iterator<Oferta> it = ts.iterator();
    	
    	while(it.hasNext() && i<n) {
    		res.add(it.next());
    		i++;
    	}
    	return res;
    }
}