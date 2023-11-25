package ciphers;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class AesCipher implements ICipher {
    private byte[] key;
    private java.security.Key aesKey;
    private IvParameterSpec ivParameterSpec;
    private final Cipher aesCipher;
    private boolean encryptMode;
    private boolean flag;
    private static final int _BLOCK_SIZE = 16;

    public AesCipher(byte[] key) {
        this.key = key;
        aesKey = new SecretKeySpec(Arrays.copyOfRange(key, 0, 16), "AES");
        ivParameterSpec = new IvParameterSpec(Arrays.copyOfRange(key, 16, 32));
        flag = false;
        try {
            aesCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        } catch (NoSuchPaddingException | NoSuchAlgorithmException err) {
            throw new IllegalStateException("unknown error. contact the developer", err);
        }
        try {
            aesCipher.init(Cipher.DECRYPT_MODE, aesKey, ivParameterSpec);
            encryptMode = false;
        } catch (InvalidKeyException | InvalidAlgorithmParameterException err) {
            throw new IllegalArgumentException("incorrect key format for aes cipher", err);
        }
    }

    public byte[] encrypt(byte[] openText) {
        if (!encryptMode) {
            try {
                aesCipher.init(Cipher.ENCRYPT_MODE, aesKey, ivParameterSpec);
                encryptMode = true;
                flag = false;
            } catch (InvalidKeyException | InvalidAlgorithmParameterException err) {
                throw new IllegalStateException("unknown error. contact the developer", err);
            }

        }
        return mapTextToTextByAes(openText);
    }

    public byte[] decrypt(byte[] closedText) {
        if (encryptMode) {
            try {
                aesCipher.init(Cipher.DECRYPT_MODE, aesKey, ivParameterSpec);
                encryptMode = false;
                flag = false;
            } catch (InvalidKeyException | InvalidAlgorithmParameterException err) {
                throw new IllegalStateException("unknown error. contact the developer", err);
            }

        }
        return mapTextToTextByAes(closedText);
    }

    private byte[] mapTextToTextByAes(byte[] text) {
        byte[] res;
        try
        {
            if (text.length > _BLOCK_SIZE)
            {
                throw new IllegalArgumentException("size of open text larger block size");
            }
            if (text.length != 0)
            {
                if (flag)
                    flag = false;
                res = aesCipher.update(text);
            }
            else
            {
                if (flag)
                    res = new byte[0];
                else
                {
                    flag = true;
                    res = aesCipher.doFinal();
                }
            }
        }
        catch (IllegalBlockSizeException err)
        {
            throw new IllegalArgumentException("block size not correct", err);
        }
        catch (BadPaddingException err)
        {
            throw new IllegalStateException("badPadding", err);
        }
        return res;
    }

    public void setKey(byte[] key) {
        this.key = key;
        aesKey = new SecretKeySpec(Arrays.copyOfRange(key, 0, 16), "AES");
        ivParameterSpec = new IvParameterSpec(Arrays.copyOfRange(key, 16, 32));
        flag = false;
        try {
            aesCipher.init(Cipher.DECRYPT_MODE, aesKey, ivParameterSpec);
            encryptMode = false;
        } catch (InvalidKeyException | InvalidAlgorithmParameterException err) {
            throw new IllegalArgumentException("incorrect key format for aes cipher", err);
        }
    }

    public int getBlockSize() {
        return _BLOCK_SIZE;
    }

    @Override
    public byte[] getKey() {
        return key;
    }

}
