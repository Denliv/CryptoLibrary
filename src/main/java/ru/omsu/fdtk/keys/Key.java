package ru.omsu.fdtk.keys;

import java.util.Arrays;

public class Key
{
    protected byte[] byteArr;

    protected Key(byte[] byteArr)
    {
        this.byteArr = byteArr;
    }

    public byte[] getByteArr()
    {
        return byteArr;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Key key = (Key) o;
        return Arrays.equals(byteArr, key.byteArr);
    }

    @Override
    public int hashCode()
    {
        return Arrays.hashCode(byteArr);
    }
}
