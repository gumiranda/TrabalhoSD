package grpc;

import java.util.concurrent.TimeUnit;
import java.math.BigInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import grpc.ImprimeMensagem;
import java.io.IOException;
import java.net.Socket;
import io.atomix.cluster.MemberId;
import io.atomix.cluster.Node;
import io.atomix.cluster.discovery.BootstrapDiscoveryProvider;
import io.atomix.core.Atomix;
import io.atomix.core.AtomixBuilder;
import io.atomix.utils.net.Address;
import io.atomix.utils.serializer.Namespace;
import io.atomix.utils.serializer.Namespaces;
import io.atomix.utils.serializer.Serializer;
import java.util.ArrayList;

public class Cliente {

    public int porta;
    public String host;
    private ComunicaThread com = new ComunicaThread();
    public Atomix a;
    private static final String IP = "127.0.0.1";
    private static final Logger logger = Logger.getLogger(Cliente.class.getName());
    public String comando;
    public String idServidor;
    public Serializer s;
    public Cliente(String host, int port) {
        this.comando = null;
        this.host = host;
        this.porta = port;
    }

    public Cliente(String host, int port, Comando comando) {

        String command;
        command = comando.getComando() + " " + comando.getChave();
        if (comando.getValor() != null) {
            command = command + " " + comando.getValor();
        }
        this.host = host;
        this.porta = port;
        this.comando = command;
    }

    public static void main(String[] args) throws Exception {
        Cliente cliente = new Cliente("127.0.0.1", 59043);
        int IdCliente = Integer.parseInt(args[0]);
        ArrayList<Address> enderecos = new ArrayList<>();
        Serializer s = Serializer.using(Namespace.builder().register(Namespaces.BASIC).register(BigInteger.class).register(MemberId.class).register(Comando.class).build());
        cliente.s = s;
        for (int i = 2; i < args.length; i++) {
            Address endereco = new Address(args[i], Integer.parseInt(args[i + 1]));
            enderecos.add(endereco);
            i++;
        }
        cliente.idServidor = args[1];
        AtomixBuilder builder = Atomix.builder();
        Address endereco = new Address(IP, (6000 + IdCliente));
        Atomix a = builder.withMemberId("IdCliente-" + IdCliente)
                .withAddress(endereco).withMembershipProvider(BootstrapDiscoveryProvider.builder()
                .withNodes(Node.builder()
                        .withId("member-0")
                        .withAddress(enderecos.get(0))
                        .build(),
                        Node.builder()
                                .withId("member-1")
                                .withAddress(enderecos.get(1))
                                .build(),
                        Node.builder()
                                .withId("member-2")
                                .withAddress(enderecos.get(2))
                                .build())
                .build())
                .build();
        cliente.a = a;
        a.start().join();
        System.out.println("Cluster formado");

        try {
            cliente.executa(cliente);
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

    public void enviaComando(Cliente cliente) throws IOException, InterruptedException {
        ImprimeMensagem imprimir = new ImprimeMensagem(cliente, this.com);
        imprimir.enviaComando(this.comando);
        this.comando = null;
    }

}
