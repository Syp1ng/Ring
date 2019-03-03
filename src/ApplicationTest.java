import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.ArrayList;

class ApplicationTest {

    @Test
    public void TestNumber() {
        RingsOfPrimes47 s = new RingsOfPrimes47(1, 10, 500, 1);
        s.doCallculation(new BigInteger("1"), new BigInteger("3"), new BigInteger("5"), new BigInteger("7"));
        ArrayList<String> result = s.getResult();
        Assertions.assertEquals(
                "[[1, 3, 5, 7], [11, 9, 15, 13], [33, 35, 37, 39], [107, 105, 111, 109], [321, 323, 325, 327], Primes per Ring: [3, 2, 1, 2, 0]]",
                result.toString());


    }

    @Test
    public void TestPerformance() {
        long startTime;
        long stopTime;
        int start = 0;
        int stop = 20;
        startTime = System.currentTimeMillis();
        RingsOfPrimes47 s = new RingsOfPrimes47(1, start, stop, stop);
        s.start();
        try {
            s.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        stopTime = System.currentTimeMillis();
        long test1 = stopTime - startTime;

        startTime = System.currentTimeMillis();
        int cores = Runtime.getRuntime().availableProcessors();
        RingsOfPrimes47[] threadsArray = new RingsOfPrimes47[cores];
        int range = stop / cores;
        for (int i = 0; i < cores; i++) {
            threadsArray[i] = new RingsOfPrimes47(i + 1, start, stop, range);
            start += range;
        }
        for (RingsOfPrimes47 t : threadsArray) {
            t.start();
        }
        try {
            for (RingsOfPrimes47 t : threadsArray) t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        stopTime = System.currentTimeMillis();
        long test2 = stopTime - startTime;
        System.out.println(test1 + "/" + test2 + " ms");

        Assertions.assertTrue(test1 > test2);

    }

}