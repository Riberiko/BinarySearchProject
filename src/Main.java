import ParserPackage.Parser;

public class Main {
    public static void main(String[] args) {
        Parser temp = new Parser("res/reservedWords.txt", "res/nums.txt");


        System.out.println("Reserved KeyWords:\n");
        temp.getReservedWordsTree().print2D();
        System.out.println("Is this balances? : "+ temp.getReservedWordsTree().isBalanced());
        System.out.println("Is this a Binary Search Tree? : "+ temp.getReservedWordsTree().isBST());

        System.out.println("UserDefined KeyWords:\n");
        temp.getUserWordsTree().print2D();
        System.out.println("Is this balances? : "+ temp.getUserWordsTree().isBalanced());
        System.out.println("Is this a Binary Search Tree? : "+ temp.getUserWordsTree().isBST());
    }
}
