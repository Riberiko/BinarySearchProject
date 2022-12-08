package ParserPackage;

import TreePackage.BinarySearchTree;

import java.util.List;

public interface ParserInterface {

    /**
     * Read in the file of reserved words and put them into your reserved words data structure. Then use setBalancedBST to set up a balanced BST from them.
     */
    public void initializeReservedWords();


    /**
     * Add a reserved word and ensure binary search tree is balanced.
     */
    public void setBalancedBTS(String userWordsPath, List<String> words);


    /**
     * Retrieves a binary search tree that contains users words
     *
     * @return a Binary Search Tree of String
     */
    public BinarySearchTree<String> getUserWordsTree();

    /**
     * Retrieves a binary search tree that contains reserved words
     *
     * @return  a Binary Search Tree of String
     */
    public BinarySearchTree<String> getReservedWordsTree();
}
