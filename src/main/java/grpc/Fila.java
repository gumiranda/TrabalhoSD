/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grpc;


import java.util.ArrayList;
import java.util.List;

public class Fila {
    private ArrayList<Comando> comandos = new ArrayList<Comando>();
    private boolean possui_dados = false;
    
    
    
    public synchronized void imprimir(){
        for(int i=0;i<this.comandos.size();i++){
            System.out.println("COMANDO: "+i);
            this.comandos.get(i).imprimir();
        }
    } 
    
    public synchronized void copiaArrayComandos(ArrayList<Comando> origem,ArrayList<Comando> destino){
        for(Comando c:origem){
            destino.add(c);
        }
    }
            
    //Funcao que esvazia F1 completamente
    public synchronized ArrayList getAll(){
        ArrayList<Comando> aux = new ArrayList<Comando>();
        while(!this.possui_dados){
            try{
                wait();
            }catch(InterruptedException e){}
        }
        
        //Copiando array para outro
        this.possui_dados = false;
        copiaArrayComandos(this.comandos,aux);
         for(int i=0;i<this.comandos.size();i++){
            this.comandos.remove(i);
        }
        notifyAll();
        return aux;
    }
    
    public synchronized void put(Comando c){
        this.possui_dados = true;
        this.comandos.add(c);
        notifyAll();
    } 
    
    public synchronized void putArray(ArrayList<Comando> c){
        this.possui_dados = true;
        for(int i=0;i<c.size();i++){
            this.comandos.add(c.get(i));
        }
        notifyAll();
    }
    
     public synchronized Comando getFirst(){
        while(!this.possui_dados){
            try{
                wait();
            }catch(InterruptedException e){}
        }
        notifyAll();
        Comando c = this.comandos.get(0);
        this.comandos.remove(0);
        
        if(this.comandos.size() == 0){
            this.possui_dados = false;
        }
        return c;
    }
}
