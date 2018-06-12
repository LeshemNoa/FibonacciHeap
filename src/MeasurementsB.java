import java.io.*;

public class MeasurementsB {

    public static void main(String[] args) {

            long startB = System.nanoTime();

            int m = 1000;
            FibonacciHeap heapBm = new FibonacciHeap();

            for (int i = m; i >= 1; i--) {
                heapBm.insert(i);
            }
            for (int j = 0; j < m / 2; j++) {
                heapBm.deleteMin();
            }
            long endB = System.nanoTime();
            long time = (endB - startB)/(long) 1000000000.0;

           System.out.println(""+ m + ","+ + time + "," + FibonacciHeap.totalLinks() +
                "," + FibonacciHeap.totalCuts() + "," + heapBm.potential());

    }
}
