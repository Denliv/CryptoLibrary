package keys;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class XorKeyGenerator implements IKeyGenerator
{
    private final SecureRandom secureRandom;

    public XorKeyGenerator()
    {
        secureRandom = new SecureRandom();
    }
    @Override
    public byte[] generate()
    {
        byte[] key = new byte[32];
        secureRandom.nextBytes(key);
        return key;
    }

}
