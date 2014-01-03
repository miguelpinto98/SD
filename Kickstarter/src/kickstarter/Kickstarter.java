package kickstarter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeSet;

public class Kickstarter {
    
    private HashMap<String,Utilizador> utilizadores;
    private HashMap<Integer,Projecto> projectos;
    
	public static int CODIGO=1;
	
	public Kickstarter() {
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
    
    public boolean registaUtilizador(String nick, String pass) {
    	boolean res = false;
    	
    	if(!this.utilizadores.containsKey(nick)) {
    		Utilizador u = new Utilizador(nick, pass);
    		this.utilizadores.put(nick, u);
    		res = true;
    	}
    	return res;
    }
    
    public boolean validaUser(String nick, String pass) {
    	boolean res=false;
    	
    	if(this.utilizadores.containsKey(nick)) {
    		if(this.utilizadores.get(nick).getPassword().equals(pass))
    			res= true; 		
    	}
    	return res;
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