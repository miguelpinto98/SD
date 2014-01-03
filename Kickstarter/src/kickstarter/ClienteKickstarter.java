package kickstarter;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;

public class ClienteKickstarter {
    private static String ip = "localhost";
    private static int port = 9999;
    
    public static Scanner in;
    
    public static void main(String args[]) throws IOException {
        
        Socket s = new Socket(ip,port);
        System.out.println("Novo cliente conectado!!!");
        
        in = new Scanner(System.in);
        String user = in.nextLine();
        String pw = in.nextLine();
        
        //Registar
        HashMap<String,String> hash = new HashMap<>();
        hash.put(ServidorKickstarter.NOME_USER, user);
        hash.put(ServidorKickstarter.PW_USER, pw);
        Pacote p = new Pacote(ServidorKickstarter.REGISTAR,hash);
        
        //Enviar, parte comun
        ObjectOutputStream o = new ObjectOutputStream(s.getOutputStream());
        o.writeObject(p);
        o.flush();
  
    }
    
}
