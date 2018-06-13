import java.io.*;

public class MeasurementsB {

    public static void main(String[] args) {


            int m = 3000;
            FibonacciHeap heapBm = new FibonacciHeap();

            long startB = System.nanoTime();
            for (int i = m; i >= 1; i--) {
                    heapBm.insert(i);
            }
            for (int j = 0; j < m / 2; j++) {
                heapBm.deleteMin();
            }
            long endB = System.nanoTime();
            long time = endB - startB;

            System.out.println(""+ m + ","+ time/1000000.0 + "," + FibonacciHeap.totalLinks() +
                "," + FibonacciHeap.totalCuts() + "," + heapBm.potential());

    }
}
