import Methods.InfixPostFixMethods;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Hashtable;
import java.util.Scanner;

public class Tester {
    public static void main(String[] args) throws FileNotFoundException {

        File keyValues = new File("hw5_input2.txt");
        File expressionList = new File ("hw5_input3.txt");
        File outputFile = new File ("hw5_output.txt");

        Scanner scan = null;
        PrintStream output;
        Hashtable<Character,String> keyTable = new Hashtable<>();

        try {
            scan = new Scanner(keyValues);

        }catch(FileNotFoundException e){
            System.out.println("file not found!");
        }

        while(scan.hasNextLine()){
            String data = scan.nextLine().trim();
            String[] elements = data.split(" = ");
            keyTable.put(elements[0].charAt(0),elements[1]);
        }
        scan.close();

        try{
            scan = new Scanner(expressionList);

        }catch(FileNotFoundException e){
            throw new FileNotFoundException("file not found!");
        }

        try{
            output = new PrintStream(outputFile);

        }catch(FileNotFoundException e){
            throw new FileNotFoundException("output file not found or could not be created!");
        }

        InfixPostFixMethods postfix = new InfixPostFixMethods();

        while (scan.hasNextLine()) {

            String infix = scan.nextLine();
            String pfix = postfix.infixToPostfix(infix);

            if (pfix.contains("Error")) {
                output.println(infix + " --> " + pfix);

            } else {
                String result = postfix.postfixEvaluation(pfix, keyTable);
                output.println(infix + " --> " + pfix + " --> " + result);
            }
          }

        scan.close();
    }

}
