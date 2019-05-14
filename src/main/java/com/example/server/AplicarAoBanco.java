package com.example.server;


import com.example.ServerResponse;
import java.io.UnsupportedEncodingException;


import java.net.Socket;
import java.math.BigInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.IOException;
import java.io.PrintStream;
import java.io.FileInputStream;
import java.io.DataInputStream;
import java.io.FileNotFoundException;
import com.example.controle.Comando;
import io.grpc.stub.StreamObserver;


public class AplicarAoBanco implements Runnable{
    private BaseDados banco;
    private Fila F3;
    private Servidor servidor;
    
    
    public AplicarAoBanco(BaseDados banco,Fila F3,Servidor s){
        this.banco = banco;
        this.F3 = F3;
        this.servidor = s;
    }
   
     public String ProcessaComando(String comando,StreamObserver<ServerResponse> responseObserver){
            ServerResponse sr;
            String comandos[] = comando.split(" ");
            byte[] dados = null;
            String retorno = null;  
            BigInteger chave = this.banco.getChave(comandos[1]);

            if(comandos.length >=3 )
                dados = this.banco.getDados(comandos);
 
            byte[] retorno_select = null;
                    String cmd = comandos[0].toLowerCase();

                   if(cmd.equals("select")){
                   if(this.banco.verifica(chave)){
                         retorno_select = this.banco.get(chave);
                          try {
                            retorno = new String(retorno_select, "UTF-8");
                            
                        } catch (UnsupportedEncodingException ex) {
                            Logger.getLogger(AplicarAoBanco.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }else{
                        retorno = "Chave nao existe";
                    }
                   }
                           else if(cmd.equals("insert")){
                                                   retorno = this.banco.add(chave, dados);

                           }
        else if(cmd.equals("delete")){
                                retorno = this.banco.Deletar(chave);

        }
                           else if(cmd.equals("update")){
                                retorno = this.banco.update(chave, dados);

                           }

                
             
            //Tratamento para tentar evitar memoria Leak:
            comando = null;
            comandos = null;
            dados = null;
            chave = null;
            System.gc();
            
            return retorno;
    }
     
    public void run(){
        while(true){
            Comando c = F3.getFirst(); //Tratamento para tentar evitar memoria Leak:
            StreamObserver<ServerResponse> responseObserver = c.getObserver();
            String comando = c.getComando();
            String retorno = this.ProcessaComando(comando,responseObserver);
//            c = null;
            //          cliente = null;
            //        comando = null;
            //      retorno = null;
            //    System.gc();
            //PrintStream cliente_retorno = new PrintStream(cliente.getOutputStream());
            //cliente_retorno.println(servidor.MandarMensagem(retorno));
            
        }
    }
    
    
    
}
