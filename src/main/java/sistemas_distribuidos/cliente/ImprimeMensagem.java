/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemas_distribuidos.cliente;


import  java.util.Scanner;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ImprimeMensagem implements Runnable{
    private InputStream servidor;
    ComunicaThread com;
    private boolean exit = false;
    private boolean teste;
       
    public ImprimeMensagem(InputStream servidor,ComunicaThread com,boolean teste){
        this.servidor = servidor;
        this.com = com;
        this.teste = teste;
    }
    
    //Imprime mensagem ao receber do servidor
    public void run(){
        Scanner s = new Scanner(this.servidor);
        while(!exit){
            boolean verifica;
            long start;
            long elapsed;

            while(true){
                verifica = false;
    
                this.com.tentaExecutar();
     
                if(this.com.morta){
                    this.stop();
                    break;
                }
                start = System.currentTimeMillis();

                //Esperar por 5 segundos
                while(true){
                    if(!verifica){
                        elapsed = System.currentTimeMillis() - start;
                        if(elapsed > 5000)
                            break;
                    }else{
                        break;
                    }
                    while(s.hasNextLine()){
                        verifica = true;
                        System.out.println(s.nextLine());
                        this.com.FinalLeitura();
                    }
                }
                if(!verifica){
                    System.out.println("Tempo de reposta excedido, conexao perdida");
                    this.stop();
                    break;
                }
           
            }
        }
        
    }
    
    public void stop() 
    { 
        this.exit = true; 
    } 
}
