package ciphers;

import key_generators.AesKeyGenerator;
import org.junit.Assert;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class AesCipherTest {
    @Test(expected = IllegalArgumentException.class)
    public void testAesWithSuperKeyEncryptWithNot16ByteOpenText() {
        //Arrange
        byte[] byteArr = new byte[32];
        for (int i = 0; i < 32; i++) {
            byteArr[i] = (byte) i;
        }
        AesCipher cipher = new AesCipher(byteArr);
        byte[] openText = "argadhwtuwrtusrjj".getBytes(StandardCharsets.UTF_8);
        //Act + assert
        byte[] cipherText = cipher.encrypt(openText);
    }

    @Test
    public void testAesWithSuperKeyEncryptWith16ByteOpenText() {
        //Arrange
        byte[] byteArr = new byte[32];
        for (int i = 0; i < 32; i++) {
            byteArr[i] = (byte) i;
        }
        AesCipher cipher = new AesCipher(byteArr);
        byte[] openText = "argadhwtuwrtusrj".getBytes(StandardCharsets.UTF_8);
        //Act
        byte[] cipherText1 = cipher.encrypt(openText);
        byte[] cipherText2 = cipher.encrypt(new byte[]{});
        byte[] openText1 = cipher.decrypt(cipherText1);
        byte[] openText2 = cipher.decrypt(cipherText2);
        byte[] openText3 = cipher.decrypt(new byte[]{});
        //Assert
        Assert.assertEquals(
                "argadhwtuwrtusrj",
                new String(openText2, StandardCharsets.UTF_8));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAesWithSuperRandomKeyEncryptWithNot16ByteOpenText()
    {
        for (int i = 0; i < 10_000; i++) {
            //Arrange
            byte[] byteArr = new AesKeyGenerator().generate();
            AesCipher cipher = new AesCipher(byteArr);
            byte[] openText = "argadhwtuwrtusrjjфапыфвпрвыаорвровпроasrtgetysrt".getBytes(StandardCharsets.UTF_8);
            //Act + Assert
            byte[] cipherText = cipher.encrypt(openText);
        }
    }
}
