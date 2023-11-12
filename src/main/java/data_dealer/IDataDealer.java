package data_dealer;

public interface IDataDealer {
    void setBlockSize(long size);

    void toFirst();

    byte[] readBlock();

    void writeBlock(byte[] in);

    boolean hasNext();

    void clean();

    void cleanRest();
}
