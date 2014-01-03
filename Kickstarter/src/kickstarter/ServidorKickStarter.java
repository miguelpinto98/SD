package kickstarter;

import java.io.IOException;
import java.net.ServerSocket;

public class ServidorKickStarter {
	
	private static Kickstarter kstart;

	public static void main(String[] args) {
		kstart = new Kickstarter();
	}
	
	public void runSocktKickstarter() throws IOException {
		ServerSocket ss = new ServerSocket(9999);
	}

}
