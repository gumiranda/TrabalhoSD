/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grpc;

import grpc.command.CreateCommand;
import grpc.command.DeleteCommand;
import grpc.command.ReadQuery;
import grpc.command.UpdateCommand;
import java.util.Scanner;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ImprimeMensagem implements Runnable {

    private Cliente cliente;
    ComunicaThread com;
    private boolean exit = false;
    private static final Logger logger = Logger.getLogger(Cliente.class.getName());
    private String c;

    public ImprimeMensagem(Cliente cliente, ComunicaThread com) {
        this.cliente = cliente;
        this.com = com;
    }

    public ImprimeMensagem(Cliente cliente, String c, ComunicaThread com) {
        this.cliente = cliente;
        this.c = c;
        this.com = com;
    }

    public void insertOrUpdate(String chave, String valor, String comando) {
        logger.info(comando + " no dado com chave: " + chave + " e valor: " + valor);
        Comando cmd = new Comando(comando, valor, new BigInteger(chave));

        try {
            if (comando.equals("INSERT")) {
                this.cliente.client.submit(new CreateCommand(new BigInteger(chave), valor)).thenAccept(result -> System.out.println("Resultado do Insert no dado com chave "+chave+": "+result));
            } else if (comando.equals("UPDATE")) {
                this.cliente.client.submit(new UpdateCommand(new BigInteger(chave), valor)).thenAccept(result -> System.out.println("Resultado do Update no dado com chave "+chave+": "+result));
            } else {
                logger.log(Level.WARNING, "Comando inválido a ser enviado pro servidor: {0}");

                return;
            }
        } catch (Exception e) {
            return;
        }
    }

    public void selectOrDelete(String chave, String comando) {
        logger.info(comando + " no dado com chave: " + chave);
        Comando cmd = new Comando(comando, new BigInteger(chave));
        try {
            if (comando.equals("SELECT")) {
                this.cliente.client.submit(new ReadQuery(new BigInteger(chave))).thenAccept(result -> System.out.println(result.toString()));

            } else if (comando.equals("DELETE")) {
                this.cliente.client.submit(new DeleteCommand(new BigInteger(chave))).thenAccept(result -> System.out.println("Resultado do Delete no dado com chave "+chave+": "+result));
            } else {
                logger.log(Level.WARNING, "Comando inválido a ser enviado pro servidor: {0}");
                return;
            }
        } catch (Exception e) {
            return;
        }
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
            String valor = "";
            for (int i = 2; i < cmd.length; i++) {
                valor = valor + " " + cmd[i];
            }
            this.insertOrUpdate(cmd[1], valor, "INSERT");
        } else if (cmd[0].equals("DELETE")) {
            this.selectOrDelete(cmd[1], "DELETE");
        } else if (cmd[0].equals("SELECT")) {
            this.selectOrDelete(cmd[1], "SELECT");
        } else if (cmd[0].equals("UPDATE")) {
            String valor = "";
            for (int i = 2; i < cmd.length; i++) {
                valor = valor + " " + cmd[i];
            }
            this.insertOrUpdate(cmd[1], cmd[2], "UPDATE");
        } else {
            System.out.println("Comando inválido , portanto não será enviado para o servidor");
        }
    }

}
