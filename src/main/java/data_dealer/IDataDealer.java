package data_dealer;

import java.io.Closeable;
import java.io.IOException;

public interface IDataDealer extends AutoCloseable, Closeable {
    void setBlockSize(long size);

    void toFirst() throws IOException;

    byte[] readBlock();

    void writeBlock(byte[] in);

    boolean hasNext();

    void close();
}
