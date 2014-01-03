package kickstarter;

import java.util.TreeSet;

public class Projecto {
	private int codigo;
	private Utilizador user;
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

	public Projecto(String n, String d, double m, Utilizador u) {
		this.codigo = Kickstarter.CODIGO;
		this.user = u;
		this.nome = n;
		this.descricao = d;
		this.montanteRequerido = m;
		this.montanteAdquirido = 0;
		this.terminado = false;
		Kickstarter.CODIGO++;
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
	
	public Utilizador getUtilizador() {
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
		s.append("Nome: " + this.getNome());	
		s.append("Descricao: " + this.getDescricao());	
		s.append("Montante Requerido: " + this.getMontanteRequerido());
		s.append("Montante já Adquirido: " + this.getMontanteAdquirido());        
		s.append("Financiamento Assegurado: " + this.isTerminado());
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

	public synchronized void ajudarFinanciamento(String nick, double montante) {
		this.montanteRequerido += montante;
		
		for(Oferta o : this.ofertas) {
			if(o.getNick().equals(nick)) {
				double ant = o.getDoado();
				this.ofertas.remove(o);
				this.ofertas.add(new Oferta(nick, ant+montante));
			}
			else
				this.ofertas.add(new Oferta(nick,montante));				
		}	
		notifyAll();
	}
}


