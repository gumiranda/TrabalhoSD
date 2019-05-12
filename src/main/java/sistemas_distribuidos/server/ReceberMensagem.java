
package sistemas_distribuidos.server;



import sistemas_distribuidos.controle.Comando;
import  java.util.Scanner;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.InputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;


public class ReceberMensagem implements Runnable{
    private Socket cliente;
    private Servidor servidor;
    private Fila F1;
    private boolean exit = false;

    
    
    public ReceberMensagem(Socket cliente,Servidor servidor,Fila F1){
        this.cliente = cliente;
        this.servidor = servidor;
        this.F1 = F1;
        
    }
    
    public void run(){
        Scanner s;
        while(!this.exit){
           
            try {
                s = new Scanner(this.cliente.getInputStream());
                //PrintStream cliente_retorno = new PrintStream(this.cliente.getOutputStream());
                String comando;
                while(s.hasNextLine()){
                    comando = s.nextLine();
                    Comando c = new Comando(this.cliente,comando);
                    if(comando.toLowerCase().equals("quit")){
                        PrintStream cliente_retorno = new PrintStream(cliente.getOutputStream());
                        cliente_retorno.println("Quit Recebido");
                        this.stop();
                        break;
                    }else{
                        F1.put(c);
                    }
                    //cliente_retorno.println(servidor.processaComando(s.nextLine()));
                }
                //s.close();
            } catch (IOException ex) {
                this.stop();
       
                //Logger.getLogger(ReceberMensagem.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
      
    }
    
     public void stop() 
  { 
        this.exit = true; 
  } 
}


    
