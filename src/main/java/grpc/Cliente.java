package grpc;

import com.google.common.collect.Lists;
import java.util.concurrent.TimeUnit;
import java.math.BigInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import grpc.ImprimeMensagem;
import io.atomix.cluster.Member;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Cliente {

    public int primeiraPorta = 59043;
    public String host;
    private ComunicaThread com = new ComunicaThread();
    public Atomix a;
    public int contador = 0;
    Set<String> names = new HashSet<String>();
    private static final String IP = "127.0.0.1";
    private static final Logger logger = Logger.getLogger(Cliente.class.getName());
    public String comando;
    public String idServidor;
    public Serializer s;

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

    public static void main(String[] args) throws Exception {
        int IdCliente = Integer.parseInt(args[0]);
        ArrayList<Address> enderecos = new ArrayList<>();
        Serializer s = Serializer.using(Namespace.builder().register(Namespaces.BASIC).register(BigInteger.class).register(MemberId.class).register(Comando.class).build());
        for (int i = 2; i < args.length; i++) {
            Address endereco = new Address(args[i], Integer.parseInt(args[i + 1]));
            enderecos.add(endereco);
            i++;
        }
        Cliente cliente = new Cliente("127.0.0.1", enderecos.get(0).port());
        cliente.s = s;
        List<Member> members = cliente.returnMembers(enderecos.size());
        cliente.idServidor = args[1];
        AtomixBuilder builder = Atomix.builder();
        Address endereco = new Address(IP, (3000 + IdCliente));
        Atomix a = builder.withMemberId("IdCliente-" + IdCliente)
                .withAddress(endereco).withMembershipProvider(BootstrapDiscoveryProvider.builder()
                .withNodes(Lists.newArrayList(members))
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
        private Member nextNode() {
        Address address = Address.from("127.0.0.1", ++this.primeiraPorta);
        return Member.builder(MemberId.from(String.valueOf(++this.contador)))
                .withAddress(address)
                .build();
    }

    private List<Member> returnMembers(int nodes) throws Exception {
        List<Member> members = new ArrayList<>();

        for (int i = 0; i < nodes; i++) {
            names.add("member-" + this.contador);
            members.add(nextNode());
        }

        return members;
    }

}
