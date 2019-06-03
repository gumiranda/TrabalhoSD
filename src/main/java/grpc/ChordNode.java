/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grpc;

import gRPC.proto.DataNode;
import java.math.BigInteger;
/**
 *
 * @author Samsung
 */
public class ChordNode {

   public DataNode getDataNode() {
return DataNode.newBuilder().setIp(this.ip).setPort(this.porta).build();
   }
   public int getId(){
       return Id;
   }
   public void setId(int id){
   this.Id = id;
   }
   public BigInteger getOffsetId(){
   return offsetId;
   }
   public void setOffsetId(BigInteger offsetId){
   this.offsetId = offsetId;
   }
   public BigInteger getMaxId(){
   return maxId;
   }
   public void setMaxId(BigInteger maxId){
       this.maxId = maxId;
   }
   public int getNumeroDeNodes(){
   return numeroDeNodes;
   }
   public void setNumeroDeNodes(int numeroDeNodes){
   this.numeroDeNodes = numeroDeNodes;
   }

   public void setAnterior(DataNode request) {
       this.ipAnterior = request.getIp();
       this.portaAnterior = request.getPort();
    }

  public  boolean ehPrimeiro() {
return ehPrimeiro;
  }
    public void setIpAnterior(String ehPrimeiro){
      this.ipAnterior = ehPrimeiro;
  }
  public String getIpAnterior(){
  return ipAnterior;
  }
  public void setEhPrimeiro(boolean ehPrimeiro){
      this.ehPrimeiro = ehPrimeiro;
  }
  public boolean ehUltimo(){
  return ehUltimo;
  }
public void setEhUltimo(boolean ehPrimeiro){
      this.ehUltimo = ehPrimeiro;
  }

   public void setProximo(DataNode request) {
    this.proximoIp = request.getIp();
    this.proximaPorta = request.getPort();
   }
    private int porta;
    private String proximoIp;
    private int Id,numeroDeNodes,proximaPorta,portaAnterior;
    private BigInteger offsetId,maxId;
    private boolean ehPrimeiro,ehUltimo;
    private String ip,ipAnterior;
    public ChordNode() {
    }
public void setPorta(int porta){
this.porta = porta;
}
public int getPorta(){
return this.porta;
}
public void setProximaPorta(int porta){
this.proximaPorta = porta;
}
public int getProximaPorta(){
return this.proximaPorta;
}
public void setPortaAnterior(int porta){
this.portaAnterior = porta;
}
public int getPortaAnterior(){
return this.portaAnterior;
}
   public String getIp() {
   return this.ip;
   }
    public void setIp(String porta){
this.ip = porta;
}
     public String getIpProximo() {
   return this.proximoIp;
   }
    public void setIpProximo(String porta){
this.proximoIp = porta;
}
    
}
