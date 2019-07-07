package grpc;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.logging.Logger;
import java.math.BigInteger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.*;
import java.math.*;
import java.util.concurrent.CountDownLatch;
import java.util.ArrayList;
import io.atomix.cluster.MemberId;
import io.atomix.cluster.Member;
import io.atomix.cluster.Node;
import io.atomix.cluster.discovery.BootstrapDiscoveryProvider;
import io.atomix.core.Atomix;
import io.atomix.core.AtomixBuilder;
import io.atomix.core.map.DistributedMap;
import io.atomix.core.profile.ConsensusProfile;
import io.atomix.utils.net.Address;
import io.atomix.utils.serializer.Namespace;
import io.atomix.utils.serializer.Namespaces;
import io.atomix.utils.serializer.Serializer;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class Servidor {

    public int contador = 0;
    private BigInteger quantidade_chaves = new BigInteger("2");
    Set<String> names = new HashSet<String>();
    private BigInteger chave_responsavel = new BigInteger("1"); // Chave em que o servidor eh responsavel
    private static FingerTable tabela; //Tabela de nos 
    private static final Logger logger = Logger.getLogger(Servidor.class.getName());
    private int quantidade_threads = 15;
    public BaseDados Banco;
    private Fila F2;
    private Fila F3;
    public Fila F1;
    public String retorno;
    public int primeiraPorta = 59043;
    private String ip;
    private ComunicaThread com;
    public Serializer s;
    public Atomix a;
    public int IdServidor = 0;

    public Servidor(int porta_servidor) throws IOException, Exception {
        this.F1 = new Fila();
        this.F2 = new Fila();
        this.F3 = new Fila();
        this.Banco = new BaseDados();
        this.tabela = new FingerTable(this, porta_servidor);
        this.com = new ComunicaThread();
        this.Banco.RecuperarBanco(this.chave_responsavel.toString());
    }

    public static void main(String[] args) throws IOException, InterruptedException, Exception {
        int IdServidor = Integer.parseInt(args[0]);
        Servidor server1 = new Servidor(-1);
        server1.IdServidor = IdServidor;
        ArrayList<Address> enderecos = new ArrayList<>();
        Serializer s = Serializer.using(Namespace.builder()
                .register(Namespaces.BASIC)
                .register(BigInteger.class)
                .register(MemberId.class)
                .register(Comando.class)
                .build());
        for (int i = 1; i < args.length; i++) {
            Address end = new Address(args[i], Integer.parseInt(args[i + 1]));
            enderecos.add(end);
            i++;
        }
        List<Member> members = server1.returnMembers(enderecos.size());
        AtomixBuilder builder = Atomix.builder();
        Atomix a = builder.withMemberId("member-" + server1.IdServidor)
                .withAddress(enderecos.get(server1.IdServidor))
                .withMembershipProvider(BootstrapDiscoveryProvider.builder()
                        .withNodes(Lists.newArrayList(members))
                        .build())
                .withProfiles(ConsensusProfile.builder().withDataPath("C:\\server" + server1.IdServidor).withMembers(server1.names).build())
                .build();

        server1.a = a;
        server1.s = s;
        server1.start();

    }

    public void start() throws IOException {
        ExecutorService thds = Executors.newFixedThreadPool(this.quantidade_threads);
        this.a.start().join();
        System.out.println("Cluster formado");
        this.a.getMembershipService().addListener(event -> {
            switch (event.type()) {
                case MEMBER_ADDED:
                    System.out.println(event.subject().id() + " entrou no cluster");
                    break;
                case MEMBER_REMOVED:
                    System.out.println(event.subject().id() + " saiu do cluster");
                    break;
            }
        });
        DistributedMap<BigInteger, byte[]> map = a.<BigInteger, byte[]>mapBuilder("map-database")
                .withCacheEnabled()
                .build();
        this.Banco = new BaseDados(map);

        a.getCommunicationService().subscribe("mensagem-big", s::decode, cmd -> {

            Comando cmdRecebido = (Comando) cmd;

            logger.info("Comando recebido: " + cmdRecebido.getComando());

            if (cmdRecebido == null) {
                logger.info("Comando inválido");
                return CompletableFuture.completedFuture(new Comando("COMANDO INVÁLIDO", new BigInteger("-1")));
            }

            this.F1.put(cmdRecebido);
            CopiarLista copy = new CopiarLista(this.F1, this.F2, this.F3);
            new Thread(copy).start();
            AplicarAoBanco bancoDados = new AplicarAoBanco(this.Banco, this.F3, this);
            SnapShot snapshot = new SnapShot(this.Banco, this.com, this.chave_responsavel.toString());
            new Thread(snapshot).start();
            Log log = new Log(this.F2, this.com, snapshot, this.chave_responsavel.toString());
            new Thread(log).start();
            String retorno = "";
            Comando c = F3.getFirst();
            retorno = bancoDados.ProcessaComando(c);
            logger.info("Retorno ao cliente " + retorno);
            return CompletableFuture.completedFuture(new Comando(retorno, new BigInteger("-1")));

        }, s::encode);
        logger.info("Server started");
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

    public BigInteger getQuantidadeChaves() {
        return this.quantidade_chaves;
    }

    public BigInteger getChave() {
        return this.chave_responsavel;
    }

}
