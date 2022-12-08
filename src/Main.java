import ParserPackage.Parser;

import java.util.Iterator;

public class Main {
    public static void main(String[] args) {
        Parser temp = new Parser("res/reservedWords.txt", "res/reservedWords.txt");


        System.out.println("Reserved KeyWords:\n-------------------------\n");
        temp.getReservedWordsTree().print2D();
        System.out.println("\n-------------------------\nIs this balances? : "+ temp.getReservedWordsTree().isBalanced());
        System.out.println("Is this a Binary Search Tree? : "+ temp.getReservedWordsTree().isBST());

        System.out.println("\nUserDefined KeyWords:\n-------------------------\n");
        temp.getUserWordsTree().print2D();
        System.out.println("\n-------------------------\nIs this balances? : "+ temp.getUserWordsTree().isBalanced());
        System.out.println("Is this a Binary Search Tree? : "+ temp.getUserWordsTree().isBST());

        System.out.println("Reserved KeyWords found in user file: \n");
        Iterator<String> iter = temp.getUserWordsTree().getInorderIterator();
        while(iter.hasNext()){
            String pointer = iter.next();
            if(pointer != null && temp.getReservedWordsTree().contains(pointer)) System.out.println(pointer);
        }
    }
}
