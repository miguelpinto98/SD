package kickstarter;

import java.io.Serializable;

public class Oferta implements Comparable<Oferta>, Serializable{
	private String nick;
	private double doado;
	
	public Oferta() {
		this.nick = "";
		this.doado = 0;
	}
	
	public Oferta(String nick, double mont) {
		this.nick = nick;
		this.doado = mont;
	}
	
	public Oferta(Oferta o) {
		this.nick = o.getNick();
		this.doado = o.getDoado();
	}
	
	public String getNick() {
		return this.nick;
	}
	
	public double getDoado() {
		return this.doado;
	}
 
	public int compareTo(Oferta o) {
		if(o.getDoado() < this.doado) return -1;
		if(o.getDoado() > this.doado) return 1;
		else return 0;
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
		if (Double.doubleToLongBits(doado) != Double
				.doubleToLongBits(other.doado))
			return false;
		if (nick == null) {
			if (other.nick != null)
				return false;
		} else if (!nick.equals(other.nick))
			return false;
		return true;
	}
	
}
