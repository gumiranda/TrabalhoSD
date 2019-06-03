/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grpc;

import gRPC.proto.ChaveRequest;
import gRPC.proto.ServerResponse;
import gRPC.proto.ServicoGrpc;
import gRPC.proto.ValorRequest;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import java.util.Scanner;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ImprimeMensagem implements Runnable {

    private Cliente cliente;
    ComunicaThread com;
    private boolean exit = false;
    private static final Logger logger = Logger.getLogger(Cliente.class.getName());
    private final ServicoGrpc.ServicoBlockingStub blockingStub;
    private String c;
    public ImprimeMensagem(Cliente cliente, ComunicaThread com) {
        this.cliente = cliente;
        blockingStub = ServicoGrpc.newBlockingStub(cliente.channel);
        this.com = com;
    }
    
    public ImprimeMensagem(Cliente cliente,String c, ComunicaThread com) {
        this.cliente = cliente;
        this.c = c;
        blockingStub = ServicoGrpc.newBlockingStub(cliente.channel);
        this.com = com;
    }    

    public void insertOrUpdate(String chave, String valor, String comando) {
        logger.info(comando + " no dado com chave: " + chave+ " e valor: " + valor);
        ValorRequest request = ValorRequest.newBuilder().setChave(chave).setValor(valor).build();
        ServerResponse response = null;
        try {
            if (comando.equals("INSERT")) {
               int portaCliente = this.cliente.porta;
               if(portaCliente != 59043){
                   System.out.println("Verifica erro");
                                response = blockingStub.insert(request);
               }else{
                               response = blockingStub.insert(request);
               }
            } else if (comando.equals("UPDATE")) {
                response = blockingStub.update(request);
            } else {
                logger.log(Level.WARNING, "Comando inválido a ser enviado pro servidor: {0}");
                return;
            }
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
            return;
        }
        logger.info("Resposta do servidor: " + response.getResponse());

    }

    public void selectOrDelete(String chave, String comando) {
        logger.info(comando + " no dado com chave: " + chave);
        ChaveRequest request = ChaveRequest.newBuilder().setChave(chave).build();

        ServerResponse response = null;
        try {
            if (comando.equals("SELECT")) {
                response = blockingStub.select(request);
            } else if (comando.equals("DELETE")) {
                response = blockingStub.delete(request);
            } else {
                logger.log(Level.WARNING, "Comando inválido a ser enviado pro servidor: {0}");
                return;
            }
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
            return;
        }
        logger.info("Resposta do servidor: " + response.getResponse());

    }
    //Imprime mensagem ao receber do servidor
    public void run() {
        while (!exit) {
            boolean verifica;
            long start;
            long elapsed;

            while (true) {
                verifica = false;

                this.com.tentaExecutar();

                if (this.com.morta) {
                    this.stop();
                    break;
                }
                start = System.currentTimeMillis();

                //Esperar por 5 segundos
                while (true) {
                    if (!verifica) {
                        elapsed = System.currentTimeMillis() - start;
                        if (elapsed > 5000) {
                            break;
                        }
                    } else {
                        break;
                    }
                    while (this.cliente.comando != null) {
                        verifica = true;
                        System.out.println("Thread Imprime mensagem vai enviar o comando: " + this.cliente.comando);
                        this.enviaComando(this.cliente.comando);
                        this.cliente.comando = null;
                        this.com.FinalLeitura();
                    }
                }
                if (!verifica) {
                    System.out.println("Tempo de reposta excedido, conexao perdida");
                    this.stop();
                    break;
                }

            }
        }

    }

    public void stop() {
        this.exit = true;
    }

    public void enviaComando(String comando) {
        boolean flag = true;
        boolean fi = false;
        String cmd[] = comando.split(" ");
        cmd[0] = cmd[0].toUpperCase();
        if (cmd[0].equals("INSERT")) {
            this.insertOrUpdate(cmd[1],cmd[2], "INSERT");
        } else if (cmd[0].equals("DELETE")) {
            this.selectOrDelete(cmd[1], "DELETE");
        } else if (cmd[0].equals("SELECT")) {
            this.selectOrDelete(cmd[1], "SELECT");
        } else if (cmd[0].equals("UPDATE")) {
            this.insertOrUpdate(cmd[1] , cmd[2], "UPDATE");
        } else {
            System.out.println("Comando inválido , portanto não será enviado para o servidor");
        }
    }

}
