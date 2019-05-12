/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemas_distribuidos.cliente;
import java.io.File;
import java.net.Socket;
import java.util.Scanner;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;


public class Cliente {
    private int porta;
    private String host;
    private ComunicaThread com = new ComunicaThread();
    private boolean teste;
    private String comando; //Para testes
    
    
    public Cliente(String host,int porta,boolean teste,String comando) {
        this.porta = porta;
        this.teste = teste;
        this.host = host;
        this.comando = comando;
    }
     public static void main(String[] args) throws IOException, InterruptedException{
   
        File arquivo = new File("Porta_e_host.txt");
        if (arquivo.exists()) {
            FileReader arq = new FileReader("Porta_e_host.txt");
            BufferedReader lerArq = new BufferedReader(arq);
            int porta = 0;
            String host = "";
            String linha = "";
            while ((linha = lerArq.readLine()) != null) {
                String[] porta_e_host = linha.split(" ");
                String cliente_ou_servidor = porta_e_host[0];
                if (cliente_ou_servidor.toUpperCase().equals("CLIENTE")) {
                    // new Cliente("127.0.0.1", 1234).executa();
                    porta = Integer.parseInt(porta_e_host[1]);
                    host = porta_e_host[2];
                }
            }
            System.out.println(porta);
            new Cliente(host, porta,false,null).executa();

        }
    }
    
    
    public void executa() throws IOException, InterruptedException{
        Socket cliente = null;
        try{
          cliente = new Socket(this.host,this.porta);
        }catch(Exception e){
            System.out.println("Erro ao tentar conectar no servidor,verifica o ip e portas");
            System.exit(0);
        }
        ImprimeMensagem imprimir = new ImprimeMensagem(cliente.getInputStream(),this.com,this.teste);
        Thread im = new Thread(imprimir);
        im.start();
        //Lendo mensagem do teclado e mandando para o servidor
        
        LerComandos comandos = new LerComandos(cliente,this.com,this.teste,this.comando);
        Thread c = new Thread(comandos);
        c.start();
        
        im.join();
        c.stop();
        //cliente.close();
        
    }

 
    

}