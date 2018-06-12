import java.io.*;

public class MeasurementsA {

    public static void main(String[] args) {

        long startA = System.nanoTime();

        int m = 1000;
        FibonacciHeap heapAm = new FibonacciHeap();
        for (int j = m; j >= 1; j--) {
            heapAm.insert(j);
        }
        long endA = System.nanoTime();
        long time = (endA - startA)/(long) 1000000000.0;

        System.out.println(""+ m + ","+ + time + "," + FibonacciHeap.totalLinks() +
                "," + FibonacciHeap.totalCuts() + "," + heapAm.potential());
    }
}
