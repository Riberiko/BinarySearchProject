package TreePackage;

/**
 *
 * This is a Binary Tree optimized for searching operations
 *
 * @author  the one before riberiko niyomwungere
 * @author  Riberiko Niyomwungere
 *
 * @version 1.0
 */

import java.util.*;

public class BinarySearchTree<T extends Comparable<? super T>>
        extends BinaryTree<T> implements SearchTreeInterface<T>
{
    private final static int COUNT = 5;

    public BinarySearchTree()
    {
        super();
    } // end default constructor

    public BinarySearchTree(T rootEntry)
    {
        super();
        setRootNode(new BinaryNode<T>(rootEntry));
    } // end constructor

    // Disable setTree (see Segment 26.6)
    public void setTree(T rootData, BinaryTreeInterface<T> leftTree,
                        BinaryTreeInterface<T> rightTree)
    {
        throw new UnsupportedOperationException();
    } // end setTree

    public T getEntry(T anEntry)
    {
        return findEntry(getRootNode(), anEntry);
    } // end getEntry

    private T findEntry(BinaryNode<T> rootNode, T anEntry)
    {
        T result = null;

        if (rootNode != null)
        {
            T rootEntry = rootNode.getData();

            if (rootEntry != null && anEntry.equals(rootEntry))
                result = rootEntry;
            else if (rootEntry != null && anEntry.compareTo(rootEntry) < 0)     //Riko changed this to deal with null values
                result = findEntry(rootNode.getLeftChild(), anEntry);
            else if (rootEntry != null && anEntry.compareTo(rootEntry) > 0)     //Riko changed this to deal with null values
                result = findEntry(rootNode.getRightChild(), anEntry);
        } // end if

        return result;
    } // end findEntry

    public boolean contains(T entry)
    {
        return getEntry(entry) != null;
    } // end contains

    public T add(T newEntry)
    {
        T result = null;

        if (isEmpty())
            setRootNode(new BinaryNode<>(newEntry));
        else
            result = addEntry(getRootNode(), newEntry);

        return result;
    } // end add

    // Adds anEntry to the nonempty subtree rooted at rootNode.
    //updated to it sets the parents by riberiko niyomwungere
    private T addEntry(BinaryNode<T> rootNode, T anEntry)
    {
        // Assertion: rootNode != null
        T result = null;
        int comparison = anEntry.compareTo(rootNode.getData());

        if (comparison == 0)
        {
            result = rootNode.getData();
            rootNode.setData(anEntry);
        }
        else if (comparison < 0)
        {
            if (rootNode.hasLeftChild())
                result = addEntry(rootNode.getLeftChild(), anEntry);
            else {
                rootNode.setLeftChild(new BinaryNode<>(anEntry));
            }
        }
        else
        {
            // Assertion: comparison > 0

            if (rootNode.hasRightChild())
                result = addEntry(rootNode.getRightChild(), anEntry);
            else {
                rootNode.setRightChild(new BinaryNode<>(anEntry));
            }
        } // end if

        return result;
    } // end addEntry

    /**
     * creates an empty binary search tree and populates it with the data
     * form array
     * @param list  list to add
     */
    public void addArray(List<T> list){
        setRootNode(new BinaryNode<>());
        addArray(list, getRootNode());
    }

    /**
     * creates an empty binary search tree and populates it with the data
     * form array
     *
     * @param list  list to add
     * @param node  current head node
     */
    private void addArray(List<T> list, BinaryNode node){
        if(addArrayEmpty(list) || node == null) return;
        if(node.getData() == null){
            node.setData(list.get(list.size()/2));
            list.set(list.size()/2, null);
        }
        if(!addArrayEmpty(list)) {
            node.setLeftChild(new BinaryNode<T>());
            addArray(list.subList(0, list.size() / 2), node.getLeftChild());
        }
        if(!addArrayEmpty(list)) {
            node.setRightChild(new BinaryNode<T>());
            addArray(list.subList(list.size() / 2, list.size()), node.getRightChild());
        }

    }

    /**
     * Get methods that returns the item in the tree that corresponds to inorder
     *
     * @param index     the index to make this feel like an array
     * @return  the node found at that index
     */
    public T get(int index){
        assert (index < getNumberOfNodes() && index >= 0);
        Iterator iter = getInorderIterator();
        while (index != 0){
            iter.next();
            index -= 1;
        }
        return (T) iter.next();
    }

    /**
     *
     * @param list  you want to check if empty
     * @return  true only when empty
     */
    private boolean addArrayEmpty(List<T> list){
        for(int i = 0; i < list.size(); i++){
            if(list.get(i) != null) return false;
        }
        return true;
    }

    public T remove(T anEntry)
    {
        ReturnObject oldEntry = new ReturnObject(null);
        BinaryNode<T> newRoot = removeEntry(getRootNode(), anEntry, oldEntry);
        setRootNode(newRoot);

        return oldEntry.get();
    } // end remove

    // Removes an entry from the tree rooted at a given node.
    // Parameters:
    //    rootNode A reference to the root of a tree.
    //    anEntry  The object to be removed.
    //    oldEntry An object whose data field is null.
    //    Returns: The root node of the resulting tree; if anEntry matches
    //             an entry in the tree, oldEntry's data field is the entry
    //             that was removed from the tree; otherwise it is null.
    private BinaryNode<T> removeEntry(BinaryNode<T> rootNode, T anEntry,
                                      ReturnObject oldEntry)
    {
        if (rootNode != null)
        {
            T rootData = rootNode.getData();
            int comparison = anEntry.compareTo(rootData);

            if (comparison == 0)       // anEntry == root entry
            {
                oldEntry.set(rootData);
                rootNode = removeFromRoot(rootNode);
            }
            else if (comparison < 0)   // anEntry < root entry
            {
                BinaryNode<T> leftChild = rootNode.getLeftChild();
                BinaryNode<T> subtreeRoot = removeEntry(leftChild, anEntry, oldEntry);
                rootNode.setLeftChild(subtreeRoot);
            }
            else                       // anEntry > root entry
            {
                BinaryNode<T> rightChild = rootNode.getRightChild();
                // A different way of coding than for left child:
                rootNode.setRightChild(removeEntry(rightChild, anEntry, oldEntry));
            } // end if
        } // end if

        return rootNode;
    } // end removeEntry

    // Removes the entry in a given root node of a subtree.
    // rootNode is the root node of the subtree.
    // Returns the root node of the revised subtree.
    private BinaryNode<T> removeFromRoot(BinaryNode<T> rootNode)
    {
        // Case 1: rootNode has two children
        if (rootNode.hasLeftChild() && rootNode.hasRightChild())
        {
            // Find node with largest entry in left subtree
            BinaryNode<T> leftSubtreeRoot = rootNode.getLeftChild();
            BinaryNode<T> largestNode = findLargest(leftSubtreeRoot);

            // Replace entry in root
            rootNode.setData(largestNode.getData());

            // Remove node with largest entry in left subtree
            rootNode.setLeftChild(removeLargest(leftSubtreeRoot));
        } // end if

        // Case 2: rootNode has at most one child
        else if (rootNode.hasRightChild())
            rootNode = rootNode.getRightChild();
        else
            rootNode = rootNode.getLeftChild();

        // Assertion: If rootNode was a leaf, it is now null

        return rootNode;
    } // end removeFromRoot

    // Finds the node containing the largest entry in a given tree.
    // rootNode is the root node of the tree.
    // Returns the node containing the largest entry in the tree.
    private BinaryNode<T> findLargest(BinaryNode<T> rootNode)
    {
        if (rootNode.hasRightChild())
            rootNode = findLargest(rootNode.getRightChild());

        return rootNode;
    } // end findLargest

    // Removes the node containing the largest entry in a given tree.
    // rootNode is the root node of the tree.
    // Returns the root node of the revised tree.
    private BinaryNode<T> removeLargest(BinaryNode<T> rootNode)
    {
        if (rootNode.hasRightChild())
        {
            BinaryNode<T> rightChild = rootNode.getRightChild();
            rightChild = removeLargest(rightChild);
            rootNode.setRightChild(rightChild);
        }
        else
            rootNode = rootNode.getLeftChild();

        return rootNode;
    } // end removeLargest

    /**
     *Determines if the tree is a binary search tree
     *
     * @return
     */
    public boolean isBST(){
        return(isBST(getRootNode()));
    }

    /**
     * Determins if the node passed in is a binary search optimal
     *
     * @param node  to check
     * @return  true if it is
     */
    private boolean isBST(BinaryNode node){
        if(node == null) return true;
        boolean left = true;
        boolean right = true;
        if(node.getLeftChild() != null){
            left = isBST(node.getLeftChild()) && testLeft( (T) node.getLeftChild().getData(), (T) node.getData());
        }
        if(node.getRightChild() != null){
            right = isBST(node.getRightChild()) && testRight( (T) node.getRightChild().getData(), (T) node.getData());
        }
        return left && right;
    }

    /**
     *The check for the left node to see if it is binary search optimal
     *
     * @param leftValue value for the left node
     * @param val   value for the head node
     * @return  true if left is less than  or equal to head
     */
    private boolean testLeft(T leftValue, T val){
        if(leftValue == null) return true;
        if(leftValue.compareTo(val) == 0) return true;
        if(leftValue.compareTo(val) < 0) return true;
        return false;
    }

    /**
     *The check for the right node to see if it is binary search optimal
     *
     * @param rightVal value for the right node
     * @param val   value for the head node
     * @return  true if right is greater than head node
     */
    private boolean testRight(T rightVal, T val){
        if(rightVal.compareTo(val) > 0) return true;
        return false;
    }

    /**
     *Determines if the tree is balanced
     *
     * @return true if it is balanced
     */
    public boolean isBalanced(){
        return isBalanced(getRootNode());
    }

    /**
     *Determines if the node is balanced
     *
     * @param root  the node to check
     * @return  true if it is balanced
     */
    private boolean isBalanced(BinaryNode<T> root) {
        if (root == null) {
            return true;
        }

        int leftHeight = getHeight(root.getLeftChild());
        int rightHeight = getHeight(root.getRightChild());

        if (isBalanced(root.getLeftChild()) && isBalanced(root.getRightChild()) && (Math.abs(leftHeight - rightHeight) <= 1)) {
            return true;
        }

        return false;
    }

    /**
     * Returns the height of the node
     *
     * @param root  the node to check
     * @return  the height level for the node
     */
    private int getHeight(BinaryNode<T> root) {
        if (root == null) {
            return -1;
        }

        int leftHeight = getHeight(root.getLeftChild());
        int rightHeight = getHeight(root.getRightChild());
        int height = Math.max(leftHeight, rightHeight) + 1;

        return height;
    }

    /**
     * Prints the tree out
     *
     * @param root  the node of the tree to print
     * @param space the number spaces desired
     */
    private void print2DUtil(BinaryNode<T> root, int space)
    {
        // Base case
        if (root == null) return;

        // Increase distance between levels
        space += COUNT;

        // Process right child first
        print2DUtil(root.getRightChild(), space);

        // Print current node after space
        // count
        System.out.print("\n");
        for (int i = COUNT; i < space; i++)
            System.out.print(" ");
        System.out.print("{ "+root.getData() + " }\n");

        // Process left child
        print2DUtil(root.getLeftChild(), space);
    }

    /**
     *Prints out the binary tree
     */
    public void print2D()
    {
        // Pass initial space count as 0
        print2DUtil(getRootNode(), 0);
    }



    private class ReturnObject
    {
        private T item;

        private ReturnObject(T entry)
        {
            item = entry;
        } // end constructor

        public T get()
        {
            return item;
        } // end get

        public void set(T entry)
        {
            item = entry;
        } // end set
    } // end ReturnObject
} // end BinarySearchTree