package key_generators;

import ciphers.AthenianCipher;
import org.junit.Assert;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

public class AthenianKeyGeneratorTest {
    @Test
    public void test1() {
        //Arrange
        byte[] openText = "kHrkn knhavug Bqqzon sdfhgshDDGHDD   5558абвГД".getBytes(StandardCharsets.UTF_8);
        IKeyGenerator generator = new AthenianKeyGenerator();
        //Act + assert
        for (int i = 0; i < 10000; i++) {
            AthenianCipher cipher = new AthenianCipher(generator.generate());
            byte[] newClosedText = cipher.encrypt(openText);
            byte[] newOpenText = cipher.decrypt(newClosedText);
            Assert.assertArrayEquals(openText, newOpenText);
        }
    }
}
