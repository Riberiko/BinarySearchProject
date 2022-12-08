import ParserPackage.Parser;

import java.util.Iterator;

public class Main {
    public static void main(String[] args) {
        Parser temp = new Parser("res/reservedWords.txt", "res/Palindrome.java");

        System.out.println("Reserved KeyWords found in user file: \n");
        Iterator<String> iter = temp.getUserWordsTree().getInorderIterator();
        while(iter.hasNext()){  //prints out reserved keywords that are found in the user file
            String pointer = iter.next();
            if(pointer != null && temp.getReservedWordsTree().contains(pointer)) System.out.println(pointer);
        }
        System.out.println("\nUser Defined words (not in the reserved file): \n");
        iter = temp.getUserWordsTree().getInorderIterator();
        while(iter.hasNext()){  //prints out the user keywords that are not found int the user file
            String pointer = iter.next();
            if(pointer != null && !temp.getReservedWordsTree().contains(pointer)) System.out.println(pointer);
        }


        //Prints out all the trees
        System.out.println("\nReserved KeyWords File Tree:-------------------------");
        temp.getReservedWordsTree().print2D();
        System.out.println("\n-------------------------\nIs this balances? : "+ temp.getReservedWordsTree().isBalanced());
        System.out.println("Is this a Binary Search Tree? : "+ temp.getReservedWordsTree().isBST());

        System.out.println("\nUserDefined KeyWords File Tree:\n-------------------------");
        temp.getUserWordsTree().print2D();
        System.out.println("\n-------------------------\nIs this balances? : "+ temp.getUserWordsTree().isBalanced());
        System.out.println("Is this a Binary Search Tree? : "+ temp.getUserWordsTree().isBST());
    }
}
