import ciphers.ICipher;
import data_dealer.IDataDealer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Cryptographer {
    private final List<ICipher> cipherList;
    private IDataDealer dataDealer;

    public Cryptographer(List<ICipher> cipherList, IDataDealer dataDealer) {
        this.cipherList = new ArrayList<>();
        this.cipherList.addAll(cipherList);
        this.dataDealer = dataDealer;
    }

    public Cryptographer(IDataDealer dataDealer) {
        this(null, dataDealer);
    }

    public Cryptographer encrypt(ICipher cipher) {
        this.cipherList.add(cipher);
        return this;
    }

    public void terminalEncrypt() throws IOException {
        for (var cipher : cipherList) {
            byte[] cipherText;
            this.dataDealer.toFirst();
            this.dataDealer.setBlockSize(cipher.getBlockSize());
            while (dataDealer.hasNext()) {
                var plainText = this.dataDealer.readBlock();
                cipherText = cipher.encrypt(plainText);
                if (cipherText.length > cipher.getBlockSize())
                    throw new IllegalStateException("time to fix cipher)))");
                this.dataDealer.writeBlock(cipherText);
            }
            do {
                cipherText = cipher.encrypt(new byte[0]);
                this.dataDealer.writeBlock(cipherText);
            }
            while (cipherText.length != 0);
        }
    }

    public void terminalDecrypt(List<ICipher> cipherList, IDataDealer dataDealer) throws IOException {
        for (int i = cipherList.size() - 1; i >= 0; --i) {
            var cipher = cipherList.get(i);
            byte[] plainText;
            dataDealer.toFirst();
            dataDealer.setBlockSize(cipher.getBlockSize());
            while (dataDealer.hasNext()) {
                var cipherText = dataDealer.readBlock();
                plainText = cipher.decrypt(cipherText);
                if (plainText.length > cipher.getBlockSize())
                    throw new IllegalStateException("time to fix cipher)))");
                dataDealer.writeBlock(plainText);
            }
            do {
                plainText = cipher.decrypt(new byte[0]);
                this.dataDealer.writeBlock(plainText);
            }
            while (plainText.length != 0);
        }
    }

    public void setDataDealer(IDataDealer dataDealer)
    {
        this.dataDealer = dataDealer;
    }
}
