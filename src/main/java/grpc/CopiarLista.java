package grpc;

import java.util.ArrayList;
import java.math.BigInteger;
import java.util.List;

public class CopiarLista implements Runnable {

    private Fila F1;
    private Fila F2;
    private Fila F3;

    public CopiarLista(Fila F1, Fila F2, Fila F3) {
        this.F1 = F1;
        this.F2 = F2;
        this.F3 = F3;
    }

    public void run() {
        while (true) {
            try {
                Comando c = F1.getFirst();
                System.out.println("Enfileirando comandos nas filas F2 e F3 ");
                F2.put(c);
                F3.put(c);
            } catch (Exception e) {
                System.out.println(e);
            }

        }
    }

}
