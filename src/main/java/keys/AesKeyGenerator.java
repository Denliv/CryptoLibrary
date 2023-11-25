package keys;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class AesKeyGenerator implements IKeyGenerator
{
    private final SecureRandom secureRandom;
    private final MessageDigest messageDigest;

    public AesKeyGenerator()
    {
        secureRandom = new SecureRandom();
        try
        {
            messageDigest = MessageDigest.getInstance("MD5");
        }
        catch (NoSuchAlgorithmException err)
        {
            throw new IllegalStateException("unknown error. contact the developer", err);
        }
    }

    @Override
    public byte[] generate()
    {
        byte[] key = new byte[32];
        secureRandom.nextBytes(key);
        return key;
    }
    public byte[] create(String password1, String password2)
    {
        byte[] aesKey = messageDigest.digest(password1.getBytes(StandardCharsets.UTF_8));
        byte[] ivParameterSpec = messageDigest.digest(password2.getBytes(StandardCharsets.UTF_8));
        byte[] key = new byte[32];
        System.arraycopy(aesKey, 0, key, 0, 16);
        System.arraycopy(ivParameterSpec, 0, key, 16, 16);
        return key;
    }
}
