package ParserPackage;

import TreePackage.BinarySearchTree;

import java.util.*;

public class Parser implements ParserInterface{

    private Scanner scan;

    private final String reservedWordsPath;
    private final String userWordsPath;



    private BinarySearchTree<String> reservedWords;     //reserved words
    private BinarySearchTree<String> userIdentifiers;   //userdefined words

    public Parser(){
        this("res/reservedWords.txt", "res/Palindrome.java");
    }

    public Parser(String reservedWordsPath, String userWordsPath){

        reservedWords = new BinarySearchTree<>();
        userIdentifiers = new BinarySearchTree<>();

        this.userWordsPath = userWordsPath;
        this.reservedWordsPath = reservedWordsPath;

        try {
            scan = new Scanner(new java.io.File(reservedWordsPath));
            initializeReservedWords();
        }catch (java.io.FileNotFoundException e){
            System.err.println("Could not find the Reserved Word File");
            System.exit(0); //Terminates the program
        }

        initializeUserWords(userWordsPath);


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
                if(!container.contains(str)) container.add(str);
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
                val = val.replace("(", "");
                val = val.replace(")", "");


                if( val != null && !val.isEmpty() && !container.contains(val) ) {
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
    public void setBalancedBTS(String path, List<String> words) {

        System.out.println(words);

        if(path.equals(userWordsPath)){
            userIdentifiers.addArray(words);
        }
        if(path.equals(reservedWordsPath)){
            reservedWords.addArray(words);
        }
    }

    /**
     *Determins if a string is a digit
     *
     * @param val   string you want to test
     * @return  true only when val is digit
     */
    private boolean isDigit(String val){
        try{
            Double.parseDouble(val);
        }catch (NumberFormatException e){
            return false;
        }
        return true;
    }

    /**
     * {{@inheritDoc}}
     */
    @Override
    public BinarySearchTree<String> getReservedWordsTree() {
        return reservedWords;
    }

    /**
     * {{@inheritDoc}}
     */
    @Override
    public BinarySearchTree<String> getUserWordsTree(){
        return userIdentifiers;
    }

}
