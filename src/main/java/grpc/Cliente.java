package grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import grpc.ImprimeMensagem;
import gRPC.proto.ServicoGrpc;
import gRPC.proto.ServerResponse;
import gRPC.proto.ChaveRequest;
import gRPC.proto.ValorRequest;
import java.io.IOException;
import java.net.Socket;

public class Cliente {
	    private ComunicaThread com = new ComunicaThread();
		private static final Logger logger = Logger.getLogger(Cliente.class.getName());
		public final ManagedChannel channel;
                public String comando;
		private final ServicoGrpc.ServicoBlockingStub blockingStub;
		public Cliente(String host, int port) {
                this(ManagedChannelBuilder.forAddress(host, port).build());
                                    this.comando = null;
		}

		Cliente(ManagedChannel channel) {
			this.channel = channel;
			blockingStub = ServicoGrpc.newBlockingStub(channel);
		}

		public void shutdown() throws InterruptedException {
			channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
		}

		/** Say hello to server. */
		

		/**
		 * Greet server. If provided, the first element of {@code args} is the name to
		 * use in the greeting.
		 */
		public static void main(String[] args) throws Exception {
                           Cliente cliente = new Cliente("127.0.0.1", 50051);	
                    try {
                               cliente.executa(cliente);
                                                //client.insert(1,"valor");
			}catch(Exception e){
            System.out.println(e);
            System.exit(0);
        } finally {
				cliente.shutdown();
			}
		}
                 public void executa(Cliente cliente) throws IOException, InterruptedException{
        ImprimeMensagem imprimir = new ImprimeMensagem(cliente,this.com);
        Thread im = new Thread(imprimir);
        im.start();
        //Lendo mensagem do teclado e mandando para o servidor
        String comandoRecebido = this.comando;
        LerComandos comandos = new LerComandos(cliente,this.com);
        Thread c = new Thread(comandos);
        c.start();
        
        im.join();
        c.stop();
        //cliente.close();
        
    }
                
	}