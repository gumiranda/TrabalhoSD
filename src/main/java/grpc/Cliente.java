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

    public int primeiraPorta = 59043;
    public String host;
    private ComunicaThread com = new ComunicaThread();
    public int contador = 0;
    Set<String> names = new HashSet<String>();
    private static final String IP = "127.0.0.1";
    private static final Logger logger = Logger.getLogger(Cliente.class.getName());
    public String comando;
    public String idServidor;
    public CopycatClient client;

    public Cliente(String host, int port) {
        this.comando = null;
        this.host = host;
        this.primeiraPorta = port;
    }

    public Cliente(String host, int port, Comando comando) {

        String command;
        command = comando.getComando() + " " + comando.getChave();
        if (comando.getValor() != null) {
            command = command + " " + comando.getValor();
        }
        this.host = host;
        this.primeiraPorta = port;
        this.comando = command;
    }

    public Cliente() {

    }

    public static void main(String[] args) throws Exception {
        ArrayList<Address> enderecos = new ArrayList<>();
        boolean teste = false;
        if (args.length == 7) {
            for (int i = 0; i < 6; i += 2) {
                System.out.println(args[i] + " - " + args[i + 1]);
                Address endereco = new Address(args[i], Integer.parseInt(args[i + 1]));
                enderecos.add(endereco);
            }
            teste = true;
        } else {
            for (int i = 0; i < args.length; i += 2) {
                System.out.println(args[i] + " - " + args[i + 1]);
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
                client.submit(new CreateCommand(new BigInteger("123"), "Teste1")).thenRun(() -> System.out.println("Insert realizado"));;
                client.submit(new ReadQuery(new BigInteger("123"))).thenAccept(result -> System.out.println(result.toStringValue()));
                client.submit(new CreateCommand(new BigInteger("124"), "Teste2")).thenRun(() -> System.out.println("Insert realizado"));;
                client.submit(new UpdateCommand(new BigInteger("124"), "AltTeste3")).thenRun(() -> System.out.println("Update realizado"));;
                client.submit(new ReadQuery(new BigInteger("124"))).thenAccept(result -> System.out.println(result.toStringValue()));
                client.submit(new DeleteCommand(new BigInteger("124"))).thenRun(() -> System.out.println("Delete realizado"));
           
            }
        } catch (Exception e) {
            System.out.println(e);
            System.exit(0);
        }
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
