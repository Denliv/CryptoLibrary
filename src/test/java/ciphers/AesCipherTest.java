package ciphers;

import org.junit.Assert;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
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
        AesCipher cipher = new AesCipher(byteArr);
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
    @Test
    public void testAesWithSuperKeyEncryptWith16ByteOpenText1()
    {
        //Arrange
        byte[] byteArr = new byte[32];
        for(int i = 0; i < 32; i++)
        {
            byteArr[i] = (byte) i;
        }
        AesCipher cipher = new AesCipher(byteArr);
        byte[] openText = "argadhwtuwrtusrjфпварорфывафывааhswrtueruydtyidkdhgjdfhj".getBytes(StandardCharsets.UTF_8);
        //Act
        byte[] cipherText1 = cipher.encrypt(openText);
        byte[] openText1 = cipher.decrypt(Arrays.copyOfRange(cipherText1, 0, 16));
        byte[] openText2 = cipher.decrypt(Arrays.copyOfRange(cipherText1, 16, 32));
        byte[] openText3 = cipher.decrypt(Arrays.copyOfRange(cipherText1, 32, 48));
        byte[] openText4 = cipher.decrypt(Arrays.copyOfRange(cipherText1, 48, 64));
        System.out.println(openText1.length);
        System.out.println(openText2.length);
        System.out.println(openText3.length);
        System.out.println(openText4.length);
    }
}
