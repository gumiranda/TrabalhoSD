/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grpc;

import io.grpc.stub.StreamObserver;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Samsung
 */
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VerificaServidoresReponsaveis implements Runnable {

    private Fila F2;
    private Fila F3;
    private Fila F4;
    private File arquivo;
    private Servidor server;
    private Cliente nextNode;
    private ChordNode node;
    private Cliente previousNode;
    private int delayCommand;
    private BigInteger firstNode;
    private static final Logger logger = Logger.getLogger(VerificaServidoresReponsaveis.class.getName());
    private BigInteger offSetId;
    private ArrayList<BigInteger> mapIdNode ;
    public VerificaServidoresReponsaveis(Fila F2, Fila F3, Fila F4, Servidor server,ChordNode node) {
        this.F4 = F4;
        this.node = node;
        this.F3 = F3;
        this.F2 = F2;
        this.server = server;
        this.arquivo = new File("Tabela_de_roteamento.txt");
        this.firstNode = node.getMaxId();
        this.offSetId = node.getOffsetId();
       // this.mapIdNode = new ArrayList<BigInteger>(node.getNumeroDeNodes()+1);
       this.mapIdNode = new ArrayList<BigInteger>(node.getNumeroDeNodes()+1);
         int count = node.getNumeroDeNodes();
         for(int k = 0;k < node.getNumeroDeNodes();k++){
         this.mapIdNode.add(BigInteger.valueOf(k));
         }
         
        while (count > 0) {
            mapIdNode.add(count,firstNode);
            firstNode = firstNode.subtract(offSetId);
            count--;
        }

        count = node.getNumeroDeNodes();
        while (count >= 0) {
            System.out.print(mapIdNode.get(count));
            count--;

            if (count >= 0) {
                System.out.print(":");
            }
        }
        System.out.println();
    }

    public void run() {
        while (true) {
            Comando c = F4.getFirst();
            try {
/*              Cliente clientRedirect;
            String host1,host2;
            int porta,porta2;
              if(node.getIpProximo() == null){
              host1 = node.getIp(); 
              }else{
              host1 = node.getIpProximo();
              }
              if(node.getProximaPorta() == 0 ){
              porta = node.getPorta(); 
              }else{
              porta = node.getProximaPorta();
              }
            if (nextNode == null) {
                nextNode = new Cliente(host1,porta,c);
            }
  if(node.getIpAnterior() == null){
              host2 = node.getIp(); 
              }else{
              host2 = node.getIpAnterior();
              }
              if(node.getPortaAnterior() == 0 ){
              porta2 = node.getPorta(); 
              }else{
              porta2 = node.getPortaAnterior();
              }
            if (previousNode == null) {
                if (node.getIpAnterior() != null) {
                    previousNode = new Cliente(host2,porta2,c);
                }
            }
            */
            
int porta;
                if(this.server.porta == 59047){
                porta = 59043;
                }else{
                porta = this.server.porta+1;
                }
                this.server.stop();
                 Boolean clockwise = getPossibleRedirection(node, c.getChave());

            if (clockwise) {
              /*  if (nextNode == null) {
                nextNode = new Cliente(host1,porta,c);
            }
                clientRedirect = nextNode;
                */
                              porta = this.server.porta+1;
                                             this.node.setPorta(porta);
                                             this.node.setPortaAnterior(this.server.porta);
                                             this.node.setProximaPorta(porta+1);

            } else {
                                              porta = this.server.porta-1;
                                             this.node.setPorta(porta);
                                             this.node.setPortaAnterior(porta-1);
                                             this.node.setProximaPorta(this.server.porta);
                  /*         if (previousNode == null) {
                previousNode = new Cliente(host2,porta2,c);
            }        
                clientRedirect = previousNode;
            */}
            //clientRedirect.enviaComando(clientRedirect);
               
                Servidor servidor = new Servidor(porta);
                servidor.F1.put(c);
                servidor.start();
                servidor.blockUntilShutdown();
                logger.info("Comando " + c.getComando()+ " redirect to " + (clockwise ? "next" : "previous") + " node - " + c.getChave());

            }  catch (Exception ex) {
                Logger.getLogger(VerificaServidoresReponsaveis.class.getName()).log(Level.SEVERE, null, ex);
            }
            
    }
    }
     public boolean getPossibleRedirection(ChordNode node, BigInteger id) {
        return minDistanceOnRing(node.getId(), getDestNode(id), node.getNumeroDeNodes());
    }

    private int getDestNode(BigInteger id) {
        int curNode = node.getNumeroDeNodes();
        int prevNode = curNode - 1;

        while (curNode >= 0) {
            if ((mapIdNode.get(prevNode).compareTo(id) == -1)  && (id.compareTo(mapIdNode.get(curNode)) == -1 || id.compareTo(mapIdNode.get(curNode)) == 0) || mapIdNode.get(prevNode).compareTo(BigInteger.valueOf(0)) == 0) {
                return curNode;
            } else {
                curNode--;
                prevNode--;
            }
        }

        return -1;
    }

    public static boolean minDistanceOnRing(int startNode, int destineNode, int ringCirc) {
        int distance = startNode - destineNode;
        boolean direction = distance >= 0;

        distance = Math.abs(distance);

        if (distance <= (ringCirc - distance)) {
            return !direction;
        } else {
            return direction;
        }
    }   
}
