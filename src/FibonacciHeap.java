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
     * The data stuctrure's potential, given by the potential function:
     * potential = #Trees + 2 * #Marked Nodes
     */
    private int potential;
    /**
     * The node with the minimal key in the heap.
     */
    private HeapNode min;
    /**
     * A circular, doubly linked list of the heap trees' roots.
     */
    private nodeCDLL roots;
    /**
     * The number of trees in the heaps.
     */
    private int treeNum = roots.size;
    /**
     * The total number of nodes in the heap.
     */
    private int size;

    /**
     * Constructor for the FibonacciHeap class. Sets default values in all fields
     * and assigns a new nodeCDLL object to the roots field.
     */
    public FibonacciHeap() {
        roots = new nodeCDLL();
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
    * public void meld (FibonacciHeap heap2)
    *  Utilizing the doubly linked structure of the root list
    *  Step 1: Compare the two mins, pick which one is the new min of the melded heap
    *  Step 2: Change the new min's prev to point at the other min
    *  Step 3: Change the other min's prev to point at the first min
    */
    public void meld (FibonacciHeap heap2)
    {
    	  return; // should be replaced by student code   		
    }

   /**
    * public int size()
    *
    * Return the number of elements in the heap
    *   
    */
    public int size()
    {
    	return 0; // should be replaced by student code
    }
    	
    /**
    * public int[] countersRep()
    *
    * Return a counters array, where the value of the i-th entry is the number of trees of order i in the heap. 
    *
     * We will use this in delete min, this is the bucket sorting part.
    */
    // probably going to be used in deleteMin
    public int[] countersRep()
    {
	int[] arr = new int[42];
        return arr; //	 to be replaced by student code
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
    * public int potential() 
    *
    * This function returns the current potential of the heap, which is:
    * Potential = #trees + 2*#marked
    * The potential equals to the number of trees in the heap plus twice the number of marked nodes in the heap. 
    */
    public int potential() 
    {    
    	return 0; // should be replaced by student code
    }

   /**
    * public static int totalLinks() 
    *
    * This static function returns the total number of link operations made during the run-time of the program.
    * A link operation is the operation which gets as input two trees of the same rank, and generates a tree of 
    * rank bigger by one, by hanging the tree which has larger value in its root on the tree which has smaller value 
    * in its root.
    */
    public static int totalLinks()
    {    
    	return 0; // should be replaced by student code
    }

   /**
    * public static int totalCuts() 
    *
    * This static function returns the total number of cut operations made during the run-time of the program.
    * A cut operation is the operation which diconnects a subtree from its parent (during decreaseKey/delete methods). 
    */
    public static int totalCuts()
    {    
    	return 0; // should be replaced by student code
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
        * The list of children of the node, represented by a nodeCDLL obejct.<br>
        */
       private nodeCDLL child;
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

    private class nodeCDLL {
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
         * Joins two node lists by concatenating another list to the current one.
         * 
         * @param other     Another nodeCDLL object to be concatenated to this list
         */
        private void join(nodeCDLL other) {
            this.tail.next = other.head;
            other.head.prev = this.tail;
            this.tail = other.tail;
            head.prev = tail;
            tail.next = head;
        }

    }
}
