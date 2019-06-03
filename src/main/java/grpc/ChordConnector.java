/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grpc;
import com.google.protobuf.Empty;
import gRPC.proto.ChordServiceGrpc;
import gRPC.proto.DataNode;
import io.grpc.*;
import java.math.BigInteger;

public class ChordConnector {

    private String ip;
    private int port;
    private int saltoProximaPorta;
    private long firstNode;
    private long offSetId;
    private int numeroDeNos;

    public ChordConnector(String ip, int port, int saltoProximaPorta, int numeroDeNos, int numeroBitsId) {
        this.ip = ip;
        this.port = port;
        this.saltoProximaPorta = saltoProximaPorta;
        this.firstNode = (long) Math.pow(2, numeroBitsId) - 1;
        this.offSetId = (long) Math.pow(2, numeroBitsId) / numeroDeNos;
        this.numeroDeNos = numeroDeNos;
    }

    public ChordNode connect() throws Exception {
        ChordNode node = new ChordNode();
        node.setId(numeroDeNos);
        BigInteger offId = BigInteger.valueOf(offSetId);
        node.setOffsetId(offId);
        node.setIp(ip);
        node.setPorta(port);
        node.setMaxId(BigInteger.valueOf(firstNode));
        node.setNumeroDeNodes(numeroDeNos);
        node.setEhPrimeiro(true);
        return tryConnectOnRing(node);
    }

    public ChordNode tryConnectOnRing(ChordNode candidateNode) throws Exception {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(candidateNode.getIp(), candidateNode.getPorta())
                .usePlaintext().build();

        ChordServiceGrpc.ChordServiceBlockingStub stub = ChordServiceGrpc.newBlockingStub(channel);
        DataNode channelNodeFromNext = null;

        try {
            channelNodeFromNext = stub.escutando(Empty.newBuilder().build());
        } catch (StatusRuntimeException e) {
            // TODO diferenciar de nenhum serviço rodando na porta, de uma porta já ocupada
            if (e.getStatus().getCode().equals(Status.Code.UNAVAILABLE)) {
                System.out.println("não tem ngm");
            }

            if (e.getStatus().getCode().equals(Status.Code.ALREADY_EXISTS) || e.getStatus().getCode().equals(Status.Code.UNIMPLEMENTED)) {
                System.out.println("ocupada outro serviço ou outro servidor grpc");
            }
        }

        if (!channel.isShutdown()) {
            channel.shutdownNow();
        }

        if (channelNodeFromNext == null) {
            if (!candidateNode.ehPrimeiro()) {
                ManagedChannel channelNext = ManagedChannelBuilder.forAddress(candidateNode.getIpProximo(), candidateNode.getProximaPorta())
                        .usePlaintext().build();

                ChordServiceGrpc.ChordServiceBlockingStub stubNext = ChordServiceGrpc.newBlockingStub(channelNext);

                DataNode previous = DataNode.newBuilder()
                        .setIp(candidateNode.getIp())
                        .setPort(candidateNode.getPorta())
                        .build();

                stubNext.setAnterior(previous);

                if (!channelNext.isShutdown()) {
                    channelNext.shutdownNow();
                }
            }

            if (candidateNode.ehUltimo()) {
                ManagedChannel channelNext = ManagedChannelBuilder.forAddress(candidateNode.getIpProximo(), candidateNode.getProximaPorta())
                        .usePlaintext().build();

                ChordServiceGrpc.ChordServiceBlockingStub stubNext = ChordServiceGrpc.newBlockingStub(channelNext);

                DataNode lastNode = DataNode.newBuilder()
                        .setIp(candidateNode.getIp())
                        .setPort(candidateNode.getPorta())
                        .build();

                DataNode first = stubNext.setPrimeiroUltimo(lastNode);

                candidateNode.setAnterior(first);

                if (!channelNext.isShutdown()) {
                    channelNext.shutdownNow();
                }
            }

            System.out.println("EU SOU ----\n" + candidateNode.toString());

            return candidateNode;
        } else {
            boolean lastNode = candidateNode.getId() - 1 == 1l;

            ChordNode newCandidateNode = new ChordNode();
            newCandidateNode.setId(candidateNode.getId() - 1);
            newCandidateNode.setIp(ip);
            newCandidateNode.setPorta(candidateNode.getPorta() + saltoProximaPorta);// TODO diferenciar de nenhum serviço rodando na porta, de uma porta já ocupada, se porta estiver ocupada só incrementar a porta
            newCandidateNode.setMaxId(candidateNode.getMaxId());
            newCandidateNode.setNumeroDeNodes(numeroDeNos);
            newCandidateNode.setOffsetId(BigInteger.valueOf(offSetId));
            newCandidateNode.setMaxId(BigInteger.valueOf(firstNode));
            newCandidateNode.setProximo(channelNodeFromNext);
            newCandidateNode.setEhPrimeiro(false);
            newCandidateNode.setEhUltimo(lastNode);

            if (newCandidateNode.getId() > 0 && !channelNodeFromNext.getUltimoNode()) {
                return tryConnectOnRing(newCandidateNode);
            } else {
                throw new Exception("Chord Ring is full!!");
            }
        }
    }
}