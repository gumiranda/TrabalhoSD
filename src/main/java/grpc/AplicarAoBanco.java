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
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class AplicarAoBanco {

    private BaseDados banco;
    private static BlockingQueue< Comando> F3;
    private Servidor servidor;

    public AplicarAoBanco(BaseDados banco, BlockingQueue<Comando> F3, Servidor s) {
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

    public String ProcessaComando(Comando comando) {

        String command;
        command = comando.getComando() + " " + comando.getChave();
        if (comando.getValor() != null) {
            command = command + " " + comando.getValor();
        }
        String comandos[] = command.split(" ");
        byte[] dados = null;
        String retorno = null;

        BigInteger chave = comando.getChave();
        try {
            if (comandos.length >= 3) {
                dados = this.banco.getDados(comandos);
            }

            byte[] retorno_select = null;
            String cmd = comandos[0].toLowerCase();

            if (cmd.equals("select")) {
                if (this.banco.verifica(chave)) {
                    retorno_select = this.banco.get(chave);
                    try {
                        retorno = new String(retorno_select, "ISO-8859-1");
                        System.out.println("RETORNO: " + retorno);
                    } catch (UnsupportedEncodingException ex) {
                        Logger.getLogger(AplicarAoBanco.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    retorno = "Chave nao existe";
                }
            } else if (cmd.equals("insert")) {
                if (this.banco.verifica(chave)) {
                    retorno = "Chave ja existe";
                } else {
                    retorno = this.banco.add(chave, dados);
                    String value = new String(dados, "UTF-8");
                }
            } else if (cmd.equals("delete")) {

                if (this.banco.verifica(chave)) {
                    retorno = this.banco.Deletar(chave);

                } else {
                    retorno = "Nao existe chave para ser deletada";
                }
            } else if (cmd.equals("update")) {
                if (this.banco.verifica(chave)) {
                    retorno = this.banco.update(chave, dados);
                    String vlr = new String(dados, "UTF-8");
                } else {
                    retorno = "Nao existe chave para ser atualizada";
                }
            }

            //Tratamento para tentar evitar memoria Leak:
            comando = null;
            comandos = null;
            dados = null;
            chave = null;
            System.gc();
            return retorno;
        } catch (Exception e) {
            System.out.println("Excecao : " + e);
        }
        return retorno;
    }

}
