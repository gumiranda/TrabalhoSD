package grpc;

import gRPC.proto.ChaveRequest;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import java.io.IOException;
import java.util.logging.Logger;

import gRPC.proto.ServerResponse;
import gRPC.proto.ValorRequest;
import java.math.BigInteger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Servidor {

    private static final Logger logger = Logger.getLogger(Servidor.class.getName());
    private int quantidade_threads = 15;
    private int porta;
    private BaseDados Banco;

    private Fila F2;
    private Fila F3;
    private Fila F4;
    private Fila F1;
    private Server server;

    public Servidor(int porta) throws IOException {
        this.porta = porta;
        this.F1 = new Fila();
        this.F2 = new Fila();
        this.F3 = new Fila();
        this.F4 = new Fila();
        this.Banco = new BaseDados();
        this.Banco.RecuperardoLog("Log.txt");
    }

    private void start() throws IOException {
        /* The port on which the server should run */

        ExecutorService thds = Executors.newFixedThreadPool(this.quantidade_threads);
        server = ServerBuilder.forPort(this.porta).addService(new ServiceImpl(this.F1)).build().start();
        logger.info("Server started, listening on " + this.porta);
        CopiarLista copy = new CopiarLista(this.F1, this.F2, this.F3, this.F4, this.porta);
        new Thread(copy).start();

        Log log = new Log(this.F2);
        new Thread(log).start();
        AplicarAoBanco bancoDados = new AplicarAoBanco(this.Banco, this.F3, this);
        new Thread(bancoDados).start();

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                // Use stderr here since the logger may have been reset by its JVM shutdown
                // hook.
                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                Servidor.this.stop();
                System.err.println("*** server shut down");
            }
        });
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    /**
     * Await termination on the main thread since the grpc library uses daemon
     * threads.
     */
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    /**
     * Main launches the server from the command line.
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        int porta = 59043;
        final Servidor server = new Servidor(59043);
        server.start();
        server.blockUntilShutdown();
    }

    static class ServiceImpl extends gRPC.proto.ServicoGrpc.ServicoImplBase {

        private Fila f1;

        public ServiceImpl(Fila f1) {
            this.f1 = f1;
        }

        @Override
        public void select(ChaveRequest req, StreamObserver<ServerResponse> responseObserver) {
            Comando c;
            c = new Comando("SELECT", new BigInteger(req.getChave()), responseObserver);
            this.f1.put(c);
        /*    ServerResponse reply = ServerResponse.newBuilder().setResponse("Selecionando dado com chave: " + req.getChave()).build();
            responseObserver.onNext(reply);
            responseObserver.onCompleted();*/
        }

        @Override
        public void delete(ChaveRequest req, StreamObserver<ServerResponse> responseObserver) {
            Comando c;
            c = new Comando("DELETE", new BigInteger(req.getChave()), responseObserver);
            this.f1.put(c);
/*            ServerResponse reply = ServerResponse.newBuilder().setResponse("Deletando dado com chave: " + req.getChave()).build();
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
  */      }

        @Override
        public void insert(ValorRequest req, StreamObserver<ServerResponse> responseObserver) {
            Comando c;
            c = new Comando("INSERT", req.getValor(), new BigInteger(req.getChave()), responseObserver);
            this.f1.put(c);
    /*        ServerResponse reply = ServerResponse.newBuilder().setResponse("Inserindo dado com chave: " + req.getChave() + " e valor: " + req.getValor()).build();
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
      */  }

        @Override
        public void update(ValorRequest req, StreamObserver<ServerResponse> responseObserver) {
            Comando c;
            c = new Comando("UPDATE", req.getValor(), new BigInteger(req.getChave()), responseObserver);
            this.f1.put(c);
/*            ServerResponse reply = ServerResponse.newBuilder().setResponse("Atualizando dado com chave: " + req.getChave() + " e valor: " + req.getValor()).build();
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
  */      }
    }
}
