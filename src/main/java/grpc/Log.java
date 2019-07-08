package grpc;

import java.io.IOException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Log implements Runnable {

    private static BlockingQueue<Comando> F2;
    private File arquivo;
    ComunicaThread com;
    SnapShot snap;
    private String id;
    private int contador = 0;
    private ArrayList<Integer> c = new ArrayList();

    public Log(BlockingQueue<Comando> F2, ComunicaThread com, SnapShot s, String id) {
        this.com = com;
        this.F2 = F2;
        this.snap = s;
        this.id = id;

    }

    public void criarDiretorio() {
        try {
            File diretorio = new File("LOG_" + this.id);
            diretorio.mkdir();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao criar o diretorio");
            System.out.println(ex);
        }
    }

    public int getMenor() {
        int menor = c.get(0);
        for (int i = 1; i < this.c.size(); i++) {
            if (c.get(i) < menor) {
                menor = c.get(i);
            }
        }
        return menor;
    }

    public int getPos(int n) {
        for (int i = 0; i < this.c.size(); i++) {
            if (c.get(i) == n) {
                return i;
            }
        }
        return -1;
    }

    public void run() {
        while (true) {
            com.tentaExecutar();
            Comando c;
            try {
                c = F2.take();

                String comando = c.getComando();
                String comandos[] = comando.split(" ");
                int contador_snap = this.snap.getContador();

                File diretorio = new File("LOG_" + this.id);
                if (!diretorio.exists()) {
                    this.criarDiretorio();
                }

                // System.out.println(contador_snap);
                if (this.contador <= contador_snap) {

                    this.contador = contador_snap + 1;

                    if (this.c.size() == 3) {
                        int menor = this.getMenor();
                        // System.out.println(menor);
                        File arq_remover = new File("LOG_" + this.id + "//" + "Log" + menor + ".txt");
                        if (arq_remover.exists()) {
                            arq_remover.delete();
                        }

                        this.c.remove(getPos(menor));
                    }
                    this.c.add(this.contador);

                }
                this.arquivo = new File("LOG_" + this.id + "//" + "Log" + this.contador + ".txt");

                if (!this.arquivo.exists()) {
                    try {
                        this.arquivo.createNewFile();
                    } catch (IOException ex) {
                        Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                FileWriter fw;
                try {
                    FileWriter arq = new FileWriter(this.arquivo, true);
                    PrintWriter gravarArq = new PrintWriter(arq);

                    if (!comandos[0].toLowerCase().equals("select") || !comandos[0].equals("quit")) {
                        if (comandos[0].toLowerCase().equals("delete")) {
                            gravarArq.println(comando + " " + c.getChave());
                        } else if (comandos[0].toLowerCase().equals("update")
                                || comandos[0].toLowerCase().equals("insert")) {
                            gravarArq.println(comando + " " + c.getChave() + " " + c.getValor());
                        }
                    }
                    gravarArq.flush();
                    arq.close();

                } catch (IOException ex) {
                    Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, ex);
                }

                c = null;
                comando = null;
                comandos = null;
                System.gc();
                com.FinalLeitura();
            } catch (InterruptedException ex) {
                Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
