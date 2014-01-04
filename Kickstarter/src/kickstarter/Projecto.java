package kickstarter;

import java.io.Serializable;
import java.util.Iterator;
import java.util.TreeSet;

public class Projecto implements Serializable{
	private int codigo;
	private String user;
	private String nome;
	private String descricao;
	private double montanteRequerido;
	private double montanteAdquirido;
	private boolean terminado;
	private TreeSet<Oferta> ofertas;

	public Projecto() {
		this.codigo = 0;
		this.nome = "";
		this.descricao = "";
		this.montanteRequerido = 0;
		this.montanteAdquirido = -1;
		this.terminado = false;
	}

	public Projecto(String n, String d, double m, String u) {
		this.codigo = ServidorKickstarter.CODIGO;
		this.user = u;
		this.nome = n;
		this.descricao = d;
		this.montanteRequerido = m;
		this.montanteAdquirido = 0;
		this.terminado = false;
		this.ofertas = new TreeSet<>();
		ServidorKickstarter.CODIGO++;
	}

	public Projecto(Projecto p) {
		this.codigo = p.getCodigo();
		this.nome = p.getNome();
		this.descricao = p.getDescricao();
		this.montanteRequerido = p.getMontanteRequerido();
		this.montanteAdquirido = p.getMontanteAdquirido();
		this.terminado = p.isTerminado();
	} 

	public int getCodigo() {
		return this.codigo;
	}
	
	public String getUtilizador() {
		return this.user;
	}

	public String getNome() {
		return this.nome;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public double getMontanteRequerido() {
		return this.montanteRequerido;
	}

	public double getMontanteAdquirido() {
		return this.montanteAdquirido;
	}
	
	public boolean isTerminado() {
		return this.terminado;
	}
	
	public TreeSet<Oferta> getOfertas() {
		TreeSet<Oferta> res = new TreeSet<>();
		
		for(Oferta o : this.ofertas)
			res.add(o.clone());
		
		return res;
	}

	public void setMontanteAquirido(double m) {
		this.montanteAdquirido = m;
	}

	public void setFinanciamento(boolean f) {
		this.terminado = f;
	}

	public Projecto clone() {
		return new Projecto(this);
	}

	public String toString() {
		StringBuilder s = new StringBuilder("***Projecto***\n");
		s.append("Código: " + this.getCodigo());
		s.append("\nProjeto de :" +this.getUtilizador());
		s.append("\nNome: " + this.getNome());	
		s.append("\nDescricao: " + this.getDescricao());	
		s.append("\nMontante Requerido: " + this.getMontanteRequerido());
		s.append("\nMontante já Adquirido: " + this.getMontanteAdquirido());        
		s.append("\nFinanciamento Assegurado: " + this.isTerminado());
		
		return s.toString();
	}

	public boolean equals(Object o) {
		if (this == o)
			return true;
		if ((o == null) || (o.getClass() != this.getClass()))
			return false;
		else {
			Projecto p = (Projecto) o;
			return (this.getCodigo() == p.getCodigo());	
		}
	}

	public synchronized boolean ajudarFinanciamento(String nick, double montante) {
		boolean res = false, encontrou = false;
		Oferta of = null;
		
		if(!this.isTerminado()) {
			this.montanteAdquirido += montante;
			
			if(this.ofertas.isEmpty()) {
				this.ofertas.add(new Oferta(nick, montante));
			} else {
				Iterator<Oferta> it =this.ofertas.iterator();
				while(it.hasNext() && !encontrou) {
					if((of = it.next()).getNick().equals(nick))
						encontrou=true;
				}
				if(encontrou) {
					this.ofertas.remove(of);
					this.ofertas.add(new Oferta(nick, of.getDoado()+montante));
				} else {
					this.ofertas.add(new Oferta(nick, montante));
				}
			}
			res = true;
		}
		//notifyAll();
		return res;
	}
}


