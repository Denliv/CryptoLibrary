package ru.omsu.fdtk;

import org.junit.Assert;
import org.junit.Test;
import ru.omsu.fdtk.ciphers.AesCipher;
import ru.omsu.fdtk.keys.IKeyFactory;

import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.util.Arrays;

public class AesCipherTest
{
    @Test
    public void testAesWithSuperKeyEncryptWithNot16ByteOpenText()
    {
        //Arrange
        byte[] byteArr = new byte[32];
        for(int i = 0; i < 32; i++)
        {
            byteArr[i] = (byte) i;
        }
        AesCipher cipher = new AesCipher(IKeyFactory.create(byteArr));
        byte[] openText = "argadhwtuwrtusrjj".getBytes(StandardCharsets.UTF_8);
        //Act
        byte[] cipherText = cipher.encrypt(openText);
        //Assert

        Assert.assertFalse(
                Arrays.equals(cipherText, openText));

        Assert.assertArrayEquals(
                openText,
                cipher.decrypt(cipherText));
    }
    @Test
    public void testAesWithSuperKeyEncryptWith16ByteOpenText()
    {
        //Arrange
        byte[] byteArr = new byte[32];
        for(int i = 0; i < 32; i++)
        {
            byteArr[i] = (byte) i;
        }
        AesCipher cipher = new AesCipher(IKeyFactory.create(byteArr));
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
}