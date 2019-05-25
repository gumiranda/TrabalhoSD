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
import java.util.logging.Level;

public class Servidor {

    private static final Logger logger = Logger.getLogger(Servidor.class.getName());
    private int quantidade_threads = 15;
    public int porta;
    public BaseDados Banco;
    private Fila F2;
    private Fila F3;
    private Fila F4;
    public Fila F1;
    private Server server;
    public String retorno;
    public Servidor(int porta) throws IOException {
        this.porta = porta;
        this.F1 = new Fila();
        this.F2 = new Fila();
        this.F3 = new Fila();
        this.F4 = new Fila();
        this.Banco = new BaseDados();
        this.Banco.RecuperardoLog("Log.txt");
    }

    public void start() throws IOException {
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
        VerificaServidoresReponsaveis invocaServers = new VerificaServidoresReponsaveis(this.F2, this.F3, this.F4, this);
        new Thread(invocaServers).start();

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

    public void stop() {
        if (server != null) {
            server.shutdown();
        }
    }
public void transmitResponse(String sr) throws IOException{
    if(this.porta == 59043){

    }else{
                    try {
                this.stop();
                int porta;
                porta = 59043; 
                Servidor servidor = new Servidor(porta);
                servidor.start();
                ServerResponse response = ServerResponse.newBuilder().setResponse(sr).build();
                System.out.println(response);
                servidor.blockUntilShutdown();
            } catch (IOException ex) {
                Logger.getLogger(VerificaServidoresReponsaveis.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(VerificaServidoresReponsaveis.class.getName()).log(Level.SEVERE, null, ex);
            }
/*        server = ServerBuilder.forPort(59043).addService(new ServiceImpl(this.F1)).build().start();
        ServerResponse response = ServerResponse.newBuilder().setResponse(sr).build();
        System.out.println(response);
        server = null;
        this.start();
  */  }
    
}

    /**
     * Await termination on the main thread since the grpc library uses daemon
     * threads.
     */
    public void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    /**
     * Main launches the server from the command line.
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        int porta = 59043;
        int flag = 0;
         Servidor server1 = new Servidor(porta);
      /*   Servidor server2 = new Servidor(porta+1);
         Servidor server3 = new Servidor(porta+2);
         Servidor server4 = new Servidor(porta+3);
         Servidor server5 = new Servidor(porta+4);
*/
        server1.start();
        server1.blockUntilShutdown();

/*while(true){
            server1.start();
    if(flag == 1){
            server5.blockUntilShutdown();        
    }
        server2.start();
        server1.blockUntilShutdown();
        server3.start();
        server2.blockUntilShutdown();        
        server4.start();
        server3.blockUntilShutdown();
        server5.start();
        server4.blockUntilShutdown();
        flag = 1;
}
         */
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
             */        }

        @Override
        public void insert(ValorRequest req, StreamObserver<ServerResponse> responseObserver) {
            Comando c;
            c = new Comando("INSERT", req.getValor(), new BigInteger(req.getChave()), responseObserver);
            this.f1.put(c);
            /*        ServerResponse reply = ServerResponse.newBuilder().setResponse("Inserindo dado com chave: " + req.getChave() + " e valor: " + req.getValor()).build();
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
             */        }

        @Override
        public void update(ValorRequest req, StreamObserver<ServerResponse> responseObserver) {
            Comando c;
            c = new Comando("UPDATE", req.getValor(), new BigInteger(req.getChave()), responseObserver);
            this.f1.put(c);
            /*            ServerResponse reply = ServerResponse.newBuilder().setResponse("Atualizando dado com chave: " + req.getChave() + " e valor: " + req.getValor()).build();
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
             */        }
    }
}
