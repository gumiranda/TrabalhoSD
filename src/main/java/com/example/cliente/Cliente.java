/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.cliente;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import java.io.File;
import java.net.Socket;
import java.util.Scanner;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import com.example.RequisicaoChave;
import com.example.RequisicaoValor;
import com.example.ServerResponse;
import com.example.ServiceGrpc;
import io.grpc.stub.StreamObserver;
import java.math.BigInteger;
import java.util.ArrayList;
public class Cliente {
    private int porta;
    private String host;
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
//        Socket cliente = null;
        try{
//          cliente = new Socket(this.host,this.porta);
Scanner s = new Scanner(System.in);
String comando = null;
String [] cmd ;
long chave ;
String valor;
ManagedChannel m = ManagedChannelBuilder.forAddress(this.host,this.porta).usePlaintext().build();
ServiceGrpc.ServiceBlockingStub sbs = ServiceGrpc.newBlockingStub(m);
ServerResponse sr;

while(!comando.equals("SAIR")){
System.out.println("Digite um dos comandos: INSERT|UPDATE|DELETE|SELECT seguidos de chave e valor(caso necessário)");
comando = s.next();
cmd = comando.split(" ");
cmd[0] = comando.toUpperCase();

if(cmd[0].equals("INSERT")){
    chave = Long.parseLong(cmd[1]);
    valor = cmd[2];
    sr = sbs.insert(RequisicaoValor.newBuilder().setChave(chave).setValor(valor).build());
    System.out.println("Resposta do servidor"+ sr);

}else if(cmd[0].equals("DELETE")){
        chave = Long.parseLong(cmd[1]);
    sr = sbs.delete(RequisicaoChave.newBuilder().setChave(chave).build());
        System.out.println("Resposta do servidor"+ sr);

}
else if(cmd[0].equals("SELECT")){
        chave = Long.parseLong(cmd[1]);
    sr = sbs.select(RequisicaoChave.newBuilder().setChave(chave).build());    
        System.out.println("Resposta do servidor"+ sr);

}else if(cmd[0].equals("UPDATE")){
    chave = Long.parseLong(cmd[1]);
    valor = cmd[2];
    sr = sbs.update(RequisicaoValor.newBuilder().setChave(chave).setValor(valor).build());
        System.out.println("Resposta do servidor"+ sr);

}
else{
    System.out.println("Comando inválido");
System.out.println("Digite um dos comandos: INSERT|UPDATE|DELETE|SELECT seguidos de chave e valor(caso necessário)");
}
m.shutdown();
}
        }catch(Exception e){
            System.out.println("Erro ao tentar conectar no servidor,verifica o ip e portas");
            System.exit(0);
        }

        //cliente.close();
        
    }

 
    

}