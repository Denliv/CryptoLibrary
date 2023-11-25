package ciphers;

public class XorCipher implements ICipher {
    private byte[] key;
    private static final int _BLOCK_SIZE = 32;

    public XorCipher(byte[] key) {
        if (key.length != 32)
            throw new IllegalArgumentException("not correct key");
        this.key = key;
    }

    @Override
    public byte[] encrypt(byte[] openText) {
        if (openText.length > _BLOCK_SIZE)
            throw new IllegalArgumentException("size of open text larger block size");
        return xorByteArrays(openText);
    }

    @Override
    public byte[] decrypt(byte[] closedText) {
        if (closedText.length > _BLOCK_SIZE)
            throw new IllegalArgumentException("size of open text larger block size");
        return xorByteArrays(closedText);
    }

    private byte[] xorByteArrays(byte[] input) {
        byte[] output = new byte[input.length];
        for (int i = 0; i < input.length; i++) {
            output[i] = (byte) (input[i] ^ key[i]);
        }
        return output;
    }

    @Override
    public void setKey(byte[] key) {
        if (key.length != 32)
            throw new IllegalArgumentException("not correct key");
        this.key = key;
    }

    @Override
    public byte[] getKey() {
        return key;
    }

    @Override
    public int getBlockSize() {
        return _BLOCK_SIZE;
    }
}
