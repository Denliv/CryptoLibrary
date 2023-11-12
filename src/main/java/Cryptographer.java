import ciphers.ICipher;
import data_dealer.IDataDealer;

import java.util.ArrayList;
import java.util.Arrays;
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

    public void terminalEncrypt() {
        for (var cipher : cipherList) {
            this.dataDealer.toFirst();
            this.dataDealer.setBlockSize(cipher.getBlockSize());
            while (dataDealer.hasNext()) {
                var plainText = this.dataDealer.readBlock();
                var cipherText = cipher.encrypt(plainText);
                if (cipherText.length != 0) this.dataDealer.writeBlock(cipherText);
            }
            while(!Arrays.equals(cipher.encrypt(new byte[]{}), new byte[]{})) {}
        }
    }

    public void terminalDecrypt(List<ICipher> cipherList, IDataDealer dataDealer) {
        for (int i = cipherList.size() - 1; i >= 0; --i) {
            var cipher = cipherList.get(i);
            dataDealer.toFirst();
            dataDealer.setBlockSize(cipher.getBlockSize());
            while (dataDealer.hasNext()) {
                var cipherText = dataDealer.readBlock();
                var plainText = cipher.decrypt(cipherText);
                if (plainText.length != 0) dataDealer.writeBlock(cipherText);
            }
        }
    }
}
