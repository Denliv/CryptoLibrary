package ciphers;

import key_generators.AesKeyGenerator;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class AesCipherTest {
    @Test(expected = IllegalArgumentException.class)
    public void testAesWithSuperKeyEncryptWith16ByteOpenText1() {
        //Arrange
        byte[] byteArr = new byte[32];
        for (int i = 0; i < 32; i++) {
            byteArr[i] = (byte) i;
        }
        AesCipher cipher = new AesCipher(byteArr);
        byte[] openText = "argadhwtuwrtusrjj".getBytes(StandardCharsets.UTF_8);
        //Act + assert
        cipher.encrypt(openText);
    }

    @Test
    public void testAesWithSuperKeyEncryptWith16ByteOpenText2() throws IOException {
        //Arrange
        byte[] byteArr = new byte[32];
        for (int i = 0; i < 32; i++) {
            byteArr[i] = (byte) i;
        }
        AesCipher cipher = new AesCipher(byteArr);
        byte[] openText = "argad   акjjhj".getBytes(StandardCharsets.UTF_8);
        //Act
        byte[] cipherText1 = cipher.encrypt(openText);
        byte[] cipherText2 = cipher.encrypt(new byte[]{});
        byte[] cipherText3 = cipher.encrypt(new byte[]{});
        byte[] openText1 = cipher.decrypt(cipherText1);
        byte[] openText2 = cipher.decrypt(cipherText2);
        byte[] openText3 = cipher.decrypt(cipherText3);
        byte[] openText4 = cipher.decrypt(new byte[]{});
        byte[] openText5 = cipher.decrypt(new byte[]{});

        ByteArrayOutputStream openTextStream = new ByteArrayOutputStream();
        openTextStream.write(openText1);
        openTextStream.write(openText2);
        openTextStream.write(openText3);
        openTextStream.write(openText4);
        openTextStream.write(openText5);
        //Assert
        Assert.assertEquals(
                "argad   акjjhj",
                openTextStream.toString(StandardCharsets.UTF_8));
    }
    @Test
    public void testAesWithSuperKeyEncryptWithNot16ByteOpenText() throws IOException {
        //Arrange
        byte[] byteArr = new byte[32];
        for (int i = 0; i < 32; i++) {
            byteArr[i] = (byte) i;
        }
        AesCipher cipher = new AesCipher(byteArr);
        byte[] openText = "argad ".getBytes(StandardCharsets.UTF_8);
        //Act
        ByteArrayOutputStream cipherTextStream = new ByteArrayOutputStream();
        cipherTextStream.write(cipher.encrypt(openText));
        cipherTextStream.write(cipher.encrypt(new byte[]{}));

        ByteArrayOutputStream openTextStream = new ByteArrayOutputStream();

        byte[] cipherText = cipherTextStream.toByteArray();
        for (int i = 0; i < cipherText.length; i = i + 16)
        {
            openTextStream.write(cipher.decrypt(Arrays.copyOfRange(cipherText, i, i + 16)));
        }
        openTextStream.write(cipher.decrypt(new byte[]{}));
        //Assert
        Assert.assertEquals(
                "argad ",
                openTextStream.toString(StandardCharsets.UTF_8));
    }
    @Test
    public void testAesWithSuperKeyEncryptWith32ByteOpenText() throws IOException {
        //Arrange
        byte[] byteArr = new byte[32];
        for (int i = 0; i < 32; i++) {
            byteArr[i] = (byte) i;
        }
        AesCipher cipher = new AesCipher(byteArr);
        byte[] oldOpenText1 = "argad   акjjhj".getBytes(StandardCharsets.UTF_8);
        byte[] oldOpenText2 = "argad   акjjh".getBytes(StandardCharsets.UTF_8);
        //Act
        ByteArrayOutputStream cipherTextStream = new ByteArrayOutputStream();
        cipherTextStream.write(cipher.encrypt(oldOpenText1));
        cipherTextStream.write(cipher.encrypt(oldOpenText2));
        cipherTextStream.write(cipher.encrypt(new byte[]{}));

        ByteArrayOutputStream openTextStream = new ByteArrayOutputStream();

        byte[] cipherText = cipherTextStream.toByteArray();
        for (int i = 0; i < cipherText.length; i = i + 16)
        {
            openTextStream.write(cipher.decrypt(Arrays.copyOfRange(cipherText, i, i + 16)));
        }
        openTextStream.write(cipher.decrypt(new byte[]{}));
        //Assert
        Assert.assertEquals(
                "argad   акjjhjargad   акjjh",
                openTextStream.toString(StandardCharsets.UTF_8));
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
