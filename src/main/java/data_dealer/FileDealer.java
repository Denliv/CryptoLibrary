package data_dealer;

import java.io.*;

public class FileDealer implements IDataDealer {
    private int blockSize;
    private RandomAccessFile openText;
    private RandomAccessFile closedText;

    public FileDealer(File openText, File closedText) throws FileNotFoundException {
        if (!openText.exists()) {
            throw new IllegalArgumentException("File with " + openText.getName() +  "do not exists in " + openText.getParent());
        }
        if (!closedText.getAbsolutePath().equals(openText.getAbsolutePath()) && closedText.exists()) {
            throw new IllegalArgumentException("File with " + closedText.getName() +  "already exists in " + closedText.getParent());
        }
        this.openText = new RandomAccessFile(openText, "r");
        this.closedText = new RandomAccessFile(closedText, "rw");
        if (!closedText.getAbsolutePath().equals(openText.getAbsolutePath()) && closedText.length() == 0) {
            try (FileInputStream input = new FileInputStream(openText);
                 FileOutputStream output = new FileOutputStream(closedText)) {
                while (input.available() > 0) {
                    int data = input.read();
                    output.write(data);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public FileDealer(String openText, String closedText) throws FileNotFoundException {
        this(new File(openText), new File(closedText));
    }

    @Override
    public void setBlockSize(int size) {
        this.blockSize = size;
    }

    @Override
    public void toFirst() throws IOException {
        if (openText.length() == 0 || openText.getFilePointer() == 0) return;
        this.openText.seek(0L);
        if (closedText.length() == 0 || closedText.getFilePointer() == 0) return;
        this.closedText.seek(0L);
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
    }

    @Override
    public boolean hasNext() throws IOException {
        return openText.length() > 0 && openText.getFilePointer() < openText.length();
    }

    @Override
    public void close() throws IOException {
        if (openText != null)
            openText.close();
        if (closedText != null)
            closedText.close();
    }
}
