import java.io.*;
import java.util.LinkedList;

public class MeasurementsA {

    public static void main(String[] args) {


        int m = 3000;
        FibonacciHeap heapAm = new FibonacciHeap();

        long startA = System.nanoTime();
        for (int j = m; j >= 1; j--) {
            heapAm.insert(j);
        }
        long endA = System.nanoTime();
        long time = endA - startA;

        System.out.println(""+ m + "," + time /1000000.0+ "," + FibonacciHeap.totalLinks() +
                "," + FibonacciHeap.totalCuts() + "," + heapAm.potential());
    }
}
