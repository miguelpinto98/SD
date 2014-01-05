/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kickstarter;

import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author serafim
 */
public class Pacote implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String accao;
    private HashMap<String,String> argumentos; //chave NomeUser -- serafim

    public Pacote(String a, HashMap<String,String> args) {
        this.accao = a;
        this.argumentos = args;
    }

    public String getAccao() {
        return accao;
    }

    public HashMap<String, String> getArgumentos() {
        return argumentos;
    }
}


