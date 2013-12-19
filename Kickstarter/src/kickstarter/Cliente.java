package kickstarter;

public class Cliente {
	private String nickname;	
	private String password;


	public Cliente() {
		this.nickname = "";
		this.password = "";
	}

	public Cliente(String nick, String pw) {
		this.nickname = nick;
		this.password = pw;
	}

	public Cliente(Cliente c) {
		this.nickname = c.getNickname();
		this.password = c.getPassword();
	}

	public String getNickname() {
		return this.nickname;
	}

	public String getPassword() {
		return this.password;
	}

	public Cliente clone() {
		return new Cliente(this);
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
			Cliente c = (Cliente) o;
			return this.getNickname().equals(c.getNickname());
		}
	}
}


