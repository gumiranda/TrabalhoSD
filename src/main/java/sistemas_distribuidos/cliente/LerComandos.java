/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemas_distribuidos.cliente;



import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;

public class LerComandos implements Runnable {
    private Socket cliente;
    private ComunicaThread com;
    private boolean exit = false;
    private boolean teste;
    private String comando;

    public synchronized boolean verificaNumero(String str2){
        double valor;
        
        try {
	    valor = (Double.parseDouble(str2));
            return true;
	} catch (NumberFormatException e) {	  
            return false;
	}
    }
    
    public LerComandos(Socket cliente,ComunicaThread com,boolean teste,String comando){
        this.cliente = cliente;
        this.com = com;
        this.teste = teste;
        this.comando = comando;
    }
    
    
    //Retorna false caso a thread tenha q parar ou false caso contrario
    public synchronized  void validarComandos(String comando,PrintStream saida){
        String aux;
        aux = comando.toLowerCase();
        boolean flag = true;
        boolean fi = false;
        String comandos[] = aux.split(" ");
        
        
            switch(comandos[0]){
                case "select":
                    if(comandos.length < 2){
                        System.out.println("Quantidade de comandos invalido: Minimo de  2");
                        flag = false;
                        
                    }else{
                        if(!this.verificaNumero(comandos[1])){
                            System.out.println("Tipo de chave Invalida");
                            flag = false;
                        }
                    }
                    break;
                case "insert":
                    if(comandos.length < 3){
                        System.out.println("Quantidade de comandos invalido: Minimo de  3");
                        flag = false;
                        
                    }else{
                        if(!this.verificaNumero(comandos[1])){
                            System.out.println("Tipo de chave Invalida");
                            flag = false;
                        }
                    }
                    break;
                case "delete":
                    if(comandos.length < 2){
                        System.out.println("Quantidade de comandos invalido: Minimo de  2");
                        flag = false;
                        
                    }else{
                        if(!this.verificaNumero(comandos[1])){
                            System.out.println("Tipo de chave Invalida");
                            flag = false;
                        }
                    }
                    break;
                    
                case "update":
                    if(comandos.length < 3){
                        System.out.println("Quantidade de comandos invalido: Minimo de  3");
                        flag = false;
                        
                    }else{
                        if(!this.verificaNumero(comandos[1])){
                            System.out.println("Tipo de chave Invalida");
                            flag = false;
                        }
                    }
                    break;
                    
                case "quit":
                    //System.out.println("Programa finalizado");
                    this.com.Matar();
                    break;
                    
                default:
                    flag = false;
                    break;
                
            }
            if(flag){
               this.com.indicaFinal();
               saida.println(comando);
           
            }
            else{
                System.out.println("Comando Invalido: "+aux);
                System.out.println("Comando nao executado tente novamente");
              
            }
           
    }
            
            
  
    
    
    public void run(){
       if(!this.teste){
            System.out.println("Comandos Diponiveis:");
            System.out.println("INSERT,DELETE,READ e DELETE");
       }else{
            System.out.println("Cliente para teste");
       }
        while(!this.exit){
            PrintStream saida;
            
            
            if(this.teste){
                try {
                    saida = new PrintStream(this.cliente.getOutputStream());
                     validarComandos(this.comando,saida);
                     validarComandos("quit",saida);
                     break;
                    
                } catch (IOException ex) {
                    Logger.getLogger(LerComandos.class.getName()).log(Level.SEVERE, null, ex);
                    
                
            }
            }else{
                Scanner teclado = new Scanner(System.in);
                
                 boolean verifica = false;
                try {
                    saida = new PrintStream(this.cliente.getOutputStream());
                    System.out.print("Digite o comando: ");

                    while(teclado.hasNextLine()){

                       validarComandos(teclado.nextLine(),saida); 
                    }
                } catch (IOException ex) {
                    Logger.getLogger(LerComandos.class.getName()).log(Level.SEVERE, null, ex);
                }
         
            
            }
        }

    }
    
    public void stop() 
    { 
        this.exit = true; 
    } 
    
}
