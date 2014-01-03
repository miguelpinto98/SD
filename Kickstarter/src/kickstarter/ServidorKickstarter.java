package kickstarter;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorKickstarter { 
	public static int CODIGO=1;
    public static int PORTA=9999;
    
    public static final String REGISTAR = "RegistarUser";
    public static final String NOME_USER = "NomeUser";
    public static final String PW_USER = "PwUser";
    public static final String ENTRAR = "Entrar";
	
    public static final String ID = "ID Projeto";
	public static final String MONTANTE = "Montante ajuda projeto";
    public static final String PROJETO = "Projeto ";
    public static final String FINANCIAR = "FinanciarProjeto";

    public static final String CRIAR_PROJETO = "CriarProjeto";
    public static final String NOME_PROJETO = "NomeProjeto";
    public static final String DESC_PROJETO = "DescricaoProjeto";
    public static final String MONTANTE_PROJETO = "MontantePRojeto";
    
    public static final String LISTANAOFINANCIADOS = "ListaNaoFinanciados";
	public static final String PROJ_FINANCIADOS = "ListarProjetosFinanciados";
	
	public static final String NCONTRIBUTOS = "NumeroContributosVisiveis";
	public static final String INF_PROJ = "InformacaoProjeto";

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