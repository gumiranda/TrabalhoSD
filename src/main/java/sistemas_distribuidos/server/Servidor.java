package sistemas_distribuidos.server;

/**
 *
 * @author Natan Rodovalho
 */

import java.util.ArrayList;
import java.net.ServerSocket;
import java.io.IOException;
import java.net.Socket;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.PrintStream;
import java.io.OutputStream;
import java.util.concurrent.*;
import static javax.imageio.ImageIO.getCacheDirectory;

public class Servidor {
    private int porta;
    private ArrayList<Socket> clientes;
    private Fila F2;
    private Fila F3;
    private Fila F1;
    private int quantidade_threads = 15;
    private BaseDados Banco;
    
     public static void main(String[] args) throws IOException{
        File arquivo = new File("Porta_e_host.txt");
        System.out.println(System.getProperty("user.dir"));
System.out.println(new File("Porta_e_host.txt").getAbsolutePath());
        if (arquivo.exists()) {
            FileReader arq = new FileReader("Porta_e_host.txt");
            BufferedReader lerArq = new BufferedReader(arq);
            int porta = 0;
            String linha = "";
            while ((linha = lerArq.readLine()) != null) {
                String[] porta_e_host = linha.split(" ");
                String cliente_ou_servidor = porta_e_host[0];
                if (cliente_ou_servidor.toUpperCase().equals("SERVIDOR")) {
                    // new Cliente("127.0.0.1", 1234).executa();
                    porta = Integer.parseInt(porta_e_host[1]);

                }
            }
            new Servidor(porta).executa();

        }
        //new Servidor(1234).executa();
       
    }
         
    public Servidor(int porta) throws IOException{
        this.porta = porta;
        this.clientes = new ArrayList<Socket>();
        this.F1 = new Fila();
        this.F2 = new Fila();
        this.F3 = new Fila();
        this.Banco = new BaseDados();
        this.Banco.RecuperardoLog("Log.txt");
    }
    
    public void executa() throws IOException{
        
        ServerSocket servidor = new ServerSocket(this.porta);
        
        //Devinindo POOL de threads
        ExecutorService thds = Executors.newFixedThreadPool(this.quantidade_threads);
        
        //Para copiar de F1 para F2 e para F3
        CopiarLista copy = new CopiarLista(this.F1,this.F2,this.F3);
        new Thread(copy).start();
        
        //Criando Log
        Log log = new Log(this.F2);  
        new Thread(log).start();
        
        
        //Thread para aplicar operacoes ao Banco de Dados
        AplicarAoBanco bancoDados = new AplicarAoBanco(this.Banco,this.F3,this);
        new Thread(bancoDados).start();
     
        while(true){
            Socket cliente = servidor.accept();
            System.out.println("Nova conexão com o cliente " +cliente.getInetAddress().getHostAddress());
            this.clientes.add(cliente);
            ReceberMensagem msg = new ReceberMensagem(cliente,this,this.F1);
            thds.execute(msg);
        }
    }
    
    public synchronized String MandarMensagem(String mensagem){
        //Mandar mensagem para os clientes
        return mensagem;
    }
    
    
}
