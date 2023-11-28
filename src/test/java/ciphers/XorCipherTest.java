package ciphers;

import key_generators.XorKeyGenerator;
import org.junit.Assert;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class XorCipherTest {
    @Test
    public void test1() {
        for (int i = 0; i < 10_000; i++) {
            //Arrange
            byte[] byteArr = new XorKeyGenerator().generate();
            XorCipher cipher = new XorCipher(byteArr);
            byte[] openText = "argadhwtuwrtusrjjusfoiUDFGHOA".getBytes(StandardCharsets.UTF_8);
            //Act
            byte[] cipherText = cipher.encrypt(openText);
            //Assert

            Assert.assertFalse(
                    Arrays.equals(cipherText, openText));

            Assert.assertArrayEquals(
                    openText,
                    cipher.decrypt(cipherText));
        }
    }
}
