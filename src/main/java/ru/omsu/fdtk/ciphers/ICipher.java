package ru.omsu.fdtk.ciphers;

import ru.omsu.fdtk.keys.Key;

public interface ICipher
{
    byte[] encrypt(byte[] openText);
    byte[] decrypt(byte[] closedText);
    void setKey(Key key);
    long getBlockSize();
}
