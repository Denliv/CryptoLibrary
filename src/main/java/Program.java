import ciphers.AthenianCipher;
import ciphers.ICipher;
import data_dealer.FileDealer;
import data_dealer.IDataDealer;
import key_generators.AthenianKeyGenerator;

import java.io.File;
import java.io.IOException;

public class Program {
    public static void main(String[] args) throws IOException {
        byte[] key1 = new AthenianKeyGenerator().generate();
        ICipher cipher1 = new AthenianCipher(key1);

        try (IDataDealer dealer = new FileDealer(new File("C:\\Users\\User\\Downloads\\Telegram Desktop\\OOSU10.exe"), new File("C:\\Users\\User\\Downloads\\Telegram Desktop\\OOSU10_encrypt.exe"))) {
            Cryptographer cryptographer = new Cryptographer(dealer);
            cryptographer.encrypt(cipher1);
            cryptographer.terminalEncrypt();
        }
        try (IDataDealer dealer = new FileDealer(new File("C:\\Users\\User\\Downloads\\Telegram Desktop\\OOSU10_encrypt.exe"), new File("C:\\Users\\User\\Downloads\\Telegram Desktop\\OOSU10_decrypt.exe"))) {
            Cryptographer cryptographer = new Cryptographer(dealer);
            cryptographer.encrypt(cipher1);
            cryptographer.terminalDecrypt();
        }
    }
}
