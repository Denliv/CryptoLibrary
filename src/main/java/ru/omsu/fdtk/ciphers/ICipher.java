package ru.omsu.fdtk.ciphers;

import ru.omsu.fdtk.keys.Key;

import java.io.InputStream;
import java.io.OutputStream;

public interface ICipher
{
    void encrypt(InputStream inputStream, OutputStream outputStream);
    void decrypt(InputStream inputStream, OutputStream outputStream);
    byte[] encrypt(byte[] openText);
    byte[] decrypt(byte[] closedText);
    void setKey(Key key);
    long getBlockSize();
}
