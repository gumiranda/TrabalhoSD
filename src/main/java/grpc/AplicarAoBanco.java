package grpc;

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
import io.grpc.stub.StreamObserver;
import gRPC.proto.ServerResponse;

public class AplicarAoBanco implements Runnable {

    private BaseDados banco;
    private Fila F3;
    private Servidor servidor;

    public AplicarAoBanco(BaseDados banco, Fila F3, Servidor s) {
        this.banco = banco;
        this.F3 = F3;
        this.servidor = s;
    }

    public String ProcessaComando(String comando) {
        String comandos[] = comando.split(" ");
        byte[] dados = null;
        String retorno = null;
        BigInteger chave = this.banco.getChave(comandos[1]);

        if (comandos.length >= 3) {
            dados = this.banco.getDados(comandos);
        }

        byte[] retorno_select = null;
        String cmd = comandos[0].toLowerCase();

        if (cmd.equals("select")) {
            if (this.banco.verifica(chave)) {
                retorno_select = this.banco.get(chave);
                try {
                    retorno = new String(retorno_select, "UTF-8");

                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(AplicarAoBanco.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                retorno = "Chave nao existe";
            }
        } else if (cmd.equals("insert")) {
            retorno = this.banco.add(chave, dados);

        } else if (cmd.equals("delete")) {
            retorno = this.banco.Deletar(chave);

        } else if (cmd.equals("update")) {
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

    public String ProcessaComando(Comando comando, StreamObserver<ServerResponse> responseObserver) {

        String command;
        command = comando.getComando()+" "+comando.getChave();
        if (comando.getValor() != null) {
            command = command+" "+ comando.getValor();
        }
                String comandos[] = command.split(" ");
        byte[] dados = null;
        String retorno = null;

        BigInteger chave = comando.getChave();
        try {
            ServerResponse sr = null;
            if (comandos.length >= 3) {
                dados = this.banco.getDados(comandos);
            }

            byte[] retorno_select = null;
            String cmd = comandos[0].toLowerCase();

            if (cmd.equals("select")) {
                if (this.banco.verifica(chave)) {
                    retorno_select = this.banco.get(chave);
                    try {
                        retorno = new String(retorno_select, "UTF-8");
                        sr = ServerResponse.newBuilder().setResponse(retorno).build();
                    } catch (UnsupportedEncodingException ex) {
                        Logger.getLogger(AplicarAoBanco.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    retorno = "Chave nao existe";
                    sr = ServerResponse.newBuilder().setResponse("Chave nao existe").build();
                }
                responseObserver.onNext(sr);
                responseObserver.onCompleted();
                System.out.println("Comandos executados");
            } else if (cmd.equals("insert")) {
                if (this.banco.verifica(chave)) {
                    retorno = "Chave ja existe";
                    sr = ServerResponse.newBuilder().setResponse("Chave ja existe").build();
                } else {
                    retorno = this.banco.add(chave, dados);
                                            String value = new String(dados, "UTF-8");

                    sr = ServerResponse.newBuilder().setResponse("Valor: " +value + "com chave: " + chave + " salvo com sucesso").build();
                }
                responseObserver.onNext(sr);
                responseObserver.onCompleted();
                System.out.println("Comandos executados");
            } else if (cmd.equals("delete")) {

                if (this.banco.verifica(chave)) {
                    retorno = this.banco.Deletar(chave);
                    sr = ServerResponse.newBuilder().setResponse("Dado com chave: " + chave + " deletado com sucesso").build();

                } else {
                    retorno = "N�o existe chave para ser deletada";
                    sr = ServerResponse.newBuilder().setResponse("Não existe chave para ser deletada").build();
                }
                responseObserver.onNext(sr);
                responseObserver.onCompleted();
                System.out.println("Comandos executados");
            } else if (cmd.equals("update")) {
                if (this.banco.verifica(chave)) {
                    retorno = this.banco.update(chave, dados);
                                                                String vlr = new String(dados, "UTF-8");

                    sr = ServerResponse.newBuilder().setResponse("Atualizando dado com o Valor: " + vlr + "e chave: " + chave + "").build();
                } else {
                    retorno = "N�o existe chave para ser atualizada";
                    sr = ServerResponse.newBuilder().setResponse("Não existe chave para ser atualizada").build();
                }
                responseObserver.onNext(sr);
                responseObserver.onCompleted();
                System.out.println("Comandos executados");
            }

            //Tratamento para tentar evitar memoria Leak:
            comando = null;
            comandos = null;
            dados = null;
            chave = null;
            System.gc();
            return retorno;
        } catch (Exception e) {
            System.out.println("Exceçao : " + e);
        }
        return retorno;
    }

    public void run() {
        while (true) {
            Comando c = F3.getFirst(); //Tratamento para tentar evitar memoria Leak:
            StreamObserver<ServerResponse> responseObserver = c.getObserver();
            String retorno = this.ProcessaComando(c, responseObserver);
            try {
                    this.servidor.transmitResponse(retorno);
                  
                } catch (Exception ex) {
                    Logger.getLogger(AplicarAoBanco.class.getName()).log(Level.SEVERE, null, ex);
                }
            } 

        }
    }

