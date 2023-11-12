package ciphers;

import keys.AesKeyGenerator;
import keys.AthenianKeyGenerator;
import keys.IKeyGenerator;
import org.junit.Assert;
import org.junit.Test;

import javax.crypto.KeyGenerator;
import java.nio.charset.StandardCharsets;

public class AthenianKeyGeneratorTest
{
    @Test
    public void test1()
    {
        //Arrange
        byte[] openText = "kHrkn knhavug Bqqzon sdfhgshDDGHDD   5558абвГД".getBytes(StandardCharsets.UTF_8);
        IKeyGenerator generator = new AthenianKeyGenerator();
        //Act + assert
        for (int i = 0; i < 10000; i++)
        {
            AthenianCipher cipher = new AthenianCipher(generator.generate());
            byte[] newClosedText = cipher.encrypt(openText);
            byte[] newOpenText = cipher.decrypt(newClosedText);
            Assert.assertArrayEquals(openText, newOpenText);
        }
    }
}
