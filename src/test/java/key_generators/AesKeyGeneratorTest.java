package key_generators;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class AesKeyGeneratorTest {
    @Test
    public void generateKeysNotEqualsTest() {
        //Arrange
        IKeyGenerator generator = new AesKeyGenerator();
        byte[] key1 = generator.generate();
        byte[] key2 = generator.generate();
        //Act + assert
        Assert.assertEquals(key1.length, key2.length);
        Assert.assertEquals(32, key1.length);
        Assert.assertFalse(Arrays.equals(key1, key2));
    }

    @Test
    public void generateKeyWithStringTest() {
        //Arrange
        AesKeyGenerator generator = new AesKeyGenerator();
        byte[] key1 = generator.create("KEY", "KEY");
        byte[] key3 = generator.create("DIFFERENT_KEY", "KEY2");
        byte[] key2 = generator.create("KEY", "KEY");

        byte[] ivParameterSpecKey1 = Arrays.copyOfRange(key1, 16, 32);
        byte[] ivParameterSpecKey3 = Arrays.copyOfRange(key3, 16, 32);
        //Act + assert
        Assert.assertArrayEquals(key1, key2);
        Assert.assertEquals(32, key1.length);
        Assert.assertEquals(32, key3.length);
        Assert.assertNotEquals(ivParameterSpecKey1, ivParameterSpecKey3);
    }
}
