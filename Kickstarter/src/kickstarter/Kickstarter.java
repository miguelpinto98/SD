package kickstarter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeSet;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Kickstarter {
	private HashMap<String, Utilizador> utilizadores;
	private HashMap<Integer, Projecto> projectos;
	
	private Lock lock = new ReentrantLock();
	private Condition cond = lock.newCondition();

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
		
		lock.lock();
		try {
			if (!this.utilizadores.containsKey(nick)) {
				Utilizador u = new Utilizador(nick, pass);
				this.utilizadores.put(nick, u);
				res = true;
			}
		} finally {
			lock.unlock();
		}
		return res;
	}

	public boolean validaUser(String nick, String pass) {
		boolean res = false;
		lock.lock();
		
		try {
			if (this.utilizadores.containsKey(nick)) {
				if (this.utilizadores.get(nick).getPassword().equals(pass)) {
					this.utilizadores.get(nick).setAtivo(true);
					res = true;
				}
			}
		} finally {
			lock.unlock();
		}
		return res;
	}

	public int novoProjeto(Handler h,String nProj, String desc, double montante, String u) throws InterruptedException {
		int res = -1;
		
		lock.lock();
		Projecto p = new Projecto(nProj, desc, montante, u);
		try {
			if (!this.projectos.containsKey(p.getCodigo())) {
				this.projectos.put(p.getCodigo(), p);
				res = p.getCodigo();
				
				h.getSOutPut().println("#   Recebeu o código: "+res+", aguarda financiamento");
				h.getSOutPut().flush();
				
				System.out.println("entrou no wait");
				while(!p.isTerminado()) {
					this.cond.await();
	
					for(Oferta o : p.getOfertas()) {
						if(o.getLida()==false) {
	                        double valor = (p.getMontanteRequerido()-p.getMontanteAdquirido());
	                        if(valor<0)
	                            valor = 0;
							h.getSOutPut().println("#   Recebeu uma oferta. Faltam "+valor+"€.");
							h.getSOutPut().flush();
							o.setLida(true);
							System.out.println("NP-"+o.getLida());
						}
					}
				}
			}
		} finally {
			System.out.println("saiu do wait");
			lock.unlock();
		}
		return res;
	}		

	public boolean ajudarProjeto(String nick, int idproj, double montante) throws InterruptedException {
			boolean res = false;
		lock.lock();
		try {	
			res = this.projectos.get(idproj).ajudarFinanciamento(nick, montante);
			this.cond.signalAll();
		
			if(this.projectos.get(idproj).getMontanteAdquirido() >= this.projectos.get(idproj).getMontanteRequerido()) {
				this.projectos.get(idproj).setFinanciamento(true);
			}
				System.out.println(this.projectos.get(idproj).getMontanteAdquirido());
				
		} finally {
			lock.unlock();
		}
		
			return res;
	}

	public HashSet<Projecto> devolveProjetosAtivos(String desc) {
		HashSet<Projecto> res = new HashSet<>();
		lock.lock();
		
		try {
			for (Projecto p : this.projectos.values())
				if (!p.isTerminado() && p.getDescricao().contains(desc))
					res.add(p);
		} finally {
			lock.unlock();
		}
		return res;
	}

	public HashSet<Projecto> devolveProjetoTerminado(String desc) {
		HashSet<Projecto> res = new HashSet<>();
		
		lock.lock();
		try {
			for (Projecto p : this.projectos.values())
				if (p.isTerminado() && p.getDescricao().contains(desc))
					res.add(p);
		} finally {
			lock.unlock();
		}
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


	public void logout(String nick) {
		
		lock.lock();
		try {
			this.utilizadores.get(nick).setAtivo(false);
		} finally {
			lock.unlock();
		}
	}


	
}
