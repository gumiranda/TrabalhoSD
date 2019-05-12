
package sistemas_distribuidos.server;

import java.util.ArrayList;
import sistemas_distribuidos.controle.Comando;
import java.util.List;

public class CopiarLista implements Runnable{
    private Fila F1;
    private Fila F2;
    private Fila F3;
    
    public CopiarLista(Fila F1,Fila F2,Fila F3){
        this.F1 = F1;
        this.F2 = F2;
        this.F3 = F3;
    }
        
    public void run(){
        while(true){
            ArrayList<Comando> aux; //Copiar para F2
            ArrayList<Comando> aux1 = new ArrayList<Comando>(); //Copiar para F3
            aux = F1.getAll();
            F1.copiaArrayComandos(aux, aux1);
            F2.putArray(aux);
            F3.putArray(aux1);
        }
    }
    
}
