package gRPC.proto;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.12.0)",
    comments = "Source: gRPC.proto")
public final class ChordServiceGrpc {

  private ChordServiceGrpc() {}

  public static final String SERVICE_NAME = "gRPC.ChordService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getEscutandoMethod()} instead. 
  public static final io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      gRPC.proto.DataNode> METHOD_ESCUTANDO = getEscutandoMethodHelper();

  private static volatile io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      gRPC.proto.DataNode> getEscutandoMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      gRPC.proto.DataNode> getEscutandoMethod() {
    return getEscutandoMethodHelper();
  }

  private static io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      gRPC.proto.DataNode> getEscutandoMethodHelper() {
    io.grpc.MethodDescriptor<com.google.protobuf.Empty, gRPC.proto.DataNode> getEscutandoMethod;
    if ((getEscutandoMethod = ChordServiceGrpc.getEscutandoMethod) == null) {
      synchronized (ChordServiceGrpc.class) {
        if ((getEscutandoMethod = ChordServiceGrpc.getEscutandoMethod) == null) {
          ChordServiceGrpc.getEscutandoMethod = getEscutandoMethod = 
              io.grpc.MethodDescriptor.<com.google.protobuf.Empty, gRPC.proto.DataNode>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "gRPC.ChordService", "escutando"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gRPC.proto.DataNode.getDefaultInstance()))
                  .setSchemaDescriptor(new ChordServiceMethodDescriptorSupplier("escutando"))
                  .build();
          }
        }
     }
     return getEscutandoMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getSetAnteriorMethod()} instead. 
  public static final io.grpc.MethodDescriptor<gRPC.proto.DataNode,
      com.google.protobuf.Empty> METHOD_SET_ANTERIOR = getSetAnteriorMethodHelper();

  private static volatile io.grpc.MethodDescriptor<gRPC.proto.DataNode,
      com.google.protobuf.Empty> getSetAnteriorMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<gRPC.proto.DataNode,
      com.google.protobuf.Empty> getSetAnteriorMethod() {
    return getSetAnteriorMethodHelper();
  }

  private static io.grpc.MethodDescriptor<gRPC.proto.DataNode,
      com.google.protobuf.Empty> getSetAnteriorMethodHelper() {
    io.grpc.MethodDescriptor<gRPC.proto.DataNode, com.google.protobuf.Empty> getSetAnteriorMethod;
    if ((getSetAnteriorMethod = ChordServiceGrpc.getSetAnteriorMethod) == null) {
      synchronized (ChordServiceGrpc.class) {
        if ((getSetAnteriorMethod = ChordServiceGrpc.getSetAnteriorMethod) == null) {
          ChordServiceGrpc.getSetAnteriorMethod = getSetAnteriorMethod = 
              io.grpc.MethodDescriptor.<gRPC.proto.DataNode, com.google.protobuf.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "gRPC.ChordService", "setAnterior"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gRPC.proto.DataNode.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
                  .setSchemaDescriptor(new ChordServiceMethodDescriptorSupplier("setAnterior"))
                  .build();
          }
        }
     }
     return getSetAnteriorMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getSetPrimeiroUltimoMethod()} instead. 
  public static final io.grpc.MethodDescriptor<gRPC.proto.DataNode,
      gRPC.proto.DataNode> METHOD_SET_PRIMEIRO_ULTIMO = getSetPrimeiroUltimoMethodHelper();

  private static volatile io.grpc.MethodDescriptor<gRPC.proto.DataNode,
      gRPC.proto.DataNode> getSetPrimeiroUltimoMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<gRPC.proto.DataNode,
      gRPC.proto.DataNode> getSetPrimeiroUltimoMethod() {
    return getSetPrimeiroUltimoMethodHelper();
  }

  private static io.grpc.MethodDescriptor<gRPC.proto.DataNode,
      gRPC.proto.DataNode> getSetPrimeiroUltimoMethodHelper() {
    io.grpc.MethodDescriptor<gRPC.proto.DataNode, gRPC.proto.DataNode> getSetPrimeiroUltimoMethod;
    if ((getSetPrimeiroUltimoMethod = ChordServiceGrpc.getSetPrimeiroUltimoMethod) == null) {
      synchronized (ChordServiceGrpc.class) {
        if ((getSetPrimeiroUltimoMethod = ChordServiceGrpc.getSetPrimeiroUltimoMethod) == null) {
          ChordServiceGrpc.getSetPrimeiroUltimoMethod = getSetPrimeiroUltimoMethod = 
              io.grpc.MethodDescriptor.<gRPC.proto.DataNode, gRPC.proto.DataNode>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "gRPC.ChordService", "setPrimeiroUltimo"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gRPC.proto.DataNode.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gRPC.proto.DataNode.getDefaultInstance()))
                  .setSchemaDescriptor(new ChordServiceMethodDescriptorSupplier("setPrimeiroUltimo"))
                  .build();
          }
        }
     }
     return getSetPrimeiroUltimoMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ChordServiceStub newStub(io.grpc.Channel channel) {
    return new ChordServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ChordServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new ChordServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ChordServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new ChordServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class ChordServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void escutando(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<gRPC.proto.DataNode> responseObserver) {
      asyncUnimplementedUnaryCall(getEscutandoMethodHelper(), responseObserver);
    }

    /**
     */
    public void setAnterior(gRPC.proto.DataNode request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      asyncUnimplementedUnaryCall(getSetAnteriorMethodHelper(), responseObserver);
    }

    /**
     */
    public void setPrimeiroUltimo(gRPC.proto.DataNode request,
        io.grpc.stub.StreamObserver<gRPC.proto.DataNode> responseObserver) {
      asyncUnimplementedUnaryCall(getSetPrimeiroUltimoMethodHelper(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getEscutandoMethodHelper(),
            asyncUnaryCall(
              new MethodHandlers<
                com.google.protobuf.Empty,
                gRPC.proto.DataNode>(
                  this, METHODID_ESCUTANDO)))
          .addMethod(
            getSetAnteriorMethodHelper(),
            asyncUnaryCall(
              new MethodHandlers<
                gRPC.proto.DataNode,
                com.google.protobuf.Empty>(
                  this, METHODID_SET_ANTERIOR)))
          .addMethod(
            getSetPrimeiroUltimoMethodHelper(),
            asyncUnaryCall(
              new MethodHandlers<
                gRPC.proto.DataNode,
                gRPC.proto.DataNode>(
                  this, METHODID_SET_PRIMEIRO_ULTIMO)))
          .build();
    }
  }

  /**
   */
  public static final class ChordServiceStub extends io.grpc.stub.AbstractStub<ChordServiceStub> {
    private ChordServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ChordServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ChordServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ChordServiceStub(channel, callOptions);
    }

    /**
     */
    public void escutando(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<gRPC.proto.DataNode> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getEscutandoMethodHelper(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void setAnterior(gRPC.proto.DataNode request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getSetAnteriorMethodHelper(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void setPrimeiroUltimo(gRPC.proto.DataNode request,
        io.grpc.stub.StreamObserver<gRPC.proto.DataNode> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getSetPrimeiroUltimoMethodHelper(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class ChordServiceBlockingStub extends io.grpc.stub.AbstractStub<ChordServiceBlockingStub> {
    private ChordServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ChordServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ChordServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ChordServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public gRPC.proto.DataNode escutando(com.google.protobuf.Empty request) {
      return blockingUnaryCall(
          getChannel(), getEscutandoMethodHelper(), getCallOptions(), request);
    }

    /**
     */
    public com.google.protobuf.Empty setAnterior(gRPC.proto.DataNode request) {
      return blockingUnaryCall(
          getChannel(), getSetAnteriorMethodHelper(), getCallOptions(), request);
    }

    /**
     */
    public gRPC.proto.DataNode setPrimeiroUltimo(gRPC.proto.DataNode request) {
      return blockingUnaryCall(
          getChannel(), getSetPrimeiroUltimoMethodHelper(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class ChordServiceFutureStub extends io.grpc.stub.AbstractStub<ChordServiceFutureStub> {
    private ChordServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ChordServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ChordServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ChordServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<gRPC.proto.DataNode> escutando(
        com.google.protobuf.Empty request) {
      return futureUnaryCall(
          getChannel().newCall(getEscutandoMethodHelper(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> setAnterior(
        gRPC.proto.DataNode request) {
      return futureUnaryCall(
          getChannel().newCall(getSetAnteriorMethodHelper(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<gRPC.proto.DataNode> setPrimeiroUltimo(
        gRPC.proto.DataNode request) {
      return futureUnaryCall(
          getChannel().newCall(getSetPrimeiroUltimoMethodHelper(), getCallOptions()), request);
    }
  }

  private static final int METHODID_ESCUTANDO = 0;
  private static final int METHODID_SET_ANTERIOR = 1;
  private static final int METHODID_SET_PRIMEIRO_ULTIMO = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ChordServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ChordServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_ESCUTANDO:
          serviceImpl.escutando((com.google.protobuf.Empty) request,
              (io.grpc.stub.StreamObserver<gRPC.proto.DataNode>) responseObserver);
          break;
        case METHODID_SET_ANTERIOR:
          serviceImpl.setAnterior((gRPC.proto.DataNode) request,
              (io.grpc.stub.StreamObserver<com.google.protobuf.Empty>) responseObserver);
          break;
        case METHODID_SET_PRIMEIRO_ULTIMO:
          serviceImpl.setPrimeiroUltimo((gRPC.proto.DataNode) request,
              (io.grpc.stub.StreamObserver<gRPC.proto.DataNode>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class ChordServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ChordServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return gRPC.proto.MyProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ChordService");
    }
  }

  private static final class ChordServiceFileDescriptorSupplier
      extends ChordServiceBaseDescriptorSupplier {
    ChordServiceFileDescriptorSupplier() {}
  }

  private static final class ChordServiceMethodDescriptorSupplier
      extends ChordServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ChordServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (ChordServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ChordServiceFileDescriptorSupplier())
              .addMethod(getEscutandoMethodHelper())
              .addMethod(getSetAnteriorMethodHelper())
              .addMethod(getSetPrimeiroUltimoMethodHelper())
              .build();
        }
      }
    }
    return result;
  }
}
