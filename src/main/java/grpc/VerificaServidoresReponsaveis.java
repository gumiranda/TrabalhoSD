/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grpc;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Samsung
 */
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VerificaServidoresReponsaveis implements Runnable {

    private Fila F2;
    private Fila F3;
    private Fila F4;
    private File arquivo;
    private Servidor server;

    public VerificaServidoresReponsaveis(Fila F2, Fila F3, Fila F4, Servidor server) {
        this.F4 = F4;
        this.F3 = F3;
        this.F2 = F2;
        this.server = server;
        this.arquivo = new File("Tabela_de_roteamento.txt");
    }

    public void run() {
        while (true) {
            Comando c = F4.getFirst();
            try {
                int porta;
                if(this.server.porta == 59047){
                porta = 59043;
                }else{
                porta = this.server.porta+1;
                }
                this.server.stop();
                Servidor servidor = new Servidor(porta);
                servidor.F1.put(c);
                servidor.start();
                servidor.blockUntilShutdown();
            } catch (IOException ex) {
                Logger.getLogger(VerificaServidoresReponsaveis.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(VerificaServidoresReponsaveis.class.getName()).log(Level.SEVERE, null, ex);
            }
            
    }
    }
}
