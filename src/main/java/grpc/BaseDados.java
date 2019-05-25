package grpc;


import io.grpc.stub.StreamObserver;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.math.BigInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BaseDados{
    private Map<BigInteger,byte[]> Banco;
    private AplicarAoBanco processa = new AplicarAoBanco(this,null,null);  
    public BaseDados(){
        this.Banco = new HashMap<BigInteger,byte[]>();
    }
    
    public void RecuperardoLog(String log) throws FileNotFoundException, IOException{
        File arquivo = new File(log);
        if(arquivo.exists()){
            FileReader arq = new FileReader(log);
            BufferedReader lerArq = new BufferedReader(arq);

            String linha = "";
            byte[] dados = null;
            while (( linha = lerArq.readLine() ) != null) {                        
                this.processa.ProcessaComando(linha);
            }
        }    
    }
    
    
    public synchronized String update(BigInteger chave,byte[] dado){
        if(!verifica(chave)){
            return "Chave nao existe";
        }
        try {
            this.Banco.put(chave, dado);
            return "UPDATE Com Sucesso";
        }catch(Exception e){
            return "UPDATE Falhou";
        }
        
    }
    
    public BigInteger getChave(String chave){   
        BigInteger chavei = new BigInteger(chave);
        return chavei;
    }
    
    public byte[] getDados(String comandos[]){
        String d= null;
 
                for(int i=2;i<comandos.length;i++){
                    if(i == 2){
                        d = comandos[i];
                    }else{
                    d = d +" " +comandos[i];
                    }
                
                }
                
        return d.getBytes();
    }
    
    public synchronized String add(BigInteger chave,byte[] dado){
        if(verifica(chave)){
            return "Chave ja existe";
        }
            
        try {
            this.Banco.put(chave, dado);
            return "INSERT realizado com Sucesso";
        }catch(Exception e){
            return "INSERT FALHOU";
        }
        
    }
    
    public synchronized String Deletar(BigInteger chave){
        if(!verifica(chave)){
            return "Chave nao existe";
        }
            
        try {
            this.Banco.remove(chave);
            return "Deletado com Sucesso";
        }catch(Exception e){
            return "Delete FALHOU";
        }
        
    }
    
    public synchronized boolean verifica(BigInteger chave){
        return this.Banco.containsKey(chave);
    }
    
    public synchronized byte[] get(BigInteger chave){
        byte[] dados = this.Banco.get(chave);
        return dados;
        
    }
    
    public synchronized void imprimir() throws UnsupportedEncodingException{
        for (BigInteger key : this.Banco.keySet()) {
                     
             //Capturamos o valor a partir da chave
              byte[] value = this.Banco.get(key);
              
              String texto = new String(value, "UTF-8");

               System.out.println(key + " = " + texto);
        }
    }
    
}
