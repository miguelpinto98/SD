package kickstarter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;

public class ClienteKickstarter {
    private static String ip = "localhost";
    private static int port = 9999;
    
    public static Scanner in = new Scanner(System.in);
    public static HashMap<String, String> hash = new HashMap<>();
    public static Pacote p = null;
    public static ObjectOutputStream o = null;
    
    
    
    public static void main(String args[]) throws IOException {
        Socket s = new Socket(ip,port);
        int opt;
        
        do {
        	opt = menuInicial();
            hash = null;
            o = null;
        	
        	if(opt==1) {
        		in.nextLine();
        		System.out.println("Nick");
        		String user = in.next();
        		System.out.println("Pass");
                String pw = in.next();
                
                hash = new HashMap<>();
                hash.put(ServidorKickstarter.NOME_USER, user);
                hash.put(ServidorKickstarter.PW_USER, pw);
                p = new Pacote(ServidorKickstarter.REGISTAR,hash);
                
                o = new ObjectOutputStream(s.getOutputStream());
                o.writeObject(p);
                o.flush();
                
        		BufferedReader sktInput = new BufferedReader(new InputStreamReader(s.getInputStream()));
        		System.out.println(sktInput.readLine());
                
        	} else {
        		if(opt == 2) {
        			in.nextLine();
            		System.out.println("Nick");
            		String user = in.next();
            		System.out.println("Pass");
                    String pw = in.next();
                    
                    hash = new HashMap<>();
                    hash.put(ServidorKickstarter.NOME_USER, user);
                    hash.put(ServidorKickstarter.PW_USER, pw);
                    p = new Pacote(ServidorKickstarter.ENTRAR,hash);
                    
                    o = new ObjectOutputStream(s.getOutputStream());
                    o.writeObject(p);
                    o.flush();
        		}
        		else {
        			if(opt == 3)
        				System.exit(3);
        			else
        				System.out.println("Opção Inválida!");
        		}
        	}
        		
        } while(true);

    }
    
    public static int menuInicial() {
    	System.out.println("1 - Registar");
        System.out.println("2 - Entrar");
        System.out.println("3 - Sair");
        
        return in.nextInt();    
    }
    
}
