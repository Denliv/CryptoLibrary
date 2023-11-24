package serializer_details;

import ciphers.ICipher;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class ICipherDeserializer extends StdDeserializer<ICipher> {
    public ICipherDeserializer() {
        super(ICipher.class);
    }

    @Override
    public ICipher deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        var keyList = new ArrayList<Byte>();
        String cipherName = null;
        boolean cipherNameFlag = false;
        byte[] key;
        boolean keyFlag = false;
        do {
            String currentName = jsonParser.getCurrentName();
            if ("cipherName".equals(currentName)) {
                if (cipherNameFlag) throw new IOException();
                cipherNameFlag = true;
                jsonParser.nextToken();
                cipherName = jsonParser.getText();
            }
            if ("key".equals(currentName)) {
                if (keyFlag) throw new IOException();
                keyFlag = true;
                if (jsonParser.nextToken() != JsonToken.START_ARRAY) throw new IOException();
                jsonParser.nextToken();
                do {
                    keyList.add(jsonParser.getByteValue());
                } while (jsonParser.nextToken() != JsonToken.END_ARRAY);
            }
        } while (jsonParser.nextToken() != JsonToken.END_OBJECT);
        if (!(keyFlag && cipherNameFlag)) throw new IOException();
        key = new byte[keyList.size()];
        for (int i = 0; i < keyList.size(); i++) {
            key[i] = keyList.get(i);
        }
        try {
            return (ICipher) Class.forName(cipherName).getConstructor(byte[].class).newInstance((Object) key);
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException |
                 IllegalAccessException e) {
            throw new IOException(e);
        }
    }
}
