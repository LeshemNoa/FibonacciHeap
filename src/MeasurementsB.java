import java.io.*;

public class MeasurementsB {

    public static void main(String[] args) {

        int m=1000;
        long startB = System.currentTimeMillis();

        FibonacciHeap heapBm = new FibonacciHeap();

        for (int i = m; i >= 1; i--) {
            heapBm.insert(i);
        }
        for (int j = 0; j < m / 2; j++) {
            heapBm.deleteMin();
        }
        long endB = System.currentTimeMillis();
        System.out.println(endB - startB + "," + FibonacciHeap.totalLinks() + "," + FibonacciHeap.totalCuts() + "," + heapBm.potential());
    }
}
