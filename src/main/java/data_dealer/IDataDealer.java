package data_dealer;

import java.io.Closeable;
import java.io.IOException;

public interface IDataDealer extends AutoCloseable, Closeable {
    void setBlockSize(int size);

    void toFirst() throws IOException;

    byte[] readBlock() throws IOException;

    void writeBlock(byte[] in) throws IOException;

    boolean hasNext() throws IOException;

     void close() throws IOException;
}
