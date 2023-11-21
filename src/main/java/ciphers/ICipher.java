package ciphers;

public interface ICipher
{
    byte[] encrypt(byte[] openText);
    byte[] decrypt(byte[] closedText);
    void setKey(byte[] key);
    byte[] getKey();
    long getBlockSize();
}
