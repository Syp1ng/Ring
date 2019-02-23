import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.ArrayList;

public class Application {
    static ArrayList<BigInteger> currentRing;
    static ArrayList<Integer> primesPerRing;
    static Integer ringNumber;
    public static void main(String[] args) {
        ringNumber = 1;
        currentRing = new ArrayList<>();
        primesPerRing = new ArrayList<>();
        currentRing.add(new BigInteger("1"));
        currentRing.add(new BigInteger("3"));
        currentRing.add(new BigInteger("5"));
        currentRing.add(new BigInteger("7"));
        int primesCounterCurrentRing=0;
        for(BigInteger bi:currentRing){
            if(checkPrime(bi)==true) primesCounterCurrentRing+=1;
        }
        primesPerRing.add(primesCounterCurrentRing);
        while (primesCounterCurrentRing>0){
            primesCounterCurrentRing =0;
            ringNumber +=1;
            ArrayList<BigInteger> oldRing = new ArrayList<>(currentRing);

            currentRing.set(0,oldRing.get(0).add(oldRing.get(1)).add(oldRing.get(3)));
            currentRing.set(1,oldRing.get(1).add(oldRing.get(0)).add(oldRing.get(2)));
            currentRing.set(2,oldRing.get(2).add(oldRing.get(1)).add(oldRing.get(3)));
            currentRing.set(3, oldRing.get(3).add(oldRing.get(2)).add(oldRing.get(0)));

            for(BigInteger bi:currentRing){
                if(checkPrime(bi)==true) primesCounterCurrentRing+=1;
            }
            primesPerRing.add(primesCounterCurrentRing);
        }
        System.out.println("Last ring: "+ currentRing);
        System.out.println("primes :"+ primesPerRing);

    }



    private static boolean checkPrime(BigInteger number){
        //check via BigInteger.isProbablePrime(certainty)
        if (!number.isProbablePrime(5))
            return false;

        //check if even
        BigInteger two = new BigInteger("2");
        if (!two.equals(number) && BigInteger.ZERO.equals(number.mod(two)))
            return false;

        //find divisor if any from 3 to 'number'
        for (BigInteger i = new BigInteger("3"); i.multiply(i).compareTo(number) < 1; i = i.add(two)) { //start from 3, 5, etc. the odd number, and look for a divisor if any
            if (BigInteger.ZERO.equals(number.mod(i))) //check if 'i' is divisor of 'number'
                return false;
        }
        return true;
    }



}
