package grpc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.json.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.JSONObject;
import java.util.Iterator;
import java.util.Set;

public class BaseDados {

    private Map<BigInteger, byte[]> Banco;
    private AplicarAoBanco processa = new AplicarAoBanco(this, null, null);

    public BaseDados(Map<BigInteger, byte[]> dm) {
        this.Banco = dm;
    }

    public BaseDados() {
        this.Banco = new HashMap<BigInteger, byte[]>();
    }

    public int getMaior(ArrayList<Integer> c) {
        int maior = c.get(0);
        for (int i = 1; i < c.size(); i++) {
            if (c.get(i) > maior) {
                maior = c.get(i);
            }
        }
        return maior;
    }

    public void RecuperarBanco(String id) throws FileNotFoundException, IOException {
        File snap = new File("SNAP_" + id);
        File log = new File("LOG_" + id);
        if (snap.exists()) {
            ArrayList<Integer> contadores = new ArrayList();
            File files[] = snap.listFiles();
            for (int i = 0; i < files.length; i++) {
                String nome = files[i].getName();
                nome = nome.replace("SnapShot", "");
                nome = nome.replace(".json", "");
                contadores.add(Integer.parseInt(nome));
            }
            int maior = getMaior(contadores);
            JSONArray ja = null;
            String nm_arquivo = "SnapShot" + Integer.toString(maior) + ".json";
            java.io.FileReader fr = new java.io.FileReader("SNAP_" + id + "//" + nm_arquivo);
            java.io.BufferedReader br = new BufferedReader(fr);
            StringBuilder sb = new StringBuilder();
            String line;
            line = br.readLine();
            if (line != null) {
                JSONObject my_obj = new JSONObject(line);
                Set<String> chaves = my_obj.keySet();
                Iterator<String> c = chaves.iterator();
                while (c.hasNext()) {
                    String chave = c.next();
                    String valor = my_obj.getString(chave);
                    byte[] s = valor.getBytes();
                    BigInteger n = new BigInteger(chave);
                    this.add(n, s);

                }
                this.imprimir();

                br.close();
                fr.close();
                for (int i = 0; i < files.length; i++) {
                    File arquivo = new File("SNAP_" + id + "//" + files[i].getName());
                    arquivo.delete();
                }

                if (log.exists()) {
                    ArrayList<Integer> indices = new ArrayList();
                    File arquivos[] = log.listFiles();
                    for (int i = 0; i < arquivos.length; i++) {
                        String nome = arquivos[i].getName();
                        nome = nome.replace("Log", "");
                        nome = nome.replace(".txt", "");
                        indices.add(Integer.parseInt(nome));
                    }
                    int maior_indice = getMaior(contadores);
                    if (maior_indice > maior) {
                        String nm = "Log_" + id + "//" + "Log" + maior_indice + ".txt";
                        FileReader arq = new FileReader(nm);

                        BufferedReader lerArq = new BufferedReader(arq);

                        String linha = "";
                        byte[] dados = null;
                        while ((linha = lerArq.readLine()) != null) {
                            this.processa.ProcessaComando(linha);
                        }
                    }

                    for (int i = 0; i < arquivos.length; i++) {
                        File arquivo = new File("LOG_" + id + "//" + arquivos[i].getName());
                        arquivo.delete();
                    }

                }
            }

        }
    }

    public synchronized String update(BigInteger chave, byte[] dado) {
        if (!verifica(chave)) {
            return "Chave nao existe";
        }
        try {
            this.Banco.put(chave, dado);
            return "UPDATE Com Sucesso";
        } catch (Exception e) {
            return "UPDATE Falhou";
        }

    }

    public BigInteger getChave(String chave) {
        BigInteger chavei = new BigInteger(chave);
        return chavei;
    }

    public byte[] getDados(String comandos[]) {
        String d = null;

        for (int i = 2; i < comandos.length; i++) {
            if (i == 2) {
                d = comandos[i];
            } else {
                d = d + " " + comandos[i];
            }

        }

        return d.getBytes();
    }

    public String read(BigInteger chave) throws UnsupportedEncodingException {
        String dado = new String(this.Banco.get(chave), "UTF-8");
          return dado;
    }

    public Map<BigInteger, byte[]> getMap() {
        return this.Banco;
    }

    public ArrayList<BigInteger> getKeys() {
        ArrayList<BigInteger> l = new ArrayList<BigInteger>();
        for (BigInteger key : this.Banco.keySet()) {
            l.add(key);
        }
        return l;
    }

    public synchronized String add(BigInteger chave, byte[] dado) {
        if (verifica(chave)) {
            return "Chave ja existe";
        }

        try {
            this.Banco.put(chave, dado);
            return "INSERT realizado com Sucesso";
        } catch (Exception e) {
            return "INSERT FALHOU";
        }

    }

    public synchronized String Deletar(BigInteger chave) {
        if (!verifica(chave)) {
            return "Chave nao existe";
        }

        try {
            this.Banco.remove(chave);
            return chave.toString();
        } catch (Exception e) {
            return "Delete FALHOU";
        }
    }

    public synchronized boolean verifica(BigInteger chave) {
        return this.Banco.containsKey(chave);
    }

    public synchronized byte[] get(BigInteger chave) {
        byte[] dados = this.Banco.get(chave);
        return dados;
    }

    public synchronized void imprimir() throws UnsupportedEncodingException {
        for (BigInteger key : this.Banco.keySet()) {

            //Capturamos o valor a partir da chave
            byte[] value = this.Banco.get(key);

            String texto = new String(value, "UTF-8");

            System.out.println(key + " = " + texto);
        }
    }

}
