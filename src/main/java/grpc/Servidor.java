package grpc;

import grpc.command.CreateCommand;
import grpc.command.DeleteCommand;
import grpc.command.ReadQuery;
import grpc.command.UpdateCommand;
import grpc.type.Data;
import java.io.IOException;
import java.util.logging.Logger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.ArrayList;
import io.atomix.catalyst.transport.Address;
import io.atomix.catalyst.transport.netty.NettyTransport;
import io.atomix.copycat.server.Commit;
import io.atomix.copycat.server.CopycatServer;
import io.atomix.copycat.server.StateMachine;
import io.atomix.copycat.server.storage.Storage;
import io.atomix.copycat.server.storage.StorageLevel;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class Servidor extends StateMachine {

    public int contador = 0;
    private BigInteger quantidade_chaves = new BigInteger("2");
    private BigInteger chave_responsavel = new BigInteger("1"); // Chave em que o servidor eh responsavel
    private static FingerTable tabela; //Tabela de nos 
    private static final Logger logger = Logger.getLogger(Servidor.class.getName());
    private int quantidade_threads = 15;
    public BaseDados Banco;
    private static BlockingQueue< Comando> F1 = new LinkedBlockingDeque<>();
    private static BlockingQueue< Comando> F2 = new LinkedBlockingDeque<>();
    private static BlockingQueue< Comando> F3 = new LinkedBlockingDeque<>();
    public String retorno;
    public int primeiraPorta = 59043;
    private String ip;
    private ComunicaThread com;
    public CopycatServer server;
    public int IdServidor = 0;
    ArrayList<Address> enderecos;

    public Servidor(int porta_servidor) throws IOException, Exception {

        this.Banco = new BaseDados();
        this.tabela = new FingerTable(this, porta_servidor);
        this.com = new ComunicaThread();
        this.Banco.RecuperarBanco(this.chave_responsavel.toString());
    }

    public Servidor() {
        this.Banco = new BaseDados();
        this.com = new ComunicaThread();
    }

    public static void main(String[] args) throws IOException, InterruptedException, Exception {
        int IdServidor = Integer.parseInt(args[0]);
        Servidor server1 = new Servidor();
        server1.IdServidor = IdServidor;
        ArrayList<Address> enderecos = new ArrayList<>();
        server1.enderecos = enderecos;
        for (int i = 1; i < args.length; i++) {
            Address end = new Address(args[i], Integer.parseInt(args[i + 1]));
            enderecos.add(end);
            i++;
        }

        CopycatServer.Builder builder = CopycatServer.builder(enderecos.get(server1.IdServidor))
                .withStateMachine(Servidor::new)
                .withTransport(NettyTransport.builder()
                        .withThreads(4)
                        .build())
                .withStorage(Storage.builder()
                        .withDirectory(new File("logs_" + server1.IdServidor)) //Must be unique
                        .withStorageLevel(StorageLevel.DISK)
                        .build());

        CopycatServer server = builder.build();
        server1.server = server;

        server1.start();

    }

    public void start() throws IOException {

        ExecutorService thds = Executors.newFixedThreadPool(this.quantidade_threads);
        CopiarLista copy = new CopiarLista(this.F1, this.F2, this.F3);
        new Thread(copy).start();
        AplicarAoBanco bancoDados = new AplicarAoBanco(this.Banco, this.F3, this);
        SnapShot snapshot = new SnapShot(this.Banco, this.com, this.chave_responsavel.toString());
        new Thread(snapshot).start();
        Log log = new Log(this.F2, this.com, snapshot, this.chave_responsavel.toString());
        new Thread(log).start();
        if (this.IdServidor == 0) {
            System.out.println("Server started");
            server.bootstrap().join();
        } else {
            System.out.println("Server started2");
            server.join(this.enderecos).join();
        }

    }

    public Boolean Create(Commit<CreateCommand> commit) throws UnsupportedEncodingException {
        try {
            CreateCommand aec = commit.operation();
            String dado = new String(aec.value, "UTF-8");
            System.out.println("Valor: " +dado);
            Comando cmd = new Comando("INSERT", aec.value.toString(), aec.key);
            AplicarAoBanco bancoDados = new AplicarAoBanco(this.Banco, this.F3, this);
            String retorno = bancoDados.ProcessaComando(cmd);
            Data e = new Data(aec.key, dado.getBytes());
            this.F1.add(cmd);
            if (retorno == "INSERT realizado com Sucesso") {
                System.out.println("Inserido");
                Data result = new Data(aec.key, this.Banco.read(aec.key).getBytes());
                return true;
            } else {
                return false;
            }

        } finally {
            commit.close();
        }
    }

    public Data Read(Commit<ReadQuery> commit) throws UnsupportedEncodingException {
        try {
            ReadQuery geq = commit.operation();
            String dado = this.Banco.read(geq.key);
            System.out.println("valor = "+dado);
            Comando cmd = new Comando("SELECT", geq.key);
            AplicarAoBanco bancoDados = new AplicarAoBanco(this.Banco, this.F3, this);
            String retorno = bancoDados.ProcessaComando(cmd);
            if (retorno == "Chave nao existe") {
                Data result = new Data(geq.key, retorno.getBytes());
                this.F1.add(cmd);
                System.out.println(retorno);
                return result;
            } else {
                Data result = new Data(geq.key,dado.getBytes());
                this.F1.add(cmd);
                System.out.println(retorno);
                return result;
            }

        } finally {
            commit.close();
        }
    }

    public Boolean Update(Commit<UpdateCommand> commit) throws UnsupportedEncodingException {
        try {
            UpdateCommand aec = commit.operation();
            Comando cmd = new Comando("UPDATE", aec.value.toString(), aec.key);
            AplicarAoBanco bancoDados = new AplicarAoBanco(this.Banco, this.F3, this);
            String retorno = bancoDados.ProcessaComando(cmd);
            this.F1.add(cmd);
            if (retorno == "UPDATE Com Sucesso") {
                System.out.println("Atualizado");
                Data result = new Data(aec.key, this.Banco.read(aec.key).getBytes());
                return true;
            } else {
                return true;
            }
        } finally {
            commit.close();
        }
    }

    public Boolean Delete(Commit<DeleteCommand> commit) throws UnsupportedEncodingException {
        try {
            DeleteCommand aec = commit.operation();
            Comando cmd = new Comando("DELETE", aec.key);
            this.F1.add(cmd);
            AplicarAoBanco bancoDados = new AplicarAoBanco(this.Banco, this.F3, this);
            String retorno = bancoDados.ProcessaComando(cmd);
            Data e = new Data(aec.key, retorno.getBytes());
            if (retorno == "Nao existe chave para ser deletada") {
                System.out.println("Deletado");
                Data result = new Data(aec.key, this.Banco.read(aec.key).getBytes());
                return true;
            } else {
                return false;
            }

        } finally {
            commit.close();
        }
    }

    public BigInteger getQuantidadeChaves() {
        return this.quantidade_chaves;
    }

    public BigInteger getChave() {
        return this.chave_responsavel;
    }

}
