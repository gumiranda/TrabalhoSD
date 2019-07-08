package grpc.command;

import java.math.BigInteger;

import grpc.type.Data;
import io.atomix.copycat.Query;

public class ReadQuery implements Query<Data> {

    public BigInteger key;

    public ReadQuery(BigInteger key) {
        this.key = key;
    }
}
