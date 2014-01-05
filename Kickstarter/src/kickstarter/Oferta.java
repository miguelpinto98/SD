package kickstarter;

import java.io.Serializable;

public class Oferta implements Comparable<Oferta>, Serializable{
	private String nick;
	private double doado;
	private boolean lida;
	
	public Oferta() {
		this.nick = "";
		this.doado = 0;
	}
	
	public Oferta(String nick, double mont) {
		this.nick = nick;
		this.doado = mont;
		this.lida = false;
	}
	
	public Oferta(Oferta o) {
		this.nick = o.getNick();
		this.doado = o.getDoado();
		this.lida = o.getLida();
	}
	
	public String getNick() {
		return this.nick;
	}
	
	public boolean getLida() {
		return this.lida;
	}
	
	public void setLida(boolean a) {
		this.lida = a; 
	}
	
	public double getDoado() {
		return this.doado;
	}
 
	public int compareTo(Oferta o) {
		if(o.getDoado() < this.doado) 
			if(o.getNick().compareTo(this.nick) != 0)
				return -1;
			else
				return 0;
		else {
			if(o.getNick().compareTo(this.nick) != 0) 
				return 1;
			else
				return 0;
		}
	}
	
	public Oferta clone() {
		return new Oferta(this);
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Oferta other = (Oferta) obj;
		if (nick == null) {
			if (other.nick != null)
				return false;
		} else if (!nick.equals(other.nick))
			return false;
		return true;
	}
	
}
