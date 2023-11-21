

import ciphers.AesCipher;
import ciphers.XorCipher;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import data_dealer.IDataDealer;
import keys.AesKeyGenerator;
import keys.XorKeyGenerator;
import org.junit.Assert;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

public class CryptographerTest
{
    @Test
    public void serializeCryptographer() throws JsonProcessingException {
        Cryptographer cryptographer = new Cryptographer((IDataDealer) null);
        cryptographer.encrypt(new AesCipher(new AesKeyGenerator().generate()));
        cryptographer.encrypt(new XorCipher(new XorKeyGenerator().generate()));
        System.out.println(new ObjectMapper().writeValueAsString(cryptographer));
        Cryptographer cryptographer2 = new ObjectMapper().readValue(new ObjectMapper().writeValueAsString(cryptographer), Cryptographer.class);
        var list = cryptographer2.getCipherList();
        Assert.assertArrayEquals(cryptographer.getCipherList().get(0).encrypt("afg".getBytes(StandardCharsets.UTF_8)), cryptographer2.getCipherList().get(0).encrypt("afg".getBytes(StandardCharsets.UTF_8)));
        Assert.assertArrayEquals(cryptographer.getCipherList().get(1).encrypt("afg".getBytes(StandardCharsets.UTF_8)), cryptographer2.getCipherList().get(1).encrypt("afg".getBytes(StandardCharsets.UTF_8)));
        Assert.assertEquals(cryptographer.getCipherList().size(), cryptographer2.getCipherList().size());
    }
}
