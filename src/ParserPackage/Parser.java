package ParserPackage;

import TreePackage.BinarySearchTree;

import java.util.*;

public class Parser implements ParserInterface{

    private Scanner scan;

    private String reservedWordsPath;
    private String userWordsPath;



    private BinarySearchTree<String> reservedWords;     //reserved words
    private BinarySearchTree<String> userIdentifiers;   //userdefined words

    private ArrayList<String> reservedTypes;

    public Parser(){
        this("res/reservedWords.txt", "res/Palindrome");
    }

    public Parser(String reservedWordsPath, String userWordsPath){

        reservedWords = new BinarySearchTree<>();
        userIdentifiers = new BinarySearchTree<>();

        try {
            scan = new Scanner(new java.io.File(reservedWordsPath));
            initializeReservedWords();
        }catch (java.io.FileNotFoundException e){
            System.err.println("Could not find the Reserved Word File");
            System.exit(0); //Terminates the program
        }

        reservedTypes = new ArrayList<>();

        initializeUserWords("res/nums.txt");


    }

    /**
     * {{@inheritDoc}}
     */
    @Override
    public void initializeReservedWords() {

        List<String> container = new ArrayList<>();

        while(scan.hasNextLine()){

            Scanner temp = new Scanner(scan.nextLine());

            while(temp.hasNext()){
                String str = temp.next();
                container.add(str);
            }
        }
        Collections.sort(container);

        setBalancedBTS(reservedWordsPath, container);

    }

    public void initializeUserWords(String userProgramPath){

        Scanner user;
        List<String> container = new ArrayList<>();

        try {
            user = new Scanner(new java.io.File(userProgramPath));
        }catch (java.io.FileNotFoundException e){
            System.err.println("Program File was not Found");
            System.exit(0);     //Terminates the program
            return;     //if user is not defined || for java
        }

        while(user.hasNextLine()){
            Scanner temp = new Scanner(user.nextLine());
            while(temp.hasNext()){
                String val = temp.next();

                if(val.contains(".")) val = "";

                //removing punctuation and math symbols
                val = val.replace(";","");
                val = val.replace("{","");
                val = val.replace("}","");
                val = val.replace("=", "");
                val = val.replace("-", "");
                val = val.replace("+", "");
                val = val.replace("/", "");
                val = val.replace("*", "");
                val = val.replace(">", "");
                val = val.replace("<", "");

                if(!reservedWords.contains(val) && !val.isEmpty() && !userIdentifiers.contains(val)) {
                    container.add(val);
                }
            }
        }

        Collections.sort(container);
        setBalancedBTS(userWordsPath, container);

    }

    /**
     * {{@inheritDoc}}
     */
    @Override
    public void setBalancedBTS(String userDefinedWord, List<String> words) {
    }

    /**
     * {{@inheritDoc}}
     */
    @Override
    public BinarySearchTree<String> getReservedWordsTree() {
        return reservedWords;
    }

    @Override
    public BinarySearchTree<String> getUserWordsTree(){
        return userIdentifiers;
    }

}
