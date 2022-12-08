package ParserPackage;

/**
 * This classs parses out
 *
 * @author  Riberiko Niyomwungere
 * @version 1.0
 */

import TreePackage.BinarySearchTree;

import java.util.*;

public class Parser implements ParserInterface{

    private Scanner scan;   //scanner tool

    private final String reservedWordsPath;     //used for test
    private final String userWordsPath;         //used for test



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
            initializeReservedWords();      //initializes the Reserved tree
        }catch (java.io.FileNotFoundException e){
            System.err.println("Could not find the Reserved Word File");    //prints error message if the file is not found
            System.exit(0); //Terminates the program
        }

        initializeUserWords(userWordsPath);     //initialises the User tree


    }

    /**
     * {{@inheritDoc}}
     */
    @Override
    public void initializeReservedWords() {

        List<String> container = new ArrayList<>();

        while(scan.hasNextLine()){  //scans each line

            Scanner temp = new Scanner(scan.nextLine());

            while(temp.hasNext()){  //scans that line for words that
                String str = temp.next();
                if(!container.contains(str)) container.add(str);    //if they are not already int the list it adds them
            }
        }
        Collections.sort(container);    //sorts the list

        setBalancedBTS(reservedWordsPath, container);       //sets up the resereved tree with the words found in file

    }

    /**
     * initalizes the user defined words tree and also populates it with data
     *
     * @param userProgramPath   the path for the file you want to use
     */
    public void initializeUserWords(String userProgramPath){

        Scanner user;
        List<String> container = new ArrayList<>();     //list to hold words

        try {
            user = new Scanner(new java.io.File(userProgramPath));
        }catch (java.io.FileNotFoundException e){
            System.err.println("Program File was not Found");   //Prints out error method if the file is not found
            System.exit(0);     //Terminates the program
            return;     //if user is not defined || for java
        }

        while(user.hasNextLine()){      //scans the next line
            Scanner temp = new Scanner(user.nextLine());
            while(temp.hasNext()){      //scans that line for words
                String val = temp.next();

                if(val.contains(".")) val = "";                 //removes and objects calls

                //removing punctuation and math symbols
                val = val.replace(";","");      //removes ;
                val = val.replace("{","");      //removes {
                val = val.replace("}","");      //removes }
                val = val.replace("=", "");     //removes =
                val = val.replace("-", "");     //removes -
                val = val.replace("+", "");     //removes +
                val = val.replace("/", "");     //removes /
                val = val.replace("*", "");     //removes *
                val = val.replace(">", "");     //removes >
                val = val.replace("<", "");     //removes <
                val = val.replace("(", "");     //removes (
                val = val.replace(")", "");     //removes )
                val = val.replace("\"", "");    //removes "
                val = val.replace("\'", "");    //removes '

                if( val != null && !val.isEmpty() && !isDigit(val) && !container.contains(val) && !userIdentifiers.contains(val)) {
                    container.add(val);     //adds to the list if the value is not null or empty or a digit or it is not already contained in the list
                }
            }
        }

        Collections.sort(container);    //sorts
        setBalancedBTS(userWordsPath, container);   //creates the binary tree

    }

    /**
     * {{@inheritDoc}}
     */
    @Override
    public void setBalancedBTS(String path, List<String> words) {

        if(path.equals(userWordsPath)){     //only if the path matched what the class know to be the path ofr the user file
            userIdentifiers.addArray(words);
        }
        if(path.equals(reservedWordsPath)){     //only if the path matches what the class know to be thr path for the reserved file
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
            Double.parseDouble(val);    //this tries to get a number value form the string that is passed in
        }catch (NumberFormatException e){
            return false;   //if it could not get a number value from the string
        }
        return true;    //if it managed to get a number form the string
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
