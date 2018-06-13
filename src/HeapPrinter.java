//import java.util.ArrayList;
//
//public class HeapPrinter {
//    // NOTE: it prints `toString()` of HeapNode, so change it to print the key or something.
//
//    // NOTE: implement this function (if no children, return EMPTY LIST)
//    /*private*/ static ArrayList<FibonacciHeap.HeapNode> getChildren(/*notnull*/ FibonacciHeap.HeapNode parent) {
//        ArrayList<FibonacciHeap.HeapNode> list = new ArrayList<>();
//        if (parent.getChildren().getSize() != 0) {
//            for (FibonacciHeap.HeapNode child: parent.getChildren()) {
//                list.add(child);
//            }
//        }
//        return list;
//    }
//
//    // CALL THIS IN YOUR CODE
//    /*private*/ static String toString(FibonacciHeap.HeapNode node) {
//        StringBuilder sb = new StringBuilder();
//        generateGenericVerbose(node, "", false, false, sb);
//        return sb.toString();
//    }
//
//    private static void generateGenericVerbose(/*notnull*/ FibonacciHeap.HeapNode node, String prefix, boolean isRightMost, boolean isLeftMost, StringBuilder sb) {
//        ArrayList<FibonacciHeap.HeapNode> children = getChildren(node);
//        int halfSize = (children.size() + 1) / 2;
//        for (int i = children.size() - 1; i >= halfSize; i--) {
//            FibonacciHeap.HeapNode child = children.get(i);
//            generateGenericVerbose(child,
//                    prefix + (isRightMost && !isLeftMost ? "    " : "│   "),
//                    i == children.size() - 1,
//                    i == 0,
//                    sb);
//        }
//        sb.append(prefix).
//                append(isRightMost && isLeftMost ? "└── " : "").
//                append(isRightMost && !isLeftMost ? "┌── " : "").
//                append(isLeftMost && !isRightMost ? "└── " : "").
//                append(!isRightMost && !isLeftMost ? "├── " : "").
//                append(node.toString()).
//                append("\n");
//        for (int i = halfSize - 1; i >= 0; i--) {
//            FibonacciHeap.HeapNode child = children.get(i);
//            generateGenericVerbose(child,
//                    prefix + (isLeftMost ? "    " : "│   "),
//                    i == children.size() - 1,
//                    i == 0,
//                    sb);
//        }
//    }
//}
