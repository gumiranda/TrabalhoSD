/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.controle;
import com.example.RequisicaoChave;
import com.example.RequisicaoValor;
import com.example.ServerResponse;
import com.example.ServiceGrpc;
import io.grpc.stub.StreamObserver;
import com.example.server.Fila;
import java.math.BigInteger;
/**
 *
 * @author Samsung
 */
public class ServiceImpl extends ServiceGrpc.ServiceImplBase{
    private Fila f1;
    public ServiceImpl(Fila f1){
        this.f1 = f1;
    }
    @Override
    public void insert(RequisicaoValor r,StreamObserver<ServerResponse> rO){
        System.out.println("Comando insert acionado");
        Comando c;
        c = new Comando("INSERT",r.getValor(),new BigInteger(Long.toString(r.getChave())),rO);
        this.f1.put(c);
    }
        @Override
    public void update(RequisicaoValor r,StreamObserver<ServerResponse> rO){
        System.out.println("Comando UPDATE acionado");
        Comando c;
        c = new Comando("UPDATE",r.getValor(),new BigInteger(Long.toString(r.getChave())),rO);
        this.f1.put(c);
    }
            @Override
    public void delete(RequisicaoChave r,StreamObserver<ServerResponse> rO){
        System.out.println("Comando DELETE acionado");
        Comando c;
        c = new Comando("DELETE",new BigInteger(Long.toString(r.getChave())),rO);
        this.f1.put(c);
    }
     @Override
    public void select(RequisicaoChave r,StreamObserver<ServerResponse> rO){
        System.out.println("Comando SELECT acionado");
        Comando c;
        c = new Comando("SELECT",new BigInteger(Long.toString(r.getChave())),rO);
        this.f1.put(c);
    }    
    
}
