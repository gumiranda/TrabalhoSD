/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grpc;

import gRPC.proto.ServerResponse;
import io.grpc.stub.StreamObserver;
import java.util.ArrayList;
import java.net.ServerSocket;
import java.io.IOException;
import java.net.Socket;
import java.io.PrintStream;
import java.io.OutputStream;
import java.math.BigInteger;

public class Comando {
    private String comando;
    private String valor;
    private BigInteger chave;
    private StreamObserver<ServerResponse> o;

     public Comando(String comando,String valor,BigInteger chave,StreamObserver<ServerResponse> o){
        this.o = o;
        this.valor = valor;
        this.chave = chave;
        this.comando = comando;
    }
          public Comando(String comando,BigInteger chave,StreamObserver<ServerResponse> o){
        this.o = o;
        this.chave = chave;
        this.comando = comando;
        
    }
    public synchronized String getComando(){
        return this.comando;
    }
    public synchronized BigInteger getChave(){
        return this.chave;
    }
    public synchronized String getValor(){
        return this.valor;
    }
    public synchronized StreamObserver<ServerResponse> getObserver(){
        return this.o;
    }
    public synchronized void setValor(String valor){
    this.valor=valor;
    }
    public synchronized void setChave(BigInteger valor){
    this.chave=valor;
    }
    public synchronized void setObserver(StreamObserver<ServerResponse> o){
    this.o = o;
    }
    public void imprimir(){
        System.out.println("COMANDO: "+this.comando);
    }
}
