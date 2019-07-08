package grpc;

import java.util.ArrayList;
import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class CopiarLista implements Runnable {

    private static BlockingQueue<Comando> F1;
    private static BlockingQueue<Comando> F2;
    private static BlockingQueue<Comando> F3;

    public CopiarLista(BlockingQueue<Comando> F1, BlockingQueue<Comando> F2, BlockingQueue<Comando> F3) {
        this.F1 = F1;
        this.F2 = F2;
        this.F3 = F3;
    }

    public void run() {
        while (true) {
            try {
                Comando c = F1.take();
                System.out.println("Enfileirando comandos nas filas F2 e F3 ");
                F2.put(c);
                F3.put(c);
            } catch (Exception e) {
                System.out.println(e);
            }

        }
    }

}
