package grpc.command;

import java.math.BigInteger;

import io.atomix.copycat.Command;

public class CreateCommand implements Command<Boolean> {

    public BigInteger key;
    public byte[] value;

    public CreateCommand(BigInteger chave, String valor) {
        this.key = chave;
        this.value = valor.getBytes();
    }
}
