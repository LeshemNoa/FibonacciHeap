import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Test {
    public static void main(String[] args) {
        fuzzTest(20);
    }

    public static void HeapTest() {
        FibonacciHeap heap = new FibonacciHeap();
        heap.insert(1);
        FibonacciHeap.HeapNode two = heap.insert(2);
        FibonacciHeap.HeapNode three = heap.insert(3);
        heap.insert(1);
        FibonacciHeap.HeapNode eight = heap.insert(8);
        heap.insert(10);
        System.out.println("min = " + heap.findMin().getKey());
        for (FibonacciHeap.HeapNode root : heap.roots) {
            System.out.println(HeapPrinter.toString(root));
        }

        System.out.println("\nDeleting min");
        heap.deleteMin();
        /*System.out.println("Size = " + heap.size());
        System.out.println("min = " + heap.findMin().getKey());
        System.out.println("Ranks: " + Arrays.toString(heap.countersRep()));
        System.out.println("Roots: " + heap.roots.toString());*/
        for (FibonacciHeap.HeapNode root : heap.roots) {
            System.out.println(HeapPrinter.toString(root));
        }

        System.out.println("\nDecreasing key 2 -> -4");
        heap.decreaseKey(two, 4);
        for (FibonacciHeap.HeapNode root : heap.roots) {
            System.out.println(HeapPrinter.toString(root));
        }

        System.out.println("\nInserting stuff");
        heap.insert(11);
        heap.insert(40);
        heap.insert(5);
        heap.insert(-7);
        FibonacciHeap.HeapNode twelve = heap.insert(12);
        System.out.println("min = " + heap.findMin().getKey());
        for (FibonacciHeap.HeapNode root : heap.roots) {
            System.out.println(HeapPrinter.toString(root));
        }

        System.out.println("\nDelete min");
        heap.deleteMin();
        System.out.println("min = " + heap.findMin().getKey());
        System.out.println("Ranks: " + Arrays.toString(heap.countersRep()));
        for (FibonacciHeap.HeapNode root : heap.roots) {
            System.out.println(HeapPrinter.toString(root));
        }

        System.out.println("\nDelete min");
        heap.deleteMin();
        System.out.println("min = " + heap.findMin().getKey());
        System.out.println("Ranks: " + Arrays.toString(heap.countersRep()));
        for (FibonacciHeap.HeapNode root : heap.roots) {
            System.out.println(HeapPrinter.toString(root));
        }

        System.out.println("\nDelete min");
        heap.deleteMin();
        System.out.println("min = " + heap.findMin().getKey());
        System.out.println("Ranks: " + Arrays.toString(heap.countersRep()));
        for (FibonacciHeap.HeapNode root : heap.roots) {
            System.out.println(HeapPrinter.toString(root));
        }
    }


    public static void fuzzTest(int size) {
        FibonacciHeap heap = new FibonacciHeap();
        List<FibonacciHeap.HeapNode> list = new ArrayList<>();
        Random r = new Random(11);

        for (int i = 0; i < size; i++) {
            list.add(heap.insert(r.nextInt(100)));
        }
        for (FibonacciHeap.HeapNode root : heap.roots) {
            System.out.println(HeapPrinter.toString(root));
        }
        System.out.println("------Done inserting------");

        for (int i = 0; i < size / 2; i++) {
            FibonacciHeap.HeapNode node = list.get(r.nextInt(list.size()));
            int randomNum = Math.floorMod(Math.abs(r.nextInt()),3);
            System.out.println("i = " +i + ", " + "node " + node);
            switch (randomNum) {
                case 0: {
                    int delta = Math.abs(r.nextInt());
                    System.out.println("Decrease Key by " + delta +"\n");
                    heap.decreaseKey(node, delta);
                    for (FibonacciHeap.HeapNode root : heap.roots) {
                        System.out.println(HeapPrinter.toString(root));
                    }
                    break;
                }
                case 1: {
                    System.out.println("Delete Min\n");
                    heap.deleteMin();
                    break;
                }
                case 2: {
                    System.out.println("Delete\n");
                    heap.delete(node);
                    for (FibonacciHeap.HeapNode root : heap.roots) {
                        System.out.println(HeapPrinter.toString(root));
                    }
                    break;
                }
            }
        }
        for (FibonacciHeap.HeapNode root : heap.roots) {
            System.out.println(HeapPrinter.toString(root));
        }
     }

    public static void cdllTest() {
        FibonacciHeap heap = new FibonacciHeap();
        FibonacciHeap.NodeCDLL list = heap.roots;
        FibonacciHeap.HeapNode one = heap.new HeapNode(1);
        FibonacciHeap.HeapNode two = heap.new HeapNode(2);
        FibonacciHeap.HeapNode three = heap.new HeapNode(3);
        FibonacciHeap.HeapNode four = heap.new HeapNode(4);

        list.insert(one);
        list.insert(two);
        list.insert(three);
        list.insert(four);

        System.out.println("List head: " + list.head.getKey());
        System.out.println("List tail: " + list.tail.getKey());
        System.out.println("Head next: " + list.head.next.getKey());
        System.out.println("Head prev: " + list.head.prev.getKey());
        System.out.println("Tail next: " + list.tail.next.getKey());

        System.out.println("Nodes in order: ");
        for (FibonacciHeap.HeapNode node : list) {
            System.out.print("" + node.getKey() +", ");
        }
        System.out.println("");

        System.out.println("Removing 2");
        list.remove(two);
        System.out.println("List head: " + list.head.getKey());
        System.out.println("List tail: " + list.tail.getKey());
        System.out.println("Head next: " + list.head.next.getKey());
        System.out.println("Head prev: " + list.head.prev.getKey());
        System.out.println("Tail next: " + list.tail.next.getKey());
        System.out.println("Tail Prev: " + list.tail.prev.getKey());

        System.out.println("Nodes in order: ");
        for (FibonacciHeap.HeapNode node : list) {
            System.out.print("" + node.getKey() +", ");
        }

        FibonacciHeap.NodeCDLL list2 = heap.new NodeCDLL();
        FibonacciHeap.HeapNode five = heap.new HeapNode(5);
        FibonacciHeap.HeapNode six = heap.new HeapNode(6);
        FibonacciHeap.HeapNode seven = heap.new HeapNode(7);

        list2.insert(five);
        list2.insert(six);
        list2.insert(seven);

        System.out.println("Nodes of list 2 in order: ");
        for (FibonacciHeap.HeapNode node : list2) {
            System.out.print("" + node.getKey() +", ");
        }
        System.out.println("");

        list.join(list2);

        System.out.println("Melded list nodes in order: ");
        for (FibonacciHeap.HeapNode node : list) {
            System.out.print("" + node.getKey() +", ");
        }
        System.out.println("");

        System.out.println("List head: " + list.head.getKey());
        System.out.println("List tail: " + list.tail.getKey());
        System.out.println("Head next: " + list.head.next.getKey());
        System.out.println("Head prev: " + list.head.prev.getKey());
        System.out.println("Tail next: " + list.tail.next.getKey());
        System.out.println("Tail Prev: " + list.tail.prev.getKey());

        System.out.println("Removing 1");
        list.remove(one);

        System.out.println("Melded list nodes in order: ");
        for (FibonacciHeap.HeapNode node : list) {
            System.out.print("" + node.getKey() +", ");
        }
        System.out.println("");
        System.out.println("List head: " + list.head.getKey());
        System.out.println("List tail: " + list.tail.getKey());
        System.out.println("Head next: " + list.head.next.getKey());
        System.out.println("Head prev: " + list.head.prev.getKey());
        System.out.println("Tail next: " + list.tail.next.getKey());
        System.out.println("Tail Prev: " + list.tail.prev.getKey());

        System.out.println("Removing 7");
        list.remove(seven);

        System.out.println("Melded list nodes in order: ");
        for (FibonacciHeap.HeapNode node : list) {
            System.out.print("" + node.getKey() +", ");
        }
        System.out.println("");
        System.out.println("List head: " + list.head.getKey());
        System.out.println("List tail: " + list.tail.getKey());
        System.out.println("Head next: " + list.head.next.getKey());
        System.out.println("Head prev: " + list.head.prev.getKey());
        System.out.println("Tail next: " + list.tail.next.getKey());
        System.out.println("Tail Prev: " + list.tail.prev.getKey());
    }
}
