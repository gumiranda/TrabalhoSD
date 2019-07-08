package grpc;

import java.util.concurrent.TimeUnit;
import java.math.BigInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import grpc.ImprimeMensagem;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.CompletableFuture;
import java.util.LinkedList;
import java.util.List;

import grpc.command.*;
import io.atomix.catalyst.transport.Address;
import io.atomix.catalyst.transport.netty.NettyTransport;
import io.atomix.copycat.client.CopycatClient;
import io.atomix.copycat.server.StateMachine;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Cliente {

    private ComunicaThread com = new ComunicaThread();
    public String comando;
    public String idServidor;
    public CopycatClient client;

    public static void main(String[] args) throws Exception {
        ArrayList<Address> enderecos = new ArrayList<>();
        boolean teste = false;
        if (args.length == 7) {
            for (int i = 0; i < 6; i += 2) {
                Address endereco = new Address(args[i], Integer.parseInt(args[i + 1]));
                enderecos.add(endereco);
            }
            teste = true;
        } else {
            for (int i = 0; i < args.length; i += 2) {
                Address endereco = new Address(args[i], Integer.parseInt(args[i + 1]));
                enderecos.add(endereco);
            }
        }

        Cliente cliente = new Cliente();

        CopycatClient.Builder builder = CopycatClient.builder()
                .withTransport(NettyTransport.builder()
                        .withThreads(4)
                        .build());

        CopycatClient client = builder.build();
        cliente.client = client;
        CompletableFuture<CopycatClient> future = client.connect(enderecos);
        future.join();
        try {
            if (!teste) {
                cliente.executa(cliente);
            } else {
            cliente.testa();
            }
        } catch (Exception e) {
            System.out.println(e);
            System.exit(0);
        }
    }

    public void testa() {
        client.submit(new CreateCommand(new BigInteger("1236"), "TestInsert1")).thenRun(() -> System.out.println("Insert realizado"));
        client.submit(new ReadQuery(new BigInteger("1236"))).thenAccept(result -> System.out.println(result.toString()));
        client.submit(new CreateCommand(new BigInteger("1247"), "Teste2")).thenRun(() -> System.out.println("Insert realizado"));
        client.submit(new UpdateCommand(new BigInteger("1247"), "AltTeste3")).thenRun(() -> System.out.println("Update realizado"));
        client.submit(new DeleteCommand(new BigInteger("1247"))).thenRun(() -> System.out.println("Delete realizado"));
    }

    public void executa(Cliente cliente) throws IOException, InterruptedException {
        ImprimeMensagem imprimir = new ImprimeMensagem(cliente, this.com);
        Thread im = new Thread(imprimir);
        im.start();
        //Lendo mensagem do teclado e mandando para o servidor
        String comandoRecebido = this.comando;
        LerComandos comandos = new LerComandos(cliente, this.com);
        Thread c = new Thread(comandos);
        c.start();

        im.join();
        c.stop();

    }

}
