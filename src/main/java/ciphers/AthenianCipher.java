package ciphers;

public class AthenianCipher implements ICipher
{
    private byte[] key;

    public AthenianCipher(byte[] key)
    {
        if (key[0] <= 0 || key[0] >= 26 || key[1] < 0 || key[1] >= 26)
            throw new IllegalArgumentException("not correct key");
        byte[] NODE = getBezuCoefficients(key[0], (byte) 26);
        if ((key[0] * NODE[0] + 26 * NODE[1]) != 1)
            throw new IllegalArgumentException("not correct key");
        this.key = key;
    }

    @Override
    public byte[] encrypt(byte[] openText)
    {
        return mapTextToText(openText, true);
    }

    @Override
    public byte[] decrypt(byte[] closedText)
    {
        return mapTextToText(closedText, false);
    }
    private byte[] mapTextToText(byte[] openText, boolean encryptFlag)
    {
        if (openText.length == 0)
            return new byte[0];
        byte[] closedText = new byte[openText.length];
        for (int i = 0; i < openText.length; i++)
        {
            closedText[i] = encryptOrDecryptInAthenianCipher(encryptFlag, openText[i]);
        }
        return closedText;
    }

    @Override
    public void setKey(byte[] key)
    {
        if (key[0] <= 0 || key[0] >= 26 || key[1] < 0 || key[1] >= 26)
            throw new IllegalArgumentException("not correct key");
        byte[] NODE = getBezuCoefficients(key[0], (byte) 26);
        if ((key[0] * NODE[0] + 26 * NODE[1]) != 1)
            throw new IllegalArgumentException("not correct key");
        this.key = key;
    }

    @Override
    public long getBlockSize() {
        return 32;
    }
    private byte encryptOrDecryptInAthenianCipher(boolean flagOfEncrypt, byte plainText)
    {
        byte[] NODE = getBezuCoefficients(key[0], (byte) 26);
        byte firstChar;
        if (plainText <= 'z' && plainText >= 'a')
        {
            firstChar = 'a';
        }
        else if (plainText <= 'Z' && plainText >= 'A')
        {
            firstChar = 'A';
        }
        else return plainText;

        if (flagOfEncrypt)
        {
            return ((byte) ((((plainText - firstChar) * key[0] + key[1]) % 26) + firstChar));
        }

        byte reverseElem = NODE[0];
        while(reverseElem < 0)
        {
            reverseElem += (byte) 26;
        }
        reverseElem = (byte) (reverseElem % 26);
        //считаем x - b
        byte differenceOfCipherTextAndSecondInKey = (byte) ((plainText - firstChar) -  key[1]);
        //считаем (x - b) * a^-1 по модулю 26
        while(differenceOfCipherTextAndSecondInKey < 0)
        {
            differenceOfCipherTextAndSecondInKey += 26;
        }
        return ((byte)(((differenceOfCipherTextAndSecondInKey * reverseElem) % 26) + firstChar));

    }
    private static byte[] getBezuCoefficients(byte first, byte second)
    {
        if (first == 0 && second == 0) throw new IllegalArgumentException();
        first = (byte) Math.abs(first);
        second = (byte) Math.abs(second);
        byte[] decompositionOfFirst = new byte[]{1, 0};
        byte[] decompositionOfSecond = new byte[]{0, 1};
        while(true)
        {
            if (first < second)
            {
                byte buffer = first;
                first = second;
                second = buffer;
                byte [] bufferOfCoefficients = decompositionOfFirst;
                decompositionOfFirst = decompositionOfSecond;
                decompositionOfSecond = bufferOfCoefficients;
            }
            if (second == 0)
                return decompositionOfFirst;
            byte count = (byte) ( first / second);
            first =(byte) (first % second);
            decompositionOfFirst[0] -= decompositionOfSecond[0] * count;
            decompositionOfFirst[1] -= decompositionOfSecond[1] * count;
        }
    }
}
