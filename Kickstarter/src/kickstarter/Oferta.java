package kickstarter;

public class Oferta implements Comparable<Oferta>{
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
}
