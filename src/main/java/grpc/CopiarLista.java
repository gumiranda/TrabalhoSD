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
    private volatile ChordNode node;
    private BigInteger maxNodeId;
    private BigInteger minNodeId;
    private int numberOfNodes;
    private int numberBitsId;
    public CopiarLista(Fila F1, Fila F2, Fila F3, Fila F4, int porta) {
        this.F1 = F1;
        this.F2 = F2;
        this.F3 = F3;
        this.F4 = F4;
        this.porta = porta;
    }

    CopiarLista(Fila F1, Fila F2, Fila F3, Fila F4, int porta, ChordNode node) {
        this.F1 = F1;
        this.F2 = F2;
        this.F3 = F3;
        this.F4 = F4;
        this.porta = porta;
        this.node = node;
        this.numberOfNodes = numberOfNodes;
        this.numberBitsId = numberBitsId;

        this.maxNodeId = calcIdNode(node.getOffsetId(), BigInteger.valueOf(node.getId()), node.getMaxId(), BigInteger.valueOf(node.getNumeroDeNodes()));
        int value = node.getId() - 1;
        this.minNodeId = node.ehUltimo() ? BigInteger.valueOf(0) : calcIdNode(node.getOffsetId(), BigInteger.valueOf(value), node.getMaxId(), BigInteger.valueOf(node.getNumeroDeNodes()));

        System.out.println("maxNodeId:" + maxNodeId + ", minNodeId:" + minNodeId);
    }

    public void run() {
        while (true) {
            try {
                Comando c = F1.getFirst();
              if  (isMyResponsibility(this.node,c.getChave())){
             System.out.println("Enfileirando comandos nas filas F2 e F3 ");
                        F2.put(c);
                        F3.put(c);
            } else {
                          System.out.println("Enfileirando comandos nas filas F4 para outro servidor tratar");
                        F4.put(c);                  
                    }
            } catch (Exception e) {
                System.out.println(e);
            }

        }
    }
    private boolean isMyResponsibility(ChordNode node,BigInteger id) throws Exception {
        if (id.compareTo(node.getMaxId()) == 1 ) {
            throw new Exception("Invalid ID, this ID surpass MAX capacity.");
        }

        if (id.compareTo(BigInteger.valueOf(0)) == -1) {
            throw new Exception("Invalid ID, can be below Zero.");
        }

        return (this.minNodeId.compareTo(id) == -1 && (id.compareTo(this.maxNodeId) == -1 || id.compareTo(this.maxNodeId) == 0 ) || (id.compareTo(BigInteger.valueOf(0))) == 0 && node.ehUltimo());
    }

    private BigInteger calcIdNode(BigInteger offSet, BigInteger node, BigInteger maxId, BigInteger numberOfNodes) {
        BigInteger count = numberOfNodes;

        while (count.compareTo(node) == 1) {
            maxId = maxId.subtract(offSet);
            count = count.subtract(BigInteger.valueOf(1));
        }

        return maxId;
    }
}
