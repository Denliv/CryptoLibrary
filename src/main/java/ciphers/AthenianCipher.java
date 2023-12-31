package ciphers;

public class AthenianCipher implements ICipher {
    private byte[] key;
    private static final int _BLOCK_SIZE = 32;

    public AthenianCipher(byte[] key) {
        if (key[0] <= 0 || key[0] >= 26 || key[1] < 0 || key[1] >= 26)
            throw new IllegalArgumentException("not correct key");
        int[] NODE = getBezuCoefficients(key[0], 26);
        if ((key[0] * NODE[0] + 26 * NODE[1]) != 1)
            throw new IllegalArgumentException("not correct key");
        this.key = key;
    }

    @Override
    public byte[] encrypt(byte[] openText) {
        return mapTextToText(openText, true);
    }

    @Override
    public byte[] decrypt(byte[] closedText) {
        return mapTextToText(closedText, false);
    }

    private byte[] mapTextToText(byte[] openText, boolean encryptFlag) {
        if (openText.length == 0)
            return new byte[0];
        if (openText.length > _BLOCK_SIZE)
            throw new IllegalArgumentException("size of open text larger block size");
        byte[] closedText = new byte[openText.length];
        for (int i = 0; i < openText.length; i++) {
            closedText[i] = encryptOrDecryptInAthenianCipher(encryptFlag, openText[i]);
        }
        return closedText;
    }

    @Override
    public void setKey(byte[] key) {
        if (key[0] <= 0 || key[0] >= 26 || key[1] < 0 || key[1] >= 26)
            throw new IllegalArgumentException("not correct key");
        int[] NODE = getBezuCoefficients(key[0], 26);
        if ((key[0] * NODE[0] + 26 * NODE[1]) != 1)
            throw new IllegalArgumentException("not correct key");
        this.key = key;
    }

    @Override
    public int getBlockSize() {
        return _BLOCK_SIZE;
    }

    private byte encryptOrDecryptInAthenianCipher(boolean flagOfEncrypt, byte plainText) {
        byte firstChar;
        if (plainText <= 'z' && plainText >= 'a') {
            firstChar = 'a';
        } else if (plainText <= 'Z' && plainText >= 'A') {
            firstChar = 'A';
        } else return plainText;

        if (flagOfEncrypt) {
            return ((byte) ((((plainText - firstChar) * key[0] + key[1]) % 26) + firstChar));
        }

        int[] NODE = getBezuCoefficients(key[0], 26);
        int reverseElem = NODE[0];
        while (reverseElem < 0) {
            reverseElem += 26;
        }
        reverseElem = (reverseElem % 26);
        //считаем x - b
        int differenceOfCipherTextAndSecondInKey = ((plainText - firstChar) - key[1]);
        //считаем (x - b) * a^-1 по модулю 26
        while (differenceOfCipherTextAndSecondInKey < 0) {
            differenceOfCipherTextAndSecondInKey += 26;
        }
        return ((byte) (((differenceOfCipherTextAndSecondInKey * reverseElem) % 26) + firstChar));

    }

    private static int[] getBezuCoefficients(int first, int second) {
        if (first == 0 && second == 0) {
            throw new IllegalArgumentException();
        }
        first = Math.abs(first);
        second = Math.abs(second);
        int[] decompositionOfFirst = new int[]{1, 0};
        int[] decompositionOfSecond = new int[]{0, 1};
        while (second != 0) {
            int buffer = first;
            first = second;
            second = buffer;
            int[] bufferOfCoefficients = decompositionOfFirst;
            decompositionOfFirst = decompositionOfSecond;
            decompositionOfSecond = bufferOfCoefficients;
            if (second != 0) {
                int count = (first / second);
                first = (first % second);
                decompositionOfFirst[0] -= decompositionOfSecond[0] * count;
                decompositionOfFirst[1] -= decompositionOfSecond[1] * count;
            }
        }
        return decompositionOfFirst;
    }

    @Override
    public byte[] getKey() {
        return key;
    }
}
