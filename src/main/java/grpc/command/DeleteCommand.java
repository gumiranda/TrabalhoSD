package grpc.command;

import java.math.BigInteger;

import io.atomix.copycat.Command;

public class DeleteCommand implements Command<Boolean> {

    public BigInteger key;
    public byte[] value;

    public DeleteCommand(BigInteger chave) {
        this.key = chave;
    }
}
