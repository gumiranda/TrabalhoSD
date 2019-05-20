package grpc;

import gRPC.proto.ChaveRequest;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import java.io.IOException;
import java.util.logging.Logger;

import gRPC.proto.ServerResponse;
import gRPC.proto.ValorRequest;

public class Servidor {

		private static final Logger logger = Logger.getLogger(Servidor.class.getName());

		private Server server;

		private void start() throws IOException {
			/* The port on which the server should run */
			int port = 59043;
			server = ServerBuilder.forPort(port).addService(new ServiceImpl()).build().start();
			logger.info("Server started, listening on " + port);
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
			final Servidor server = new Servidor();
			server.start();
			server.blockUntilShutdown();
		}

		static class ServiceImpl extends gRPC.proto.ServicoGrpc.ServicoImplBase {

			@Override
			public void select(ChaveRequest req, StreamObserver<ServerResponse> responseObserver) {
				ServerResponse reply = ServerResponse.newBuilder().setResponse("Selecionando dado com chave: " + req.getChave()).build();
				responseObserver.onNext(reply);
				responseObserver.onCompleted();
			}

                    @Override
			public void delete(ChaveRequest req, StreamObserver<ServerResponse> responseObserver) {
				ServerResponse reply = ServerResponse.newBuilder().setResponse("Deletando dado com chave: " + req.getChave()).build();
				responseObserver.onNext(reply);
				responseObserver.onCompleted();
			}

			@Override
			public void insert(ValorRequest req, StreamObserver<ServerResponse> responseObserver) {
				ServerResponse reply = ServerResponse.newBuilder().setResponse("Inserindo dado com chave: " + req.getChave()+" e valor: "+req.getValor()).build();
				responseObserver.onNext(reply);
				responseObserver.onCompleted();
			}
                        
			@Override
			public void update(ValorRequest req, StreamObserver<ServerResponse> responseObserver) {
				ServerResponse reply = ServerResponse.newBuilder().setResponse("Atualizando dado com chave: " + req.getChave()+" e valor: "+req.getValor()).build();
				responseObserver.onNext(reply);
				responseObserver.onCompleted();
			}
		}
}
