/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kickstarter;

import java.io.ObjectInputStream;
import java.net.Socket;

/**
 *
 * @author serafim
 */
public class Handler extends Thread {
    private Socket s;
    
    public Handler(Socket s) {
        this.s= s;
    }
    
    public void run() {
    
        try {
              //Ler do input
            ObjectInputStream in = new ObjectInputStream(s.getInputStream());
            
        //Tudo o que lê do input é o pacote
            Pacote pacote = (Pacote) in.readObject();
            
        
        if(pacote.getAccao().equals(ServidorKickstarter.REGISTAR)) {
            System.out.println("Recebeu pacote Registar");
            System.out.println(pacote.getArgumentos().get(ServidorKickstarter.NOME_USER) + pacote.getArgumentos().get(ServidorKickstarter.PW_USER));
            
        }
            
        } catch (Exception e) {
        }
       
    }
    
    public boolean registaUtilizador(String nick, String pass) {
    	boolean res = false;
    	
    	if(!ServidorKickstarter.utilizadores.containsKey(nick)) {
    		Utilizador u = new Utilizador(nick, pass);
    		ServidorKickstarter.utilizadores.put(nick, u);
    		res = true;
    	}
    	return res;
    }
    
     public boolean validaUser(String nick, String pass) {
    	boolean res=false;
    	
    	if(ServidorKickstarter.utilizadores.containsKey(nick)) {
    		if(ServidorKickstarter.utilizadores.get(nick).getPassword().equals(pass))
    			res= true; 		
    	}
    	return res;
    }
}
