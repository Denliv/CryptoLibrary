package ru.omsu.fdtk.ciphers;

import ru.omsu.fdtk.keys.Key;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class AesCipher  implements ICipher
{
    private Key key;
    private java.security.Key aesKey;
    private IvParameterSpec ivParameterSpec;
    private Cipher aesCipher;
    private boolean encryptMode;

    public AesCipher(Key key)
    {
        this.key = key;
        aesKey = new SecretKeySpec(Arrays.copyOfRange(key.getByteArr(), 0, 16), "AES");
        ivParameterSpec = new IvParameterSpec(Arrays.copyOfRange(key.getByteArr(), 16, 32));
        try
        {
            aesCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        }
        catch (NoSuchPaddingException | NoSuchAlgorithmException ignored)
        {
            throw new IllegalStateException("unknown error. contact the developer");
        }
        try {
            aesCipher.init(Cipher.DECRYPT_MODE, aesKey, ivParameterSpec);
            encryptMode = false;
        }
        catch (InvalidKeyException | InvalidAlgorithmParameterException err)
        {
            throw new IllegalArgumentException("incorrect key format for aes cipher");
        }
    }

    public void encrypt(InputStream inputStream, OutputStream outputStream)
    {

    }

    public void decrypt(InputStream inputStream, OutputStream outputStream)
    {

    }

    public byte[] encrypt(byte[] openText)
    {
        if (!encryptMode)
        {
            try {
                aesCipher.init(Cipher.ENCRYPT_MODE, aesKey, ivParameterSpec);
                encryptMode = true;
            }
            catch (InvalidKeyException | InvalidAlgorithmParameterException ignored)
            {
                throw new IllegalStateException("unknown error. contact the developer");
            }

        }
        return mapTextToTextByAes(openText);
    }

    public byte[] decrypt(byte[] closedText)
    {
        if (encryptMode)
        {
            try {
                aesCipher.init(Cipher.DECRYPT_MODE, aesKey, ivParameterSpec);
                encryptMode = false;
            }
            catch (InvalidKeyException | InvalidAlgorithmParameterException ignored)
            {
                throw new IllegalStateException("unknown error. contact the developer");
            }

        }
        return mapTextToTextByAes(closedText);
    }
    private byte[] mapTextToTextByAes(byte[] text)
    {
        byte[] res;
        try {
            if(text.length == 16)
                res = aesCipher.update(text);
            else
                res = aesCipher.doFinal(text);
        }
        catch (IllegalBlockSizeException err)
        {
            throw new IllegalArgumentException("block size not correct");
        }
        catch (BadPaddingException err)
        {
            throw new IllegalStateException("unknown error. contact the developer");
        }
        return res;
    }
    public void setKey(Key key)
    {
        this.key = key;
        aesKey = new SecretKeySpec(Arrays.copyOfRange(key.getByteArr(), 0, 16), "AES");
        ivParameterSpec = new IvParameterSpec(Arrays.copyOfRange(key.getByteArr(), 16, 32));
        try
        {
            aesCipher.init(Cipher.DECRYPT_MODE, aesKey, ivParameterSpec);
            encryptMode = false;
        }
        catch (InvalidKeyException | InvalidAlgorithmParameterException err)
        {
            throw new IllegalArgumentException("incorrect key format for aes cipher");
        }
    }

    public long getBlockSize()
    {
        return 128;
    }
}