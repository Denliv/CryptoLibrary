import ciphers.ICipher;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import data_dealer.IDataDealer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Cryptographer {
    private final List<ICipher> cipherList;
    @JsonIgnore
    private IDataDealer dataDealer;
    public Cryptographer(List<ICipher> cipherList, IDataDealer dataDealer) {
        this.cipherList = new ArrayList<>();
        this.cipherList.addAll(cipherList);
        this.dataDealer = dataDealer;
    }

    public Cryptographer(IDataDealer dataDealer) {
        this(new ArrayList<>(), dataDealer);
    }
    @JsonCreator
    public Cryptographer(@JsonProperty("cipherList") List<ICipher> cipherList)
    {
        this(cipherList, null);
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
                this.dataDealer.writeBlock(cipherText);
            }
            do {
                cipherText = cipher.encrypt(new byte[0]);
                this.dataDealer.writeBlock(cipherText);
            }
            while (cipherText.length != 0);
        }
    }

    public void terminalDecrypt() throws IOException {
        for (int i = cipherList.size() - 1; i >= 0; --i) {
            var cipher = cipherList.get(i);
            byte[] plainText;
            dataDealer.toFirst();
            dataDealer.setBlockSize(cipher.getBlockSize());
            while (dataDealer.hasNext()) {
                var cipherText = dataDealer.readBlock();
                plainText = cipher.decrypt(cipherText);
                dataDealer.writeBlock(plainText);
            }
            do {
                plainText = cipher.decrypt(new byte[0]);
                this.dataDealer.writeBlock(plainText);
            }
            while (plainText.length != 0);
        }
    }


    public List<ICipher> getCipherList() {
        return cipherList;
    }

    public IDataDealer getDataDealer() {
        return dataDealer;
    }

    public void setDataDealer(IDataDealer dataDealer) {
        this.dataDealer = dataDealer;
    }
}
