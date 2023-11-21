

import ciphers.AesCipher;
import ciphers.XorCipher;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import data_dealer.FileDealer;
import data_dealer.IDataDealer;
import keys.AesKeyGenerator;
import keys.XorKeyGenerator;
import org.junit.Test;

public class CryptographerTest
{
    @Test
    public void serializeCryptographer() throws JsonProcessingException {
        Cryptographer cryptographer = new Cryptographer((IDataDealer) null);
        cryptographer.encrypt(new AesCipher(new AesKeyGenerator().generate()));
        cryptographer.encrypt(new XorCipher(new XorKeyGenerator().generate()));
        System.out.println(new ObjectMapper().writeValueAsString(cryptographer));
        Cryptographer cryptographer2 = new ObjectMapper().readValue(new ObjectMapper().writeValueAsString(cryptographer), Cryptographer.class);
    }
}
