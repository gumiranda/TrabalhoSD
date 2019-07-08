package grpc;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Logger;
import org.json.JSONObject;
import org.json.simple.*;

public class FingerTable {

    private Servidor server; //Servidor que possui a FingerTable
    private int porta; //O servidor que ele ira comunicar para atualizar a tabela
    private String tabela[][];
    private BigInteger inicio_responsabilidade;
    private BigInteger fim_responsabilidade;

    public FingerTable(Servidor s, int porta_servidor) {
        this.server = s;
        this.porta = porta_servidor;
        int n = new Integer(this.server.getQuantidadeChaves().intValue());
        this.tabela = new String[n][2];
       // System.out.println(this.tabela.length);
        if (porta_servidor == -1) {//Nao tem nenhum servidor(Primeiro a estar sendo iniciado)
            this.primeiroServidor();
        }
    }

    public String[][] getTabela() {
        return this.tabela;
    }

    public String getTabelaString() {
        String tabela = new String();
        for (int i = 0; i < this.tabela.length; i++) {
            if (i != 0) {
                tabela = ";" + this.tabela[i][0] + " " + this.tabela[i][1];
            } else {
                tabela = this.tabela[i][0] + " " + this.tabela[i][1];
            }
        }
        return tabela;
    }

    public int BigToInt(BigInteger n) {
        return new Integer(n.intValue());
    }

    public void primeiroServidor() {

        this.inicio_responsabilidade = new BigInteger("1");
        int n = new Integer(this.server.getQuantidadeChaves().intValue());
        this.fim_responsabilidade = new BigInteger(Integer.toString(n));
      //  System.out.println(this.inicio_responsabilidade);
       // System.out.println(this.fim_responsabilidade);

    }

    public boolean verificaResponsabilidade(BigInteger chave) { //Verifia se servidor eh responsavel pela chave recebida
        int retorno1 = this.inicio_responsabilidade.compareTo(chave);
        int retorno2 = this.fim_responsabilidade.compareTo(chave);

        if (retorno1 == -1 || retorno1 == 0) {
            if (retorno2 == 1 || retorno2 == 0) {
                return true;
            }
        }
        return false;
    }

}
