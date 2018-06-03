import java.util.Iterator;

/**
 * FibonacciHeap
 *
 * An implementation of fibonacci heap over non-negative integers.
 */
public class FibonacciHeap {

//   TODO  Rimon - Delete min etc.
//   TODO Noa - decrease key, delete, etc.
    /**
     * Counts the total number of links executed since the class was launched.
     */
    private static int totalLinks;
    /**
     * Counts the total number of cuts executed since the class was launched.
     */
    private static int totalCuts;
    /**
     * The current number of marked nodes in the tree.
     */
    private int marked;
    /**
     * The node with the minimal key in the heap.
     */
    private HeapNode min;
    /**
     * A circular, doubly linked list of the heap trees' roots.
     */
    private NodeCDLL roots;
    /**
     * The highest rank out of all the trees in the heap.
     */
    private int maxRank;
    /**
     * The total number of nodes in the heap.
     */
    private int size;

    /**
     * Constructor for the FibonacciHeap class. Sets default values in all fields
     * and assigns a new NodeCDLL object to the roots field.
     */
    public FibonacciHeap() {
        roots = new NodeCDLL();
    }

    /**
     * Checks if the heap is empty. <br>
     *
     * This method runs in O(1) time as it only requires accessing a field.
     *
     * @return      true if the heap is empty, else false.
     */
    public boolean empty()
    {
        return (min == null);
    }


    /**
     * Inserts a node with provided key to the heap. <br>
     *
     * Employs a lazy insertion algorithm - the new node is inserted as a binomial
     * tree of rank 0 to the list of roots. Hence this method runs at O(1) time -
     * it only requires changing a fixed number of pointers.
     *
     * @param key   The integer key to be inserted to the heap
     * @return      The insertted node
     */
    public HeapNode insert(int key)  {
        HeapNode node = this.roots.insert(key);
        if (min == null) {
            min = null;
        }
        else if (node.key < min.key) {
            min = node;
        }
        size++;
        return node;
    }

   /**
    * public void deleteMin()
    *
    * Delete the node containing the minimum key.
    *
    * Complicated, keep that for later
    *
    */
    public void deleteMin() {
     	return; // should be replaced by student code
    }

   /**
    * public HeapNode findMin()
    *
    * Return the node of the heap whose key is minimal. 
    *
    */
    public HeapNode findMin()
    {
    	return min;
    }

    /**
     * Melds two Fibonacci Heaps into the current one. <br>
     *
     * This method employs the lazy meld algorithm where the two lists of
     * trees are joined into one and the minimal node is updated if necessary.
     *
     * This method runs in O(1) time as its runtime complexity is determined
     * by that of NodeCDLL.join, which runs in O(1) time.
     *
     * @param heap2     Another Fibonacci heap to be melded with this one
     */
    public void meld (FibonacciHeap heap2)
    {
        this.roots.join(heap2.roots);
        if (heap2.roots.size != 0) {
            if (heap2.min.key < this.min.key) {
                this.min = heap2.min;
            }
            if (this.maxRank < heap2.maxRank) {
                this.maxRank = heap2.maxRank;
            }
            this.size += heap2.size;
        }
    }

   /**
    * public int size()
    *
    * Return the number of elements in the heap
    *   
    */
    public int size()
    {
    	return size;
    }
    	
    /**
    * public int[] countersRep()
    *
    * Return a counters array, where the value of the i-th entry is the number of trees of order i in the heap. 
    *
     * We will use this in delete min, this is the bucket sorting part.
    */
    public int[] countersRep() {
        if (maxRank == 0) { // Heap is a linked list
            int[] counts = {roots.size};
            return counts;
        }
        else {
            int[] counts = new int[maxRank];
            HeapNode curr = this.roots.head;
            boolean done = false;
            while (!done) {
                counts[curr.rank]++;
                curr = curr.next;
                if (curr == roots.head) // Gone over all roots once
                    done = true;
            }
            return counts;
        }
    }
	
   /**
    * public void delete(HeapNode x)
    *
    * Delete and decrese key work very similar.
    *
    * We cut the subtree of the node we delete, we remove the node as the root,
    * and add its child subtree to the list of trees. Perhaps triggering cascading
    * cuts.
    *
    *
    */
    public void delete(HeapNode x) 
    {    
    	return; // should be replaced by student code
    }

   /**
    * public void decreaseKey(HeapNode x, int delta)
    *
    * The function decreases the key of the node x by delta. The structure of the heap should be updated
    * to reflect this chage (for example, the cascading cuts procedure should be applied if needed).
    */
    public void decreaseKey(HeapNode x, int delta)
    {    
    	return; // should be replaced by student code
    }

    /**
     * Returns the current potential of the heap, given by the potential function
     * /Phi = # Tree + 2 * # Marked nodes.
     *
     * This method runs in O(1) time as it only checks fields.
     *
     * @return      The current potential of the heap.
     */
    public int potential() 
    {    
    	return (roots.size + 2*marked);
    }

   /**
    * public static int totalLinks() 
    *
    * This static function returns the total number of link operations made during the
    * run-time of the program. A link operation is the operation which gets as input two
    * trees of the same rank, and generates a tree of rank bigger by one, by hanging the
    * tree which has larger value in its root on the tree which has smaller value in its root.
    *
    * @return   The number of links made since the class was loaded.
    */
    public static int totalLinks()
    {    
    	return totalLinks;
    }

   /**
    * public static int totalCuts() 
    *
    * This static function returns the total number of cut operations made during the run-time of the program.
    * A cut operation is the operation which diconnects a subtree from its parent (during decreaseKey/delete methods). 
    */
    public static int totalCuts()
    {    
    	return totalCuts;
    }
    
   /**
    * public class HeapNode
    * 
    * If you wish to implement classes other than FibonacciHeap
    * (for example HeapNode), do it in this file, not in 
    * another file 
    *  
    */
    public class HeapNode {

        private int key;
        private int rank;
        private boolean mark = false;
       /**
        * The list of children of the node, represented by a NodeCDLL obejct.<br>
        */
       private NodeCDLL child;
        private HeapNode next;
        private HeapNode prev;
        private HeapNode parent;

        public HeapNode(int key) {
            this.key = key;
        }

        public int getKey() {
            return this.key;
        }

    }

    private class NodeCDLL {
        private HeapNode head;
        private HeapNode tail;
        private int size;

        /**
         * Inserts a new node at the end of the list, setting it to be its new tail.
         * Returns the inserted node. <br>
         *
         * This method runs iin O(1) time as it only requires changing a fixed number
         * of pointers.
         *
         * @param key   key of the new node inserted to the node list
         * @return      a pointer to the inserted node
         */
        private HeapNode insert(int key) {
            HeapNode node = new HeapNode(key);
            if (head == null) {
                head = node;
                tail = node;
                head.prev = tail;
                tail.next = head;
            }
            else {
                tail.next = node;
                node.prev = tail;
                tail = node;
                tail.next = head;
            }
            size++;
            return node;
        }

        /**
         * Joins two node lists by concatenating another list to the current one. <br>
         *
         * The circular structure of the list is maintained. This method runs in O(1) time.
         *
         * @param other     Another NodeCDLL object to be concatenated to this list
         */
        private void join(NodeCDLL other) {
            if (other != null && other.size != 0) {
                this.tail.next = other.head;
                other.head.prev = this.tail;
                this.tail = other.tail;
                head.prev = tail;
                tail.next = head;

                this.size += other.size;
            }
        }
    }
}
