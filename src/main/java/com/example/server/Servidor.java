package com.example.server;

/**
 *
 * @author Natan Rodovalho
 */

import com.example.controle.ServiceImpl;
import com.example.server.Log;
import com.example.server.Fila;
import com.example.server.CopiarLista;
import com.example.server.BaseDados;
import com.example.server.AplicarAoBanco;
import com.google.common.io.Files;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import java.util.ArrayList;
import java.net.ServerSocket;
import java.io.IOException;
import java.net.Socket;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.*;
import static javax.imageio.ImageIO.getCacheDirectory;

public class Servidor {
    private int porta;
    private ArrayList<Socket> clientes;
    private Fila F2;
    private Fila F3;
    private Fila F4;
    private Fila F1;
    private int quantidade_threads = 15;
    private BaseDados Banco;
    private Server server;
     public static void main(String[] args) throws IOException{
  inicializaServers();     
    }
         
    public Servidor(int porta) throws IOException{
        this.porta = porta;
        this.clientes = new ArrayList<Socket>();
        this.F1 = new Fila();
        this.F2 = new Fila();
        this.F3 = new Fila();
        this.F4 = new Fila();
        this.Banco = new BaseDados();
        this.Banco.RecuperardoLog("Log.txt");
    }
    private static void inicializaServers() throws FileNotFoundException, IOException{
        String nomeArq = "Servers.txt";
        File arquivo = new File("Servers.txt");
        System.out.println(System.getProperty("user.dir"));
        System.out.println(new File("Servers.txt").getAbsolutePath());
        if (arquivo.exists()) {
            FileReader arq = new FileReader("Servers.txt");
            BufferedReader lerArq = new BufferedReader(arq);
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("Servers.txt", true)));
            int porta = 0;
            String ultima = ";0000";
            String linha;
            String [] v = null;
            while ((linha = lerArq.readLine()) != null) {
                ultima = linha;
            }
            if(ultima!= null){
                v = ultima.split(";");
            }
            
                   if (v != null && v[1].equals("8080")) {
                System.out.println("Primeiro servidor iniciado");
                porta = 8081;
               //out.println("");
               out.println("Server;2");
               out.println("Port;"+porta);
               new Servidor(porta).executa();
            }

            else if (v != null && v[1].equals("8081")) {
                System.out.println("Segundo servidor já foi iniciado");
                porta = 8082;
               //out.println("");
               out.println("Server;3");
               out.println("Port;"+porta);
                           new Servidor(porta).executa();
            }

            else if (v != null && v[1].equals("8082")) {
                System.out.println("Terceiro servidor já foi iniciado");
                porta = 8083;
               //out.println("");
               out.println("Server;4");
               out.println("Port;"+porta);
                new Servidor(porta).executa();
            }

            else if (v != null && v[1].equals("8083")) {
                System.out.println("Quarto servidor já foi iniciado");
                porta = 8084;
               //out.println("");
               out.println("Server;5");
               out.println("Port;"+porta);
               new Servidor(porta).executa();

            }

            else {
                porta = 8080;
               //out.println("");
               out.println("Server;1");
               out.println("Port;"+porta);
               new Servidor(porta).executa();

            }

        }
    }
    public void executa() throws IOException{
        
try{
        //Devinindo POOL de threads
        ExecutorService thds = Executors.newFixedThreadPool(this.quantidade_threads);
        
        this.server = ServerBuilder.forPort(this.porta).addService(new ServiceImpl(this.F1)).build();
        System.out.println("Servidor iniciando");
        this.server.start();
        System.out.println("Servidor iniciado na porta "+this.porta);
        //Para copiar de F1 para F2 e para F3
        CopiarLista copy = new CopiarLista(this.F1,this.F2,this.F3,this.F4,this.porta);
        new Thread(copy).start();        
        //Criando Log
        Log log = new Log(this.F2);  
        new Thread(log).start();
        //Thread para aplicar operacoes ao Banco de Dados
        AplicarAoBanco bancoDados = new AplicarAoBanco(this.Banco,this.F3,this);
        new Thread(bancoDados).start();
}
catch(Exception e){
        System.out.println(e);
}


    }
    
    public synchronized String MandarMensagem(String mensagem){
        //Mandar mensagem para os clientes
        return mensagem;
    }
    
    
}
