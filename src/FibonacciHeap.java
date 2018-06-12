import java.util.Iterator;

/**
 * FibonacciHeap
 *
 * An implementation of fibonacci heap over non-negative integers.
 */
public class FibonacciHeap {
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
    /*private*/ public NodeCDLL roots;
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
        assert (min == null) == (size == 0);
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
     * @return      The inserted node
     */
    public HeapNode insert(int key)  {
        HeapNode node = this.roots.insert(key);
        if (empty() || node.key < min.key) {
            min = node;
        }
        size++;
        return node;
    }
//TODO - Delete Min documentation
   /**
    * public void deleteMin()
    *
    * Delete the node containing the minimum key.
    *
    * Complicated, keep that for later
    *
    */
   public void deleteMin() {
       HeapNode node = min;
       if (node != null) {
           NodeCDLL children = node.children;
           if (children.size != 0) {
               for (HeapNode child : node.children) {
                   child.parent = null;
               }
               roots.join(children);
           }
           roots.remove(min);

           if (roots.size == 0) {
               min = null;
           } else {
               min = roots.head;
               consolidate();
           }
           size--;
       }
   }

    /**
     * Calculates an upper bound for ranks in the heap, based on its size; <br>
     * It's given by log_phi(n) where n is the number of nodes in the heap. We take the
     * ceiling of that value to be the rank upper bound.
     *
     * This calculation runs in O(1) time, as this is the runtime complexity of arithmetic
     * operations.
     *
     * @param size      Current number of nodes in the heap
     * @return          An upper bound for its heighest rank
     */
   private int rankUpperBound(int size){
       if (size != 0) {
           double phi = 1.6180339;
           return (int) Math.ceil(Math.log10((double) size) / Math.log10(phi));
       }
       return 0;
   }

   private void consolidate(){
        HeapNode[] rankArray = new HeapNode[rankUpperBound(size)];
        int n = roots.size;
        HeapNode node = roots.head;

        for (int i = 0; i < n; i++) {
            HeapNode curr = node;
            node = node.next;

            if (curr != null) {
                int currRank = curr.rank;

                while (rankArray[currRank] != null) {
                    HeapNode inBucket = rankArray[currRank];
                    if (curr.key > inBucket.key) {
                        curr = link(inBucket, curr);
                    } else {
                        curr = link(curr, inBucket);
                    }
                    rankArray[currRank] = null;
                    currRank++;
                    if (currRank > maxRank) {
                        maxRank = currRank;
                    }
                }

                rankArray[currRank] = curr;
            }
        }

        min = null;
        roots = new NodeCDLL();

        for (HeapNode root : rankArray) {
            if (root != null) {
                roots.insert(root);
                if (empty() || root.key < min.key) {
                    min = root;
                }
            }
        }
    }

    private HeapNode link(HeapNode parent, HeapNode child){
        roots.remove(child);
        parent.children.insert(child);
        child.parent = parent;
        parent.rank++;
        child.mark = false;
        FibonacciHeap.totalLinks++;

        return parent;
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
    public void meld (FibonacciHeap heap2) {
        if (heap2 == null || heap2.roots.size == 0) {
            return;
        }
        this.roots.join(heap2.roots);

        if (this.empty() || heap2.min.key < this.min.key) {
            this.min = heap2.min;
        }
        if (this.maxRank < heap2.maxRank) {
            this.maxRank = heap2.maxRank;
        }
        this.size += heap2.size;
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
     * Produces an array of counters for the number of trees in each rank,
     * where the rank of a tree is defined as the number of children the tree
     * root has. <br>
     *
     * This method runs in O(n) time, where n is the number of trees in the heap,
     * as it traverses the root list and increments the appropriate cell in the
     * array according to the current tree's rank.<br>
     *
     * @return      An integer array where the i'th cell contains the number of trees
     *              of rank i in the heap
     */
    public int[] countersRep() {
        if (maxRank == 0) { // Heap is a linked list
            int[] counts = {roots.size};
            return counts;
        }
        else {
            int[] counts = new int[maxRank + 1];
            for (HeapNode root : roots) {
                assert (root != null);
                counts[root.rank]++;
            }
            return counts;
        }
    }

// TODO - Delete Documentation
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
        if (x == null) {
            return;
        }

        decreaseKeyTo(x, min.key - 10);
        deleteMin();
    }
//TODO - Decrease key and documentation

    /**
     * Decreases the key of a heap node's key by a provided integer.<br>
     *
     * If
     * @param x         HeapNode object whose key is to be decrease
     * @param delta     The number to be reduced from x's key
     */
    public void decreaseKey(HeapNode x, int delta) {
        decreaseKeyTo(x, x.key - delta);
    }

    public void decreaseKeyTo(HeapNode x, int newKey) {
        x.key = newKey;
        HeapNode parent = x.parent;
        if (parent != null && x.key < parent.key) {
            cut(x);
            cascadingCut(parent);
        }
        if (x.key < min.key) {
            min = x;
        }
    }

    private void cut(HeapNode x) {
        HeapNode parent = x.parent;
        parent.children.remove(x);
        parent.rank--;

        roots.insert(x);
        x.parent = null;

        x.mark = false;
        marked--;
    }

    private void cascadingCut(HeapNode curr) {
        HeapNode parent = curr.parent;
        if (parent != null) {
            if (curr.mark == false) {
                curr.mark = true;
                marked++;
            }
            else {
                cut(curr);
                cascadingCut(parent);
            }
        }
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

//TODO - Documentation for total links
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
//TODO - Documentation for total cuts
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
//TODO - Documentation for HeapNode class
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
       /**
        * Mark is true if it has had one of its children cut, false otherwise.<br>
        *
        * This attribute is revelant locally: it maintains its value as long as this node
        * has not been cut from its parent. When ths node is cut, it becomes a root so the
        * field will be reset to false.
        */
       private boolean mark = false;
       /**
        * The list of children of the node, represented by a NodeCDLL obejct.<br>
        */
        /*private*/ NodeCDLL children;
        /*private*/ HeapNode next;
        /*private*/ HeapNode prev;
        /*private*/ HeapNode parent;

        public HeapNode(int key) {
            this.key = key;
            this.children = new NodeCDLL();
        }

        public int getKey() {
            return this.key;
        }

        /**************************************/
        @Override
        public String toString() {
            return "" + this.key;
        }

    }

    /*private*/ class NodeCDLL implements Iterable<HeapNode> {
        /*private*/ HeapNode head;
        /*private*/ HeapNode tail;
        /*private*/ int size;

        /**
         * Inserts a new node at the end of the list, setting it to be its new tail.
         * Returns the inserted node. <br>
         *
         * This method runs in O(1) time as it only requires changing a fixed number
         * of pointers.
         *
         * @param key   key of the new node inserted to the node list
         * @return      a pointer to the inserted node
         */
        /*private*/ HeapNode insert(int key) {
            HeapNode node = new HeapNode(key);
            insert(node);
            return node;
        }

        /**
         * Inserts an existing node object at the end of the list, setting it to be its
         * new tail. <br>
         *
         * @param node
         */
        /*private*/ void insert(HeapNode node) {
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
                head.prev = tail;
            }
            size++;
        }

        /**
         * Joins two node lists by concatenating another list to the current one. <br>
         *
         * The circular structure of the list is maintained. This method runs in O(1) time.
         *
         * @param other     Another NodeCDLL object to be concatenated to this list
         */
        /*private*/ void join(NodeCDLL other) {
            if (other != null && other.size != 0) {
                this.tail.next = other.head;
                other.head.prev = this.tail;
                this.tail = other.tail;
                head.prev = tail;
                tail.next = head;

                this.size += other.size;
            }
        }

        /**
         * Removes a provided heap node from the list. <br>
         *
         * Precondition - the provided node is in this NodeCDLL.<br>
         *
         * This method runs in O(1) time.
         *
         * @param removed
         */
        /*private*/ void remove(HeapNode removed) {
            if (removed == head) {
                if (size != 1)
                    head = removed.next;
                else
                    head = null;
            }
            if (removed == tail) {
                if (size != 1)
                    tail = removed.prev;
                else
                    tail = null;
            }
            if (size != 1) {
                HeapNode prev = removed.prev;
                HeapNode next = removed.next;
                prev.next = next;
                next.prev = prev;
            }
            removed.next = null;
            removed.prev = null;
            size--;
        }

        /****************************/
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            for (HeapNode node : this) {
                sb.append(node.key);
                if (node != this.tail)
                    sb.append(", ");
            }
            sb.append("]");
            return sb.toString();
        }


        @Override
        public Iterator iterator() {
            return new nodeCDLLIterator();
        }

        /*private*/ class nodeCDLLIterator implements Iterator<HeapNode> {
            boolean done = false;
            HeapNode curr;

            @Override
            public boolean hasNext() {
                if (head == null)
                    done = true;
                return (! done);
            }

            @Override
            public HeapNode next() {
                if (curr == null)
                    curr = head;
                else
                    curr = curr.next;
                if (curr == tail)
                    done = true;
                return curr;
            }
        }
    }
}
