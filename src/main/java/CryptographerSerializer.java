import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class CryptographerSerializer
{
    ObjectMapper mapper;

    public CryptographerSerializer(ObjectMapper mapper)
    {
        this.mapper = mapper;
    }

    public CryptographerSerializer()
    {
        this(new ObjectMapper());
    }
    public void serialize(Cryptographer cryptographer, File path, String keyName, Charset charset) throws IOException
    {
        if(path.isFile()) throw new IllegalArgumentException();
        path = new File(path, keyName + ".json");
        if (path.exists()) throw new IllegalArgumentException();
        try(Writer notBuffWriter = new FileWriter(path, charset);
            Writer writer = new BufferedWriter(notBuffWriter))
        {
            writer.write(mapper.writeValueAsString(cryptographer));
            writer.flush();
        }
    }
    public void serialize(Cryptographer cryptographer, File path, String keyName) throws IOException
    {
       serialize(cryptographer, path, keyName, StandardCharsets.UTF_8);
    }
    public void serialize(Cryptographer cryptographer, String path, String keyName, Charset charset) throws IOException
    {
        serialize(cryptographer, new File(path), keyName, charset);
    }
    public void serialize(Cryptographer cryptographer, String path, String keyName) throws IOException
    {
        serialize(cryptographer, new File(path), keyName, StandardCharsets.UTF_8);
    }
    public Cryptographer deserialize(File path, String keyName, Charset charset) throws IOException
    {
        if(path.isFile()) throw new IllegalArgumentException();
        path = new File(path, keyName + ".json");
        if (!path.exists()) throw new IllegalArgumentException();
        StringBuilder builder = new StringBuilder();
        try(Reader notBuffReader = new FileReader(path, charset);
            Reader reader = new BufferedReader(notBuffReader))
        {
            int character = reader.read();
            while (character != -1)
            {
                builder.append((char) character);
                character = reader.read();
            }
        }
        return mapper.readValue(builder.toString(), Cryptographer.class);
    }
    public Cryptographer deserialize(File path, String keyName) throws IOException
    {
        return deserialize(path, keyName, StandardCharsets.UTF_8);
    }
    public Cryptographer deserialize(String path, String keyName, Charset charset) throws IOException
    {
        return deserialize(new File(path), keyName, charset);
    }
    public Cryptographer deserialize(String path, String keyName) throws IOException
    {
        return deserialize(new File(path), keyName, StandardCharsets.UTF_8);
    }
}
