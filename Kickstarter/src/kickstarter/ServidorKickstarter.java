package kickstarter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeSet;

public class ServidorKickstarter { 
	public static int CODIGO=1;
    public static int PORTA=9999;
    public static String REGISTAR = "RegistarUser";
    public static String NOME_USER = "NomeUser";
    public static String PW_USER = "PwUser";
    public static final String ENTRAR = "Entrar";

    private static Kickstarter k = null;
	
    public static void main(String args[]) throws IOException, ClassNotFoundException {
        ServerSocket sv = new ServerSocket(PORTA);
        k = new Kickstarter();
        
        while(true) {
            Socket cliente = sv.accept();
            System.out.println("Entrou no Servidor - 1");
            
            Handler thread = new Handler(cliente,k);
            thread.start();
        }
    }
    
    
	
}