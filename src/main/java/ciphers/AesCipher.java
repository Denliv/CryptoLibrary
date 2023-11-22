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

    public AesCipher(byte[] key) {
        this.key = key;
        aesKey = new SecretKeySpec(Arrays.copyOfRange(key, 0, 16), "AES");
        ivParameterSpec = new IvParameterSpec(Arrays.copyOfRange(key, 16, 32));
        flag = false;
        try {
            aesCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        } catch (NoSuchPaddingException | NoSuchAlgorithmException ignored) {
            throw new IllegalStateException("unknown error. contact the developer");
        }
        try {
            aesCipher.init(Cipher.DECRYPT_MODE, aesKey, ivParameterSpec);
            encryptMode = false;
        } catch (InvalidKeyException | InvalidAlgorithmParameterException err) {
            throw new IllegalArgumentException("incorrect key format for aes cipher");
        }
    }

    public byte[] encrypt(byte[] openText) {
        if (!encryptMode) {
            try {
                aesCipher.init(Cipher.ENCRYPT_MODE, aesKey, ivParameterSpec);
                encryptMode = true;
                flag = false;
            } catch (InvalidKeyException | InvalidAlgorithmParameterException ignored) {
                throw new IllegalStateException("unknown error. contact the developer");
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
            } catch (InvalidKeyException | InvalidAlgorithmParameterException ignored) {
                throw new IllegalStateException("unknown error. contact the developer");
            }

        }
        return mapTextToTextByAes(closedText);
    }

    private byte[] mapTextToTextByAes(byte[] text) {
        byte[] res;
        try {
            if (text.length == 16)
                res = aesCipher.update(text);
            else if (text.length == 0)
            {
                if (flag)
                    res = new byte[0];
                else
                {
                    flag = true;
                    res = aesCipher.doFinal(text);
                }
            }
            else
                res = aesCipher.doFinal(text);
        } catch (IllegalBlockSizeException err) {
            throw new IllegalArgumentException("block size not correct");
        } catch (BadPaddingException err) {
            throw new IllegalStateException("unknown error. contact the developer");
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
            throw new IllegalArgumentException("incorrect key format for aes cipher");
        }
    }

    public int getBlockSize() {
        return 16;
    }

    @Override
    public byte[] getKey() {
        return key;
    }

}
