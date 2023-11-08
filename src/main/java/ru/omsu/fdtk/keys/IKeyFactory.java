package ru.omsu.fdtk.keys;

public interface IKeyFactory
{
    Key generate();
    public static Key create(byte[] byteArr)
    {
        return new Key(byteArr);
    }
}
