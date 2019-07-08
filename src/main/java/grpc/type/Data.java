package grpc.type;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Data implements Serializable {

    public BigInteger key;
    public byte[] value;

    public Data(BigInteger chave, byte[] valor) {
        this.key = chave;
        this.value = valor;
    }
    @Override
    public String toString() {

        return "Dado com chave " + key + ": " + new String(value);

    }

}
