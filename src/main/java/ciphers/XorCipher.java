package ciphers;

public class XorCipher implements ICipher
{
    private byte[] key;

    public XorCipher(byte[] key)
    {
        if (key.length != 32)
            throw new IllegalArgumentException("not correct key");
        this.key = key;
    }

    @Override
    public byte[] encrypt(byte[] openText) {
        return xorByteArrays(openText);
    }

    @Override
    public byte[] decrypt(byte[] closedText) {
        return xorByteArrays(closedText);
    }
    private byte[] xorByteArrays(byte[] input)
    {
        byte[] output = new byte[input.length];
        for (int i = 0; i < input.length; i++)
        {
            output[i] = (byte) (input[i] ^ key[i]);
        }
        return output;
    }

    @Override
    public void setKey(byte[] key)
    {
        if (key.length != 32)
            throw new IllegalArgumentException("not correct key");
        this.key = key;
    }

    @Override
    public long getBlockSize() {
        return 32;
    }
}
