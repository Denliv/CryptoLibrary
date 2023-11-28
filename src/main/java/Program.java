import ciphers.AesCipher;
import ciphers.AthenianCipher;
import ciphers.ICipher;
import ciphers.XorCipher;
import data_dealer.FileDealer;
import data_dealer.IDataDealer;
import key_generators.AesKeyGenerator;
import key_generators.AthenianKeyGenerator;
import key_generators.XorKeyGenerator;

import java.io.File;
import java.io.IOException;

public class Program {
    public static void main(String[] args) throws IOException {
        //Действия отправителя
        byte[] keyAthenian = new AthenianKeyGenerator().generate();
        byte[] keyXor = new XorKeyGenerator().generate();
        byte[] keyAes1 = new AesKeyGenerator().generate();
        byte[] keyAes2 = new AesKeyGenerator().generate();
        ICipher cipherAthenian = new AthenianCipher(keyAthenian);
        ICipher cipherXor = new XorCipher(keyXor);
        ICipher cipherAes1 = new AesCipher(keyAes1);
        ICipher cipherAes2 = new AesCipher(keyAes2);

        try (IDataDealer dealer = new FileDealer(
                new File("C:\\Users\\User\\Downloads\\demo.zip"),
                new File("C:\\Users\\User\\Downloads\\1\\demo_encrypted.zip"))
        ) {
            Cryptographer cryptographer = new Cryptographer(dealer);
            cryptographer.encrypt(cipherAes1).encrypt(cipherXor).encrypt(cipherAthenian).encrypt(cipherAes2);
            cryptographer.terminalEncrypt();
            CryptographerSerializer cs = new CryptographerSerializer();
            cs.serialize(cryptographer, "C:\\Users\\User\\Downloads\\1", "key");
        }

        //Действия получателя
        CryptographerSerializer cs = new CryptographerSerializer();
        Cryptographer cryptographer = cs.deserialize("C:\\Users\\User\\Downloads\\1", "key");
        try (IDataDealer dealer = new FileDealer(
                new File("C:\\Users\\User\\Downloads\\1\\demo_encrypted.zip"),
                new File("C:\\Users\\User\\Downloads\\1\\demo_decrypted.zip"))
        ) {
            cryptographer.setDataDealer(dealer);
            cryptographer.terminalDecrypt();
        }
    }
}
