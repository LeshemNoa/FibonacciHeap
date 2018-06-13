import java.util.Iterator;

/**
 * Rimon Shy Rimonshy 302989215
 * Noa Leshem noaleshem1 205916869
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
     * The current number of marked nodes in the heap.
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

    /**
     * Removes the node with the current minimal key in the heap. <br>
     *
     * This method's runtime complexity is determined by the complexity of the
     * consolidation process during which the new minimal node is found. Consolidate
     * runs in O(logn) amortized time, hence deleteMin runs in O(logn) amortized time
     * as well.
     */
   public void deleteMin() {
       HeapNode node = min;
       if (node != null) {
           NodeCDLL children = node.getChildren();
           if (children.getSize() != 0) {
               for (HeapNode child : node.getChildren()) {
                   child.parent = null;
               }
               roots.join(children);
           }
           roots.remove(min);

           if (roots.getSize() == 0) {
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

    /**
     * Reduces the number of trees in the heap and finds the node with the minimal
     * key in the heap. <br>
     *
     * During the consolidation process, an auxiliary array is used to sort the trees
     * in the heap by their ranks. The size of the array is chosen to be the highest
     * possible tree rank that may exists in a heap of the current size, which is O(logn).
     * The bound is calculated by the rankUpperBound method. <br>
     *
     * This method runs in O(logn) amortized time. <br>
     */
   private void consolidate(){
        HeapNode[] rankArray = new HeapNode[rankUpperBound(size)];
        int n = roots.getSize();
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

    /**
     * Links two trees of the same rank to form one tree whose rank is greater
     * by 1.<br>
     *
     * This method runs in O(1) time as it only requires changing a fixed number
     * of nodes.
     *
     * @param parent        The root node which will be placed as the root of the resulting
     *                      tree.
     * @param child         The root node which will be added as a child of the new root node.
     * @return              The root of the new tree.
     */
    private HeapNode link(HeapNode parent, HeapNode child){
        roots.remove(child);
        parent.getChildren().insert(child);
        child.parent = parent;
        parent.rank++;
        child.mark = false;
        FibonacciHeap.totalLinks++;

        return parent;
    }

    /**
     * Finds the node with the minimal key in the heap. <br>
     *
     * Runs in O(1) time as it returns a field.
     *
     * @return      HeapNode object with the smallest key in the heap.
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
        if (heap2 == null || heap2.roots.getSize() == 0) {
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
     * Returns the size of the heap. <br>
     *
     * Runs in O(1) time as it returns a field.
     *
     * @return      The number of nodes in the heap.
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
            int[] counts = {roots.getSize()};
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

    /**
     * Removed the provided HeapNode object from the heap. <br>
     *
     * This method decreases the key of the node to be the smaller than the
     * current minimal key, and then calls deleteMin.
     *
     * @param x     The node to be removed from the heap
     */
    public void delete(HeapNode x) 
    {
        if (x == null) {
            return;
        }

        decreaseKeyTo(x, min.key - 10);
        deleteMin();
    }

    /**
     * Decreases the key of a heap node's key by a provided integer delta.<br>
     *
     * Calls a more general function, decreaseKeyTo, which sets the node's
     * key to the provided value rather than reducing it by a given delta. <br>
     *
     * Hence, decreaseKey's runtime complexity is determined by that of
     * decreaseKeyTo, which runs in O(1) amortized time. <br>
     *
     * @param x         HeapNode object whose key is to be decreased
     * @param delta     The number to be reduced from x's key
     */
    public void decreaseKey(HeapNode x, int delta) {
        decreaseKeyTo(x, x.key - delta);
    }

    /**
     * Sets the key of the provided node to be the given integer. <br>
     *
     * If the heap rule is broken after the new key is set, that node
     * and its subtree are cut from the tree in which they are currently,
     * and the subtree is added to the list of trees in the heap. If the
     * node that has been cut was a child of a node whose child was cut
     * once before, a sequence of cascading cuts is triggered. <br>
     *
     * This method runs in O(1) amortized time, as the cut and cascadingCut
     * methods run in constant amortized time.<br>
     *
     * @param x         HeapNode object whose key is to be decreased
     * @param newKey    The new key to be given to the node
     */
    private void decreaseKeyTo(HeapNode x, int newKey) {
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

    /**
     * Removed the provided node from as its parent's child, adding its
     * subtree to the list of trees in the heap. <br>
     *
     * This method runs in O(1) time.
     *
     * @param x     The node to be cut off from its parent and added
     *              to the root list.
     */
    private void cut(HeapNode x) {
        HeapNode parent = x.parent;
        parent.getChildren().remove(x);
        parent.rank--;

        roots.insert(x);
        x.parent = null;

        x.mark = false;
        marked--;
        totalCuts++;
    }

    /**
     * Implementation of the Cascading Cuts algorithm. <br>
     *
     * Cascading cuts are triggered when a child of a marked node is cut during
     * the decrease key process. Child nodes are cut recursively up the path
     * from the first node that was cut to the root, until a child is cut from
     * an unmarked node or if the root is reached. <br>
     *
     * This method runs in O(logn) amortized time.
     *
     * @param curr      The parent node whose child was cut.
     */
    private void cascadingCut(HeapNode curr) {
        HeapNode parent = curr.parent;
        if (parent != null) {
            if (!curr.mark) {
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
    	return (roots.getSize() + 2*marked);
    }

    /**
     * Returns the total number of links that occurred during the deleteMin process
     * since the class was first launched. <br>
     *
     * Runs in O(1) time as it returns a static field.
     *
     * @return      The accumulated number of links that occurred during the run
     *              of the class.
     */
    public static int totalLinks()
    {    
    	return totalLinks;
    }

    /**
     * Returns the total number of cuts that occurred during the decreaseKey process
     * since the class was first launched. <br>
     *
     * Runs in O(1) time as it returns a static field.
     *
     * @return      The accumulated number of cuts that occurred during the run
     *              of the class.
     */
    public static int totalCuts()
    {    
    	return totalCuts;
    }

    /**
     * Represents an node in the heap. Contains an integer key.
     */
    public class HeapNode {

        private int key;
        /**
         * The number of children this node has.
         */
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
         * The children of the node, represented by a NodeCDLL object.
         */
       private NodeCDLL children;
        /**
         * The node's right sibling.<br>
         *
         * If the node is the rightmost of its parent's children, its next node is the
         * leftmost one, as the structure is doubly linked.
         */
        private HeapNode next;
        /**
         * The node's left sibling.<br>
         *
         * If the node is the leftmost of its parent's children, its prev node is the
         * rightmost one one, as the structure is doubly linked.
         */
        private HeapNode prev;
        /**
         * The node's parent node, or null if it's a root of a tree in the heap.
         */
        private HeapNode parent;

        /**
         * A constructor for the HeapNode class. <br>
         *
         * Sets the provided key as the key of the node created, and initialized
         * an empty NodeCDLL object to be set as the node's list of children.
         *
         * @param key       The key to be associated with the new node.
         */
        public HeapNode(int key) {
            this.key = key;
            this.children = new NodeCDLL();
        }

        /**
         * Returns the key of this node object.
         *
         * @return      The key of this node.
         */
        public int getKey() {
            return this.key;
        }

        /**
         * Returns this node's children.
         *
         * @return      The list of children of this node
         */
        private NodeCDLL getChildren() {
            return children;
        }
    }

    /**
     * Represents a circular doubly linked list of HeapNode objects.
     */
    public class NodeCDLL implements Iterable<HeapNode> {
        /**
         * The first node in the list.
         */
        private HeapNode head;
        /**
         * The last node in the list.
         */
        private HeapNode tail;
        private int size;

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
        private HeapNode insert(int key) {
            HeapNode node = new HeapNode(key);
            insert(node);
            return node;
        }

        /**
         * Inserts an existing node object at the end of the list, setting it to be its
         * new tail. <br>
         *
         * This method runs in O(1) time.
         *
         * @param node      The new node to be added to this list.
         */
        private void insert(HeapNode node) {
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
            size = getSize() + 1;
        }

        /**
         * Joins two node lists by concatenating another list to the current one. <br>
         *
         * The circular structure of the list is maintained. This method runs in O(1) time.
         *
         * @param other     Another NodeCDLL object to be concatenated to this list
         */
        private void join(NodeCDLL other) {
            if (other != null && other.getSize() != 0) {
                this.tail.next = other.head;
                other.head.prev = this.tail;
                this.tail = other.tail;
                head.prev = tail;
                tail.next = head;

                this.size = this.getSize() + other.getSize();
            }
        }

        /**
         * Removes a provided heap node from the list. <br>
         *
         * Precondition - the provided node is in this NodeCDLL.<br>
         *
         * This method runs in O(1) time.
         *
         * @param removed       The node to be removed from the list.
         */
        private void remove(HeapNode removed) {
            if (removed == head) {
                if (getSize() != 1)
                    head = removed.next;
                else
                    head = null;
            }
            if (removed == tail) {
                if (getSize() != 1)
                    tail = removed.prev;
                else
                    tail = null;
            }
            if (getSize() != 1) {
                HeapNode prev = removed.prev;
                HeapNode next = removed.next;
                prev.next = next;
                next.prev = prev;
            }
            removed.next = null;
            removed.prev = null;
            size = getSize() - 1;
        }

        /**
         * Returns an iterator for the NodeCDLL. <br>
         *
         * @return      A NodeCDLLIterator object for this list.
         */
        @Override
        public Iterator iterator() {
            return new nodeCDLLIterator();
        }

        /**
         * The number of HeapNodes in the list.
         */
        public int getSize() {
            return size;
        }

        /**
         * An iterator for the NodeCDLL class.
         */
        private class nodeCDLLIterator implements Iterator<HeapNode> {
            /**
             * True if all the nodes in the list have been retuned by the next
             * method once already, implying the iterator is exhausted, otherwise
             * false.
             */
            boolean done = false;
            /**
             * The current HeapNode object to be returned in the next call to next().
             */
            HeapNode curr;

            /**
             * Checks if the iterator has been exhausted, having returned each node once.
             *
             * @return      True if iterator has not yet finished iterating over the list,
             *              otherwise false.
             */
            @Override
            public boolean hasNext() {
                if (head == null)
                    done = true;
                return (! done);
            }

            /**
             *
             * @return      The next node in the list.
             */
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


