package data_dealer;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import static java.nio.file.StandardOpenOption.READ;
import static java.nio.file.StandardOpenOption.WRITE;

public class FileChannelDealer implements IDataDealer {
    private long textSize;
    private int blockSize;
    private FileChannel openText;
    private FileChannel closedText;

    public FileChannelDealer(File openText, File closedText) throws IOException {
        if (!openText.exists()) {
            throw new IllegalArgumentException("File with " + openText.getName() + " do not exists in " + openText.getParent());
        }
        if (!closedText.getAbsolutePath().equals(openText.getAbsolutePath()) && closedText.exists()) {
            throw new IllegalArgumentException("File with " + closedText.getName() + " already exists in " + closedText.getParent());
        }
        if (!closedText.getAbsolutePath().equals(openText.getAbsolutePath()) && closedText.length() == 0) {
            Files.copy(openText.toPath(), closedText.toPath(), StandardCopyOption.COPY_ATTRIBUTES);
        }
        this.closedText = FileChannel.open(closedText.toPath(), READ, WRITE);
        this.openText = FileChannel.open(closedText.toPath(), READ);
        this.textSize = this.closedText.size();
    }

    public FileChannelDealer(String openText, String closedText) throws IOException {
        this(new File(openText), new File(closedText));
    }

    @Override
    public void setBlockSize(int size) {
        this.blockSize = size;
    }

    @Override
    public void toFirst() throws IOException {

        if (openText.size() != 0 && openText.position() != 0) this.openText.position(0L);
        if (closedText.size() != 0 && closedText.position() != 0) this.closedText.position(0L);
        textSize = openText.size();
    }

    @Override
    public byte[] readBlock() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(blockSize);
        openText.read(buffer);
        var bytes = new byte[buffer.position()];
        for (int i = 0; i < bytes.length; ++i) {
            bytes[i] = buffer.get(i);
        }
        return bytes;
    }

    @Override
    public void writeBlock(byte[] in) throws IOException {
        if (in.length > blockSize) throw new IllegalArgumentException("byte[] size should be <= block size\n");
        closedText.write(ByteBuffer.wrap(in));
        if (!hasNext()) {
            closedText.truncate(closedText.position());
        }
    }

    @Override
    public boolean hasNext() throws IOException {
        return openText.size() > 0 && openText.position() < textSize;
    }

    @Override
    public void close() throws IOException {
        if (openText != null) openText.close();
        if (closedText != null) closedText.close();
    }
}
