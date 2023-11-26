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
        byte[] keyAes = new AesKeyGenerator().generate();
        ICipher cipherAthenian = new AthenianCipher(keyAthenian);
        ICipher cipherXor = new XorCipher(keyXor);
        ICipher cipherAes = new AesCipher(keyAes);

        try (IDataDealer dealer = new FileDealer(
                new File("C:\\Users\\Даниил\\Desktop\\Задание 1.pdf"),
                new File("C:\\Users\\Даниил\\Desktop\\Задание 1_encrypted.pdf"))
        ) {
            Cryptographer cryptographer = new Cryptographer(dealer);
            cryptographer.encrypt(cipherAes).encrypt(cipherXor);
            cryptographer.terminalEncrypt();
            CryptographerSerializer cs = new CryptographerSerializer();
            cs.serialize(cryptographer, new File("C:\\Users\\Даниил\\Desktop\\"), "Задание 1");
        }

        //Действия получателя
        CryptographerSerializer cs = new CryptographerSerializer();
        Cryptographer cryptographer = cs.deserialize(new File("C:\\Users\\Даниил\\Desktop\\"), "Задание 1");
        try (IDataDealer dealer = new FileDealer(
                new File("C:\\Users\\Даниил\\Desktop\\Задание 1_encrypted.pdf"),
                new File("C:\\Users\\Даниил\\Desktop\\Задание 1_decrypted.pdf"))
        ) {
            cryptographer.setDataDealer(dealer);
            cryptographer.terminalDecrypt();
        }
    }
}
