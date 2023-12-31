package data_dealer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class FileDealer implements IDataDealer {
    private long textSize;
    private int blockSize;
    private RandomAccessFile openText;
    private RandomAccessFile closedText;

    public FileDealer(File openText, File closedText) throws IOException {
        if (!openText.exists()) {
            throw new IllegalArgumentException("File with " + openText.getName() + " do not exists in " + openText.getParent());
        }
        if (!closedText.getAbsolutePath().equals(openText.getAbsolutePath()) && closedText.exists()) {
            throw new IllegalArgumentException("File with " + closedText.getName() + " already exists in " + closedText.getParent());
        }
        if (!closedText.getAbsolutePath().equals(openText.getAbsolutePath()) && closedText.length() == 0) {
            Files.copy(openText.toPath(), closedText.toPath(), StandardCopyOption.COPY_ATTRIBUTES);
        }
        this.closedText = new RandomAccessFile(closedText, "rw");
        this.openText = new RandomAccessFile(closedText, "r");
        this.textSize = this.closedText.length();
    }

    public FileDealer(String openText, String closedText) throws IOException {
        this(new File(openText), new File(closedText));
    }

    @Override
    public void setBlockSize(int size) {
        this.blockSize = size;
    }

    @Override
    public void toFirst() throws IOException {

        if (openText.length() != 0 && openText.getFilePointer() != 0) this.openText.seek(0L);
        if (closedText.length() != 0 && closedText.getFilePointer() != 0)  this.closedText.seek(0L);
        textSize = openText.length();
    }

    @Override
    public byte[] readBlock() throws IOException {
        var bytes = new byte[(int) Math.min(blockSize, openText.length() - openText.getFilePointer())];
        openText.read(bytes);
        return bytes;
    }

    @Override
    public void writeBlock(byte[] in) throws IOException {
        if (in.length > blockSize) throw new IllegalArgumentException("byte[] size should be <= block size\n");
        closedText.write(in);
        if (!hasNext()) {
            closedText.getChannel().truncate(closedText.getFilePointer());
        }
    }

    @Override
    public boolean hasNext() throws IOException {
        return openText.length() > 0 && openText.getFilePointer() < textSize;
    }

    @Override
    public void close() throws IOException {
        if (openText != null)
            openText.close();
        if (closedText != null)
            closedText.close();
    }
}
