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
    public int porta;
    public String host;
	    private ComunicaThread com = new ComunicaThread();
		private static final Logger logger = Logger.getLogger(Cliente.class.getName());
		public final ManagedChannel channel;
                public String comando;
                
		private final ServicoGrpc.ServicoBlockingStub blockingStub;
		public Cliente(String host, int port) {
                this(ManagedChannelBuilder.forAddress(host,port).usePlaintext(true).build());
                this.comando = null;
                this.host = host;
        this.porta = port;
		}
		public Cliente(String host, int port,Comando comando) {
                this(ManagedChannelBuilder.forAddress(host,port).usePlaintext(true).build());
        String command;
        command = comando.getComando()+" "+comando.getChave();
        if (comando.getValor() != null) {
            command = command+" "+ comando.getValor();
        }
        this.host = host;
        this.porta = port;
                this.comando = command;
		}              
		Cliente(ManagedChannel channel) {
			this.channel = channel;
			blockingStub = ServicoGrpc.newBlockingStub(channel);
		}

		public void shutdown() throws InterruptedException {
			channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
		}


		

		/**
		 * Greet server. If provided, the first element of {@code args} is the name to
		 * use in the greeting.
		 */
		public static void main(String[] args) throws Exception {
                           Cliente cliente = new Cliente("127.0.0.1", 59043);	
                    try {
                               cliente.executa(cliente);
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
        
    }
                 public void enviaComando(Cliente cliente) throws IOException, InterruptedException{
                 ImprimeMensagem imprimir = new ImprimeMensagem(cliente,this.com);
                  imprimir.enviaComando(this.comando);
                  this.comando = null;
                 }
                
	}