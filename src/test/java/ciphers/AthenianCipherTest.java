package ciphers;

import org.junit.Assert;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

public class AthenianCipherTest {
    @Test
    public void test1() {
        //Arrange
        byte[] openText = "kHrkn knhavug Bqqzon sdfhgshDDGHDD   5558абвГД".getBytes(StandardCharsets.UTF_8);
        byte[] closedText = "fWafo fowbmjt Exxyro dkqwtdwKKTWKK   5558абвГД".getBytes(StandardCharsets.UTF_8);
        AthenianCipher cipher = new AthenianCipher(new byte[]{3, 1});
        //Act
        byte[] newClosedText = cipher.encrypt(openText);
        byte[] newOpenText = cipher.decrypt(closedText);
        byte[] emptyArrayEncrypt = cipher.encrypt(new byte[0]);
        byte[] emptyArrayDecrypt = cipher.decrypt(new byte[0]);
        //Assert
        Assert.assertArrayEquals(newOpenText, openText);
        Assert.assertArrayEquals(newClosedText, closedText);
        Assert.assertArrayEquals(new byte[0], emptyArrayEncrypt);
        Assert.assertArrayEquals(new byte[0], emptyArrayDecrypt);
    }
}
