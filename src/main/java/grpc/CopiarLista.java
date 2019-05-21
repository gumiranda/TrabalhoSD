package grpc;

import java.util.ArrayList;
import java.math.BigInteger;
import java.util.List;

public class CopiarLista implements Runnable {

    private Fila F1;
    private Fila F2;
    private Fila F3;
    private Fila F4;
    private int porta;

    public CopiarLista(Fila F1, Fila F2, Fila F3, Fila F4, int porta) {
        this.F1 = F1;
        this.F2 = F2;
        this.F3 = F3;
        this.F4 = F4;
        this.porta = porta;
    }

    public void run() {
        while (true) {
            try {
                Comando c = F1.getFirst();
                if (this.porta == 8080) {
                    if ((c.getChave().compareTo(new BigInteger("1")) == 1 || c.getChave().compareTo(new BigInteger("1")) == 1) && (c.getChave().compareTo(new BigInteger("300000")) == -1)) {
                        System.out.println("Enfileirando comandos nas filas F2 e F3 usando a porta 8080");
                        F2.put(c);
                        F3.put(c);
                    } else {
                        System.out.println("Enfileirando comandos nas filas F4 para outro servidor tratar");
                        F4.put(c);
                    }
                } else if (this.porta == 8081) {
                    if ((c.getChave().compareTo(new BigInteger("300000")) == 1 || c.getChave().compareTo(new BigInteger("300000")) == 1) && (c.getChave().compareTo(new BigInteger("600000")) == -1)) {
                        System.out.println("Enfileirando comandos nas filas F2 e F3 usando a porta 8081");
                        F2.put(c);
                        F3.put(c);
                    } else {
                        System.out.println("Enfileirando comandos nas filas F4 para outro servidor tratar");
                        F4.put(c);
                    }
                } else if (this.porta == 8082) {
                    if ((c.getChave().compareTo(new BigInteger("600000")) == 1 || c.getChave().compareTo(new BigInteger("600000")) == 1) && (c.getChave().compareTo(new BigInteger("900000")) == -1)) {
                        System.out.println("Enfileirando comandos nas filas F2 e F3 usando a porta 8082");
                        F2.put(c);
                        F3.put(c);
                    } else {
                        System.out.println("Enfileirando comandos nas filas F4 para outro servidor tratar");
                        F4.put(c);
                    }
                } else if (this.porta == 8083) {
                    if ((c.getChave().compareTo(new BigInteger("900000")) == 1 || c.getChave().compareTo(new BigInteger("900000")) == 1) && (c.getChave().compareTo(new BigInteger("1200000")) == -1)) {
                        System.out.println("Enfileirando comandos nas filas F2 e F3 usando a porta 8083");
                        F2.put(c);
                        F3.put(c);
                    } else {
                        System.out.println("Enfileirando comandos nas filas F4 para outro servidor tratar");
                        F4.put(c);
                    }
                } else if (this.porta == 8084) {
                    if ((c.getChave().compareTo(new BigInteger("1200000")) == 1 || c.getChave().compareTo(new BigInteger("1200000")) == 1)) {
                        System.out.println("Enfileirando comandos nas filas F2 e F3 usando a porta 8084");
                        F2.put(c);
                        F3.put(c);
                    } else {
                        System.out.println("Enfileirando comandos nas filas F4 para outro servidor tratar");
                        F4.put(c);
                    }
                } else {
                    System.out.println("Porta inv�lida");
                }

            } catch (Exception e) {
                System.out.println(e);
            }

        }
    }

}
