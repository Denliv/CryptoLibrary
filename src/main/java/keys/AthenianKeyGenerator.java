package keys;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class AthenianKeyGenerator implements IKeyGenerator
{
    private final Map<Integer, Integer> nonZeroDivisors;
    private final Random random;

    public AthenianKeyGenerator()
    {
        random = new Random();
        nonZeroDivisors = new HashMap<>();
        //nonZeroDivisors = Map.of(0, 1,
          //      1, 3,
             //   2, 5);
        nonZeroDivisors.put(0, 1);
        nonZeroDivisors.put(1, 3);
        nonZeroDivisors.put(2, 5);
        nonZeroDivisors.put(3, 7);
        nonZeroDivisors.put(4, 9);
        nonZeroDivisors.put(5, 11);
        nonZeroDivisors.put(6, 15);
        nonZeroDivisors.put(7, 17);
        nonZeroDivisors.put(8, 19);
        nonZeroDivisors.put(9, 21);
        nonZeroDivisors.put(10, 23);
        nonZeroDivisors.put(11, 25);
    }

    @Override
    public byte[] generate()
    {
        int a = random.nextInt(12);
        a = nonZeroDivisors.get(a);
        byte b = (byte) random.nextInt(26);
        return new byte[]{(byte) a, b};
    }
    public byte[] create(int a, int b)
    {
        return new byte[]{(byte) a, (byte) b};
    }
}
