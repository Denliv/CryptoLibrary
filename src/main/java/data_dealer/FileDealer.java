package data_dealer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class FileDealer implements IDataDealer {
    private int blockSize;
    private final RandomAccessFile openText;
    private final RandomAccessFile closedText;

    public FileDealer(File openText, File closedText) throws FileNotFoundException {
        this.openText = new RandomAccessFile(openText, "r");
        this.closedText = new RandomAccessFile(closedText, "rw");
    }

    public FileDealer(String openText, String closedText) throws FileNotFoundException {
        this.openText = new RandomAccessFile(openText, "r");
        this.closedText = new RandomAccessFile(closedText, "rw");
    }

    @Override
    public void setBlockSize(int size) {
        this.blockSize = size;
    }

    @Override
    public void toFirst() throws IOException {
        if (openText.length() == 0 || openText.getFilePointer() == 0) return;
        this.openText.seek(0L);
    }

    @Override
    public byte[] readBlock() throws IOException {
        //Для уменьшения массива, при недостаточном кол-ве данных
        //var bytes = new byte[(int) Math.min(blockSize, openText.length() - openText.getFilePointer())];
        var bytes = new byte[blockSize];
        openText.read(bytes);
        return bytes;
    }

    @Override
    public void writeBlock(byte[] in) throws IOException {
        if (in.length > blockSize) throw new IllegalArgumentException("byte[] size should be <= block size\n");
        closedText.write(in);
    }

    @Override
    public boolean hasNext() throws IOException {
        return openText.length() > 0 && openText.getFilePointer() < openText.length();
    }

    @Override
    public void close() throws IOException {
        openText.close();
        closedText.close();
    }
}
