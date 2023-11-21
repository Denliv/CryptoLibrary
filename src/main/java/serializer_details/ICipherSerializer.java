package serializer_details;

import ciphers.ICipher;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class ICipherSerializer extends StdSerializer<ICipher>
{
    public ICipherSerializer() {
        super(ICipher.class);
    }

    @Override
    public void serialize(ICipher cipher, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException
    {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField
                    ("cipherName", cipher.getClass().getCanonicalName());
        jsonGenerator.writeFieldName("key");
        jsonGenerator.writeStartArray();
        for(Byte byteBlock : cipher.getKey())
        {
            jsonGenerator.writeNumber(byteBlock);
        }
        jsonGenerator.writeEndArray();
        jsonGenerator.writeEndObject();
    }
}
