// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: gRPC.proto

package gRPC.proto;

public final class MyProto {
  private MyProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_gRPC_ChaveRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_gRPC_ChaveRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_gRPC_ServerResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_gRPC_ServerResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_gRPC_ValorRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_gRPC_ValorRequest_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\ngRPC.proto\022\004gRPC\"\035\n\014ChaveRequest\022\r\n\005ch" +
      "ave\030\001 \001(\t\"\"\n\016ServerResponse\022\020\n\010response\030" +
      "\001 \001(\t\",\n\014ValorRequest\022\r\n\005chave\030\001 \001(\t\022\r\n\005" +
      "valor\030\002 \001(\t2\341\001\n\007Servico\0224\n\006delete\022\022.gRPC" +
      ".ChaveRequest\032\024.gRPC.ServerResponse\"\000\0224\n" +
      "\006select\022\022.gRPC.ChaveRequest\032\024.gRPC.Serve" +
      "rResponse\"\000\0224\n\006insert\022\022.gRPC.ValorReques" +
      "t\032\024.gRPC.ServerResponse\"\000\0224\n\006update\022\022.gR" +
      "PC.ValorRequest\032\024.gRPC.ServerResponse\"\000B" +
      "\035\n\ngRPC.protoB\007MyProtoP\001\242\002\003HLWb\006proto3"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_gRPC_ChaveRequest_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_gRPC_ChaveRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_gRPC_ChaveRequest_descriptor,
        new java.lang.String[] { "Chave", });
    internal_static_gRPC_ServerResponse_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_gRPC_ServerResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_gRPC_ServerResponse_descriptor,
        new java.lang.String[] { "Response", });
    internal_static_gRPC_ValorRequest_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_gRPC_ValorRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_gRPC_ValorRequest_descriptor,
        new java.lang.String[] { "Chave", "Valor", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}