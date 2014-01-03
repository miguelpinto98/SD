package kickstarter;

public class Utilizador {
	private String nickname;	
	private String password;

	public Utilizador() {
		this.nickname = "";
		this.password = "";
	}

	public Utilizador(String nick, String pw) {
		this.nickname = nick;
		this.password = pw;
	}

	public Utilizador(Utilizador c) {
		this.nickname = c.getNickname();
		this.password = c.getPassword();
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


