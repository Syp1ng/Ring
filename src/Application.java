public class Application {

    public static void main(String[] args) {
        int cores = Runtime.getRuntime().availableProcessors();
        RingsOfPrimes47[] threadsArray = new RingsOfPrimes47[cores];
        int start= 1;
        int stop= 50;
        int range = stop/cores;
        for(int i=0;i<cores;i++){
            threadsArray[i] = new RingsOfPrimes47(i+1,start,stop, range);
            start+=range;
        }
        for(RingsOfPrimes47 t:threadsArray){
            t.start();
        }

    }




}
