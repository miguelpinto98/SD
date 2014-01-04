package kickstarter;

import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeSet;

public class Kickstarter {
	private HashMap<String, Utilizador> utilizadores;
	private HashMap<Integer, Projecto> projectos;

	public Kickstarter() {
		this.utilizadores = new HashMap<>();
		this.projectos = new HashMap<>();
	}
	

	public HashMap<String, Utilizador> getUtilizadores() {
		return this.utilizadores;
	}


	public void setUtilizadores(HashMap<String, Utilizador> utilizadores) {
		this.utilizadores = utilizadores;
	}
	
	public HashMap<Integer, Projecto> getProjectos() {
		return this.projectos;
	}


	public void setProjectos(HashMap<Integer, Projecto> projectos) {
		this.projectos = projectos;
	}


	public boolean registaUtilizador(String nick, String pass) {
		boolean res = false;
		if (!this.utilizadores.containsKey(nick)) {
			Utilizador u = new Utilizador(nick, pass);
			this.utilizadores.put(nick, u);
			res = true;
		}
		return res;
	}

	public boolean validaUser(String nick, String pass) {
		boolean res = false;

		if (this.utilizadores.containsKey(nick)) {
			if (this.utilizadores.get(nick).getPassword().equals(pass)) {
				this.utilizadores.get(nick).setAtivo(true);
				res = true;
			}
		}
		return res;
	}

	public synchronized int novoProjeto(Handler h,String nProj, String desc, double montante, String u) throws InterruptedException {
		int res = -1;
		Projecto p = new Projecto(nProj, desc, montante, u);

		if (!this.projectos.containsKey(p.getCodigo())) {
			this.projectos.put(p.getCodigo(), p);
			res = p.getCodigo();
			
			System.out.println("entrou no wait");
			while(!p.isTerminado()) {
				this.wait();
				for(Oferta o : p.getOfertas()) {
					if(o.getLida()==false) {
						h.getSOutPut().println("Recebeu uma oferta " + o.getDoado());
						h.getSOutPut().flush();
						o.setLida(true);
					}
				}
			}
			System.out.println("saiu wait");
		}
		return res;
	}		

	public synchronized boolean ajudarProjeto(String nick, int idproj, double montante) {
			boolean res = this.projectos.get(idproj).ajudarFinanciamento(nick, montante);
			
			if(this.projectos.get(idproj).getMontanteAdquirido() >= this.projectos.get(idproj).getMontanteRequerido()) {
				this.projectos.get(idproj).setFinanciamento(true);
			}
				System.out.println(this.projectos.get(idproj).getMontanteAdquirido());
				
				this.notifyAll();
			return res;
	}

	public HashSet<Projecto> devolveProjetosAtivos(String desc) {
		HashSet<Projecto> res = new HashSet<>();

		for (Projecto p : this.projectos.values())
			if (!p.isTerminado() && p.getDescricao().contains(desc))
				res.add(p);

		return res;
	}

	public HashSet<Projecto> devolveProjetoTerminado(String desc) {
		HashSet<Projecto> res = new HashSet<>();

		for (Projecto p : this.projectos.values())
			if (p.isTerminado() && p.getDescricao().contains(desc))
				res.add(p);

		return res;
	}

	public TreeSet<Oferta> devolveProjectoContribuidores(int id, int n) {
		TreeSet<Oferta> res = new TreeSet<>();
		TreeSet<Oferta> ts = this.projectos.get(id).getOfertas();
		int i = 0;
		System.out.println(ts.isEmpty());

		if(!ts.isEmpty()) {
			Iterator<Oferta> it = ts.iterator();

			while (it.hasNext() && i < n) {
				res.add(it.next());
				i++;
			}
		}
		return res;
	}


	
}
