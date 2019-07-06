package grpc;

import java.io.IOException;
import java.util.logging.Logger;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigInteger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.io.*;
import java.math.*;
import java.util.ArrayList;
import java.util.Random;
import io.atomix.cluster.MemberId;
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
import java.util.concurrent.CompletableFuture;

public class Servidor {

    private BigInteger quantidade_chaves = new BigInteger("2"); //Quantidade de chaves que o chord tera(Maior chave)
    private BigInteger chave_responsavel = new BigInteger("1"); // Chave em que o servidor eh responsavel
    private static FingerTable tabela; //Tabela de nos 
    private static final Logger logger = Logger.getLogger(Servidor.class.getName());
    private int quantidade_threads = 15;
    public int porta, saltoProximaPorta, numeroDeNos, numeroBitsId;
    public BaseDados Banco;
    private Fila F2;
    private Fila F3;
    private Fila F4;
    public Fila F1;
    public String retorno;
    public int primeiraPorta = 59043;
    private String ip;
    private ComunicaThread com;

    public Servidor(int porta, int porta_servidor) throws IOException, Exception {
        this.porta = porta;
        this.F1 = new Fila();
        this.F2 = new Fila();
        this.F3 = new Fila();
        this.F4 = new Fila();
        this.Banco = new BaseDados();
        this.tabela = new FingerTable(this, porta_servidor);
        this.com = new ComunicaThread();
        this.Banco.RecuperarBanco(this.chave_responsavel.toString());
        
        setConfig("servers.txt");
    }

    public BigInteger getQuantidadeChaves() {
        return this.quantidade_chaves;
    }

    public BigInteger getChave() {
        return this.chave_responsavel;
    }

    public void start() throws IOException {
        /* The port on which the server should run */

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
         DistributedMap<BigInteger, byte[]> map = a.<BigInteger,byte[]>mapBuilder("map-database")
                .withCacheEnabled()
                .build();
        this.Banco = new BaseDados(map);

           a.getCommunicationService().subscribe("mensagem-big", s::decode, cmd -> {

            Comando cmdRecebido = (Comando) cmd;

            logger.info("Comando recebido: " + cmdRecebido.getComando());

            if (cmdRecebido == null) {
                logger.info("Comando inválido");
                return CompletableFuture.completedFuture(new Comando("COMANDO INVÁLIDO",new BigInteger("-1")));
            }
            
        this.F1.put(cmdRecebido);
        CopiarLista copy = new CopiarLista(this.F1, this.F2, this.F3, this.F4, this.porta);
        new Thread(copy).start();
        AplicarAoBanco bancoDados = new AplicarAoBanco(this.Banco, this.F3, this);
        new Thread(bancoDados).start();
        SnapShot snapshot = new SnapShot(this.Banco, this.com, this.chave_responsavel.toString());
        new Thread(snapshot).start();
        Log log = new Log(this.F2, this.com, snapshot, this.chave_responsavel.toString());
        new Thread(log).start();
        String retorno = "Erro ao processar comando";
            while (this.retorno != null) {
               retorno = this.retorno;
                this.retorno = null;
                 logger.info("Retorno ao cliente " + retorno);
            }
         return CompletableFuture.completedFuture(new Comando(retorno,new BigInteger("-1")));

        }, s::encode);
        logger.info("Server started, listening on " + this.porta);
    }



    public BigInteger getRandom(int length) {
        Random random = new Random();
        byte[] data = new byte[length];
        random.nextBytes(data);
        return new BigInteger(data);
    }

    public void setConfig(String arq) throws FileNotFoundException, IOException {
        File arquivo = new File(arq);
        if (arquivo.exists()) {
            FileReader arq2 = new FileReader(arquivo);
            BufferedReader lerArq = new BufferedReader(arq2);
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(arquivo, true)));

            String[] str = null;
            String linha = "";
            byte[] dados = null;
            while ((linha = lerArq.readLine()) != null) {
                str = linha.split(";");
            }
            String[] str2 = str;
            System.out.println(str);
            if (str2 != null) {

                this.ip = str2[0];
                this.saltoProximaPorta = Integer.parseInt(str2[1]);
                this.numeroBitsId = Integer.parseInt(str2[2]);
                this.numeroDeNos = Integer.parseInt(str2[3]);
                this.primeiraPorta = Integer.parseInt(str2[4]);
            }

        }
    }
public Serializer s;
public Atomix a;

    public static void main(String[] args) throws IOException, InterruptedException, Exception {
        int IdServidor = Integer.parseInt(args[0]);
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
        AtomixBuilder builder = Atomix.builder();
        Atomix a = builder.withMemberId("member-" + IdServidor)
                .withAddress(enderecos.get(IdServidor))
                .withMembershipProvider(BootstrapDiscoveryProvider.builder()
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
                .withProfiles(ConsensusProfile.builder().withDataPath("C:\\server" + IdServidor).withMembers("member-1", "member-2", "member-3").build())
                .build();

        int porta = 59043;
        int porta2 = 59045;
        int flag = 0;
        Servidor server1 = new Servidor(porta, -1);
        server1.a = a;
        server1.s = s;
        server1.start();

    }

  
}
