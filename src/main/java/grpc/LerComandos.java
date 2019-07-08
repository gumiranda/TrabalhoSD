/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grpc;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;

public class LerComandos implements Runnable {

    private Cliente cliente;
    private ComunicaThread com;
    private boolean exit = false;

    public synchronized boolean verificaNumero(String str2) {
        double valor;

        try {
            valor = (Double.parseDouble(str2));
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public LerComandos(Cliente cliente, ComunicaThread com) {
        this.cliente = cliente;
        this.com = com;
    }

    public Cliente getCliente() {
        return this.cliente;
    }

    //Retorna false caso a thread tenha q parar ou false caso contrario
    public synchronized void validarComandos(String comando) {
        boolean flag = true;
        boolean fi = false;
        String cmd[] = comando.split(" ");
        cmd[0] = cmd[0].toUpperCase();
        if (cmd[0].equals("INSERT")) {

            if (cmd.length < 3) {
                System.out.println("Quantidade de cmd invalido: Minimo de  3");
                flag = false;

            } else {
                if (!this.verificaNumero(cmd[1])) {
                    System.out.println("Tipo de chave Invalida");
                    flag = false;
                } else {
                    this.cliente.comando = comando;
                }

            }
        } else if (cmd[0].equals("DELETE")) {

            if (cmd.length < 2) {
                System.out.println("Quantidade de cmd invalido: Minimo de  2");
                flag = false;

            } else {
                if (!this.verificaNumero(cmd[1])) {
                    System.out.println("Tipo de chave Invalida");
                    flag = false;
                } else {
                    this.cliente.comando = comando;
                }
            }
        } else if (cmd[0].equals("SELECT")) {
            if (cmd.length < 2) {
                System.out.println("Quantidade de cmd invalido: Minimo de  2");
                flag = false;

            } else {
                if (!this.verificaNumero(cmd[1])) {
                    System.out.println("Tipo de chave Invalida");
                    flag = false;
                } else {
                    this.cliente.comando = comando;
                }
            }

        } else if (cmd[0].equals("UPDATE")) {
            if (cmd.length < 3) {
                System.out.println("Quantidade de cmd invalido: Minimo de  3");
                flag = false;

            } else {
                if (!this.verificaNumero(cmd[1])) {
                    System.out.println("Tipo de chave Invalida");
                    flag = false;
                } else {
                    this.cliente.comando = comando;
                }
            }

        } else if (cmd[0].equals("SAIR")) {

            this.com.Matar();
        } else {
            System.out.println("Comando inválido");
            flag = false;
            System.out.println("Digite um dos cmd: INSERT|UPDATE|DELETE|SELECT seguidos de chave e valor(caso necessário)");
        }

        if (flag) {
            this.com.indicaFinal();
            System.out.println(comando);

        }

    }

    public void run() {

        while (!this.exit) {
            Scanner s = new Scanner(System.in);
            System.out.println("Digite um comando INSERT|DELETE|SELECT|UPDATE + chave (e valor caso seja INSERT OU UPDATE)");
            while (s.hasNextLine()) {
                String comando = s.nextLine();
                validarComandos(comando);

            }

            boolean verifica = false;

        }

    }

    public void stop() {
        this.exit = true;
    }

}
