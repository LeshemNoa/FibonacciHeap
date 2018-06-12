import java.io.*;

public class MeasurementsA {

    public static void main(String[] args) {

    long startA = System.currentTimeMillis();

    FibonacciHeap heapAm = new FibonacciHeap();
    int m = 1000;
    for (int i = m; i >= 1; i--) {
        heapAm.insert(i);
    }
    long endA = System.currentTimeMillis();

    System.out.println(endA - startA + "," + FibonacciHeap.totalLinks() + "," + FibonacciHeap.totalCuts() + "," + heapAm.potential());
    }
}
