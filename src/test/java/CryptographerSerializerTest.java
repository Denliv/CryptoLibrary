import ciphers.AesCipher;
import ciphers.AthenianCipher;
import ciphers.ICipher;
import ciphers.XorCipher;
import key_generators.AesKeyGenerator;
import key_generators.AthenianKeyGenerator;
import key_generators.XorKeyGenerator;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CryptographerSerializerTest
{
    @Test
    public void test() throws IOException {
        //Arrange
        CryptographerSerializer serializer = new CryptographerSerializer();
        File cryptographerJson = new File("src\\test\\java\\cryptographer.json");
        if (cryptographerJson.exists())
            cryptographerJson.delete();
        Cryptographer cryptographer = new Cryptographer(
                List.of(
                        new AesCipher(new AesKeyGenerator().generate()),
                        new XorCipher(new XorKeyGenerator().generate()),
                        new AthenianCipher(new AthenianKeyGenerator().generate())));
        //Act
        serializer.serialize(cryptographer, "src\\test\\java", "cryptographer");
        Cryptographer desirealizeCryptographer = serializer.deserialize("src\\test\\java", "cryptographer");
        //Assert
        List<ICipher> newCipherList = desirealizeCryptographer.getCipherList();
        Assert.assertEquals(cryptographer.getCipherList().size(), newCipherList.size());
        Assert.assertArrayEquals(cryptographer.getCipherList().get(0).getKey(), newCipherList.get(0).getKey());
        Assert.assertArrayEquals(cryptographer.getCipherList().get(1).getKey(), newCipherList.get(1).getKey());
        Assert.assertArrayEquals(cryptographer.getCipherList().get(2).getKey(), newCipherList.get(2).getKey());
        Assert.assertEquals(cryptographer.getCipherList().get(0).getClass(), newCipherList.get(0).getClass());
        Assert.assertEquals(cryptographer.getCipherList().get(1).getClass(), newCipherList.get(1).getClass());
        Assert.assertEquals(cryptographer.getCipherList().get(2).getClass(), newCipherList.get(2).getClass());
    }
}
