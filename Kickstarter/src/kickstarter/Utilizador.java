package kickstarter;

import java.io.Serializable;

public class Utilizador implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nickname;	
	private String password;
	private boolean ativo;

	public Utilizador() {
		this.nickname = "";
		this.password = "";
		this.ativo = false;
	}

	public Utilizador(String nick, String pw) {
		this.nickname = nick;
		this.password = pw;
		this.ativo = false;
	}

	public Utilizador(Utilizador c) {
		this.nickname = c.getNickname();
		this.password = c.getPassword();
		this.ativo = c.isAtivo();
	}
	
	public boolean isAtivo() {
		return this.ativo;
	}
	
	public void setAtivo(boolean a) {
		this.ativo = a;
	} 

	public String getNickname() {
		return this.nickname;
	}

	public String getPassword() {
		return this.password;
	}

	public Utilizador clone() {
		return new Utilizador(this);
	}

	public String toString() {
		StringBuilder s = new StringBuilder("***Cliente***\n");
		s.append("Utilizador: " + this.getNickname());
		s.append("Password " + this.getPassword());
		return s.toString();
	}

	public boolean equals(Object o) {
		if (this == o)
			return true;
		if ((o == null) || (o.getClass() != this.getClass()))
			return false;
		else {
			Utilizador c = (Utilizador) o;
			return this.getNickname().equals(c.getNickname());
		}
	}
}


