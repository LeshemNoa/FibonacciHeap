
public class Test {
    public static void main(String[] args) {
        cdllTest();
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
