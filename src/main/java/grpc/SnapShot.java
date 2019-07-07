/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
 /*
import gRPC.proto.ChaveRequest;
import gRPC.proto.ServerResponse;
import gRPC.proto.ServicoGrpc;
import gRPC.proto.ValorRequest;
import io.grpc.StatusRuntimeException;
import java.util.Scanner;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.DataOutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import org.json.simple.*;
 */
package grpc;

import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.json.JSONObject;

public class SnapShot implements Runnable {

    private BaseDados banco;
    ComunicaThread com;
    private JSONObject jsonDados;
    private File arquivo;
    private int ind_arq = 0;
    private String id;

    public SnapShot(BaseDados banco, ComunicaThread com, String id) {
        this.banco = banco;
        this.com = com;
        this.jsonDados = new JSONObject();
        this.id = id;
    }

    public int getContador() {
        return this.ind_arq;
    }

    public void criarDiretorio() {
        try {
            File diretorio = new File("SNAP_" + this.id);
            diretorio.mkdir();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao criar o diretorio");
            System.out.println(ex);
        }
    }

    public void run() {
        //int ind_arq = 0;
        while (true) {
            File diretorio = new File("SNAP_" + this.id);
            if (!diretorio.exists()) {
                this.criarDiretorio();
            }
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                Logger.getLogger(SnapShot.class.getName()).log(Level.SEVERE, null, ex);
            }
            //Remove os arquivos passados para sobrescrever
            File arq_remover;
            this.arquivo = new File("SNAP_" + this.id + "//" + "SnapShot" + ind_arq + ".json");
            int ind_arq_remove = ind_arq - 3;

            if (ind_arq % 3 == 0 && ind_arq >= 3) {
                arq_remover = new File("SNAP_" + this.id + "//" + "SnapShot" + ind_arq_remove + ".json");
                if (arq_remover.exists()) {
                    arq_remover.delete();
                }
            } else if (ind_arq % 3 == 1 && ind_arq >= 3) {
                arq_remover = new File("SNAP_" + this.id + "//" + "SnapShot" + ind_arq_remove + ".json");
                if (arq_remover.exists()) {
                    arq_remover.delete();
                }
            } else if (ind_arq % 3 == 2 && ind_arq >= 3) {
                arq_remover = new File("SNAP_" + this.id + "//" + "SnapShot" + ind_arq_remove + ".json");
                if (arq_remover.exists()) {
                    arq_remover.delete();
                }
            }
            //-------
            if (!this.arquivo.exists()) {
                try {
                    this.arquivo.createNewFile();
                } catch (IOException ex) {
                    Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            //Escreve no JSON
            for (BigInteger key : this.banco.getKeys()) {
                byte[] value = this.banco.get(key);
                try {
                    String texto = new String(value, "UTF-8");
                    jsonDados.put(key.toString(), texto);
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(SnapShot.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            //-------
            FileWriter arqDados = null;
            try {
                arqDados = new FileWriter(this.arquivo, false);
            } catch (IOException ex) {
                Logger.getLogger(SnapShot.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                arqDados.write(jsonDados.toString());//toJSONString() talvez
                arqDados.flush();
                arqDados.close();
            } catch (IOException ex) {
                Logger.getLogger(SnapShot.class.getName()).log(Level.SEVERE, null, ex);
            }

            this.com.indicaFinal();
            ind_arq++;
        }
    }
}
