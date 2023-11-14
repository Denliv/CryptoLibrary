package data_dealer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class FileDealer implements IDataDealer {
    private long blockSize;
    private RandomAccessFile openText;
    private RandomAccessFile closedText;

    public FileDealer(File openText, File closedText) throws FileNotFoundException {
        this.openText = new RandomAccessFile(openText, "r");
        this.closedText = new RandomAccessFile(closedText, "w");
    }

    public FileDealer(String openText, String closedText) throws FileNotFoundException {
        this.openText = new RandomAccessFile(openText, "r");
        this.closedText = new RandomAccessFile(closedText, "w");
    }

    @Override
    public void setBlockSize(long size) {
        this.blockSize = size;
    }

    @Override
    public void toFirst() throws IOException {
        if (openText == null || openText.length() == 0) return;
        this.openText.seek(0L);
    }

    @Override
    public byte[] readBlock() {
        return new byte[0];
    }

    @Override
    public void writeBlock(byte[] in) {

    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public void close() {

    }
}
