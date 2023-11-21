package ciphers;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import serializer_details.ICipherDeserializer;
import serializer_details.ICipherSerializer;

@JsonSerialize(using = ICipherSerializer.class)
@JsonDeserialize(using = ICipherDeserializer.class)
public interface ICipher
{
    byte[] encrypt(byte[] openText);
    byte[] decrypt(byte[] closedText);
    void setKey(byte[] key);
    byte[] getKey();
    long getBlockSize();
}
