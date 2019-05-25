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
 * <pre>
 * The greeting service definition.
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.12.0)",
    comments = "Source: gRPC.proto")
public final class ServicoGrpc {

  private ServicoGrpc() {}

  public static final String SERVICE_NAME = "gRPC.Servico";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getDeleteMethod()} instead. 
  public static final io.grpc.MethodDescriptor<gRPC.proto.ChaveRequest,
      gRPC.proto.ServerResponse> METHOD_DELETE = getDeleteMethodHelper();

  private static volatile io.grpc.MethodDescriptor<gRPC.proto.ChaveRequest,
      gRPC.proto.ServerResponse> getDeleteMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<gRPC.proto.ChaveRequest,
      gRPC.proto.ServerResponse> getDeleteMethod() {
    return getDeleteMethodHelper();
  }

  private static io.grpc.MethodDescriptor<gRPC.proto.ChaveRequest,
      gRPC.proto.ServerResponse> getDeleteMethodHelper() {
    io.grpc.MethodDescriptor<gRPC.proto.ChaveRequest, gRPC.proto.ServerResponse> getDeleteMethod;
    if ((getDeleteMethod = ServicoGrpc.getDeleteMethod) == null) {
      synchronized (ServicoGrpc.class) {
        if ((getDeleteMethod = ServicoGrpc.getDeleteMethod) == null) {
          ServicoGrpc.getDeleteMethod = getDeleteMethod = 
              io.grpc.MethodDescriptor.<gRPC.proto.ChaveRequest, gRPC.proto.ServerResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "gRPC.Servico", "delete"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gRPC.proto.ChaveRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gRPC.proto.ServerResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new ServicoMethodDescriptorSupplier("delete"))
                  .build();
          }
        }
     }
     return getDeleteMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getSelectMethod()} instead. 
  public static final io.grpc.MethodDescriptor<gRPC.proto.ChaveRequest,
      gRPC.proto.ServerResponse> METHOD_SELECT = getSelectMethodHelper();

  private static volatile io.grpc.MethodDescriptor<gRPC.proto.ChaveRequest,
      gRPC.proto.ServerResponse> getSelectMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<gRPC.proto.ChaveRequest,
      gRPC.proto.ServerResponse> getSelectMethod() {
    return getSelectMethodHelper();
  }

  private static io.grpc.MethodDescriptor<gRPC.proto.ChaveRequest,
      gRPC.proto.ServerResponse> getSelectMethodHelper() {
    io.grpc.MethodDescriptor<gRPC.proto.ChaveRequest, gRPC.proto.ServerResponse> getSelectMethod;
    if ((getSelectMethod = ServicoGrpc.getSelectMethod) == null) {
      synchronized (ServicoGrpc.class) {
        if ((getSelectMethod = ServicoGrpc.getSelectMethod) == null) {
          ServicoGrpc.getSelectMethod = getSelectMethod = 
              io.grpc.MethodDescriptor.<gRPC.proto.ChaveRequest, gRPC.proto.ServerResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "gRPC.Servico", "select"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gRPC.proto.ChaveRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gRPC.proto.ServerResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new ServicoMethodDescriptorSupplier("select"))
                  .build();
          }
        }
     }
     return getSelectMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getInsertMethod()} instead. 
  public static final io.grpc.MethodDescriptor<gRPC.proto.ValorRequest,
      gRPC.proto.ServerResponse> METHOD_INSERT = getInsertMethodHelper();

  private static volatile io.grpc.MethodDescriptor<gRPC.proto.ValorRequest,
      gRPC.proto.ServerResponse> getInsertMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<gRPC.proto.ValorRequest,
      gRPC.proto.ServerResponse> getInsertMethod() {
    return getInsertMethodHelper();
  }

  private static io.grpc.MethodDescriptor<gRPC.proto.ValorRequest,
      gRPC.proto.ServerResponse> getInsertMethodHelper() {
    io.grpc.MethodDescriptor<gRPC.proto.ValorRequest, gRPC.proto.ServerResponse> getInsertMethod;
    if ((getInsertMethod = ServicoGrpc.getInsertMethod) == null) {
      synchronized (ServicoGrpc.class) {
        if ((getInsertMethod = ServicoGrpc.getInsertMethod) == null) {
          ServicoGrpc.getInsertMethod = getInsertMethod = 
              io.grpc.MethodDescriptor.<gRPC.proto.ValorRequest, gRPC.proto.ServerResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "gRPC.Servico", "insert"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gRPC.proto.ValorRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gRPC.proto.ServerResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new ServicoMethodDescriptorSupplier("insert"))
                  .build();
          }
        }
     }
     return getInsertMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getUpdateMethod()} instead. 
  public static final io.grpc.MethodDescriptor<gRPC.proto.ValorRequest,
      gRPC.proto.ServerResponse> METHOD_UPDATE = getUpdateMethodHelper();

  private static volatile io.grpc.MethodDescriptor<gRPC.proto.ValorRequest,
      gRPC.proto.ServerResponse> getUpdateMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<gRPC.proto.ValorRequest,
      gRPC.proto.ServerResponse> getUpdateMethod() {
    return getUpdateMethodHelper();
  }

  private static io.grpc.MethodDescriptor<gRPC.proto.ValorRequest,
      gRPC.proto.ServerResponse> getUpdateMethodHelper() {
    io.grpc.MethodDescriptor<gRPC.proto.ValorRequest, gRPC.proto.ServerResponse> getUpdateMethod;
    if ((getUpdateMethod = ServicoGrpc.getUpdateMethod) == null) {
      synchronized (ServicoGrpc.class) {
        if ((getUpdateMethod = ServicoGrpc.getUpdateMethod) == null) {
          ServicoGrpc.getUpdateMethod = getUpdateMethod = 
              io.grpc.MethodDescriptor.<gRPC.proto.ValorRequest, gRPC.proto.ServerResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "gRPC.Servico", "update"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gRPC.proto.ValorRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  gRPC.proto.ServerResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new ServicoMethodDescriptorSupplier("update"))
                  .build();
          }
        }
     }
     return getUpdateMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ServicoStub newStub(io.grpc.Channel channel) {
    return new ServicoStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ServicoBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new ServicoBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ServicoFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new ServicoFutureStub(channel);
  }

  /**
   * <pre>
   * The greeting service definition.
   * </pre>
   */
  public static abstract class ServicoImplBase implements io.grpc.BindableService {

    /**
     */
    public void delete(gRPC.proto.ChaveRequest request,
        io.grpc.stub.StreamObserver<gRPC.proto.ServerResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getDeleteMethodHelper(), responseObserver);
    }

    /**
     */
    public void select(gRPC.proto.ChaveRequest request,
        io.grpc.stub.StreamObserver<gRPC.proto.ServerResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getSelectMethodHelper(), responseObserver);
    }

    /**
     */
    public void insert(gRPC.proto.ValorRequest request,
        io.grpc.stub.StreamObserver<gRPC.proto.ServerResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getInsertMethodHelper(), responseObserver);
    }

    /**
     */
    public void update(gRPC.proto.ValorRequest request,
        io.grpc.stub.StreamObserver<gRPC.proto.ServerResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getUpdateMethodHelper(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getDeleteMethodHelper(),
            asyncUnaryCall(
              new MethodHandlers<
                gRPC.proto.ChaveRequest,
                gRPC.proto.ServerResponse>(
                  this, METHODID_DELETE)))
          .addMethod(
            getSelectMethodHelper(),
            asyncUnaryCall(
              new MethodHandlers<
                gRPC.proto.ChaveRequest,
                gRPC.proto.ServerResponse>(
                  this, METHODID_SELECT)))
          .addMethod(
            getInsertMethodHelper(),
            asyncUnaryCall(
              new MethodHandlers<
                gRPC.proto.ValorRequest,
                gRPC.proto.ServerResponse>(
                  this, METHODID_INSERT)))
          .addMethod(
            getUpdateMethodHelper(),
            asyncUnaryCall(
              new MethodHandlers<
                gRPC.proto.ValorRequest,
                gRPC.proto.ServerResponse>(
                  this, METHODID_UPDATE)))
          .build();
    }
  }

  /**
   * <pre>
   * The greeting service definition.
   * </pre>
   */
  public static final class ServicoStub extends io.grpc.stub.AbstractStub<ServicoStub> {
    private ServicoStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ServicoStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ServicoStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ServicoStub(channel, callOptions);
    }

    /**
     */
    public void delete(gRPC.proto.ChaveRequest request,
        io.grpc.stub.StreamObserver<gRPC.proto.ServerResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getDeleteMethodHelper(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void select(gRPC.proto.ChaveRequest request,
        io.grpc.stub.StreamObserver<gRPC.proto.ServerResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getSelectMethodHelper(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void insert(gRPC.proto.ValorRequest request,
        io.grpc.stub.StreamObserver<gRPC.proto.ServerResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getInsertMethodHelper(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void update(gRPC.proto.ValorRequest request,
        io.grpc.stub.StreamObserver<gRPC.proto.ServerResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getUpdateMethodHelper(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * The greeting service definition.
   * </pre>
   */
  public static final class ServicoBlockingStub extends io.grpc.stub.AbstractStub<ServicoBlockingStub> {
    private ServicoBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ServicoBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ServicoBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ServicoBlockingStub(channel, callOptions);
    }

    /**
     */
    public gRPC.proto.ServerResponse delete(gRPC.proto.ChaveRequest request) {
      return blockingUnaryCall(
          getChannel(), getDeleteMethodHelper(), getCallOptions(), request);
    }

    /**
     */
    public gRPC.proto.ServerResponse select(gRPC.proto.ChaveRequest request) {
      return blockingUnaryCall(
          getChannel(), getSelectMethodHelper(), getCallOptions(), request);
    }

    /**
     */
    public gRPC.proto.ServerResponse insert(gRPC.proto.ValorRequest request) {
      return blockingUnaryCall(
          getChannel(), getInsertMethodHelper(), getCallOptions(), request);
    }

    /**
     */
    public gRPC.proto.ServerResponse update(gRPC.proto.ValorRequest request) {
      return blockingUnaryCall(
          getChannel(), getUpdateMethodHelper(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * The greeting service definition.
   * </pre>
   */
  public static final class ServicoFutureStub extends io.grpc.stub.AbstractStub<ServicoFutureStub> {
    private ServicoFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ServicoFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ServicoFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ServicoFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<gRPC.proto.ServerResponse> delete(
        gRPC.proto.ChaveRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getDeleteMethodHelper(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<gRPC.proto.ServerResponse> select(
        gRPC.proto.ChaveRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getSelectMethodHelper(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<gRPC.proto.ServerResponse> insert(
        gRPC.proto.ValorRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getInsertMethodHelper(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<gRPC.proto.ServerResponse> update(
        gRPC.proto.ValorRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getUpdateMethodHelper(), getCallOptions()), request);
    }
  }

  private static final int METHODID_DELETE = 0;
  private static final int METHODID_SELECT = 1;
  private static final int METHODID_INSERT = 2;
  private static final int METHODID_UPDATE = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ServicoImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ServicoImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_DELETE:
          serviceImpl.delete((gRPC.proto.ChaveRequest) request,
              (io.grpc.stub.StreamObserver<gRPC.proto.ServerResponse>) responseObserver);
          break;
        case METHODID_SELECT:
          serviceImpl.select((gRPC.proto.ChaveRequest) request,
              (io.grpc.stub.StreamObserver<gRPC.proto.ServerResponse>) responseObserver);
          break;
        case METHODID_INSERT:
          serviceImpl.insert((gRPC.proto.ValorRequest) request,
              (io.grpc.stub.StreamObserver<gRPC.proto.ServerResponse>) responseObserver);
          break;
        case METHODID_UPDATE:
          serviceImpl.update((gRPC.proto.ValorRequest) request,
              (io.grpc.stub.StreamObserver<gRPC.proto.ServerResponse>) responseObserver);
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

  private static abstract class ServicoBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ServicoBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return gRPC.proto.MyProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Servico");
    }
  }

  private static final class ServicoFileDescriptorSupplier
      extends ServicoBaseDescriptorSupplier {
    ServicoFileDescriptorSupplier() {}
  }

  private static final class ServicoMethodDescriptorSupplier
      extends ServicoBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ServicoMethodDescriptorSupplier(String methodName) {
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
      synchronized (ServicoGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ServicoFileDescriptorSupplier())
              .addMethod(getDeleteMethodHelper())
              .addMethod(getSelectMethodHelper())
              .addMethod(getInsertMethodHelper())
              .addMethod(getUpdateMethodHelper())
              .build();
        }
      }
    }
    return result;
  }
}
