import java.math.BigInteger;
import java.util.ArrayList;
public class RingsOfPrimes47 extends Thread {
    int start;
    int stop;
    int threadID;
    int range;
    ArrayList<BigInteger> currentRing;
    ArrayList<Integer> primesPerRing;
    Integer ringNumber;
    public ArrayList<String> getResult() {
        return result;
    }
    ArrayList<String> result;
    public RingsOfPrimes47(int threadID, int start, int stop, int range){
        this.start= start;
        this.stop = stop;
        this.threadID = threadID;
        this.range = range;
    }

    public void run() {
        for (int i = start;i<start+range;i++) {
            for(int j= 1; j<=stop;j++){
                for(int k= 1; k<=stop;k++){
                    for(int l= 1; l<=stop;l++){
                        doCallculation(BigInteger.valueOf(i), BigInteger.valueOf(j), BigInteger.valueOf(k), BigInteger.valueOf(l));
                    }
                }
            }
        }
    }
    public void doCallculation(BigInteger a, BigInteger b, BigInteger c, BigInteger d){
        result = new ArrayList<>();
        ringNumber = 1;
        currentRing = new ArrayList<>();
        primesPerRing = new ArrayList<>();
        currentRing.add( a);
        currentRing.add( b);
        currentRing.add( c);
        currentRing.add( d);
        result.add(currentRing.toString());
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
            result.add(currentRing.toString());
        }

        result.add("Primes per Ring: " + primesPerRing.toString());
        System.out.println("Thread "+ threadID+" has calculatet"+result);

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
