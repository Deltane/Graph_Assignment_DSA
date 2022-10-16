import java.io.*;
import keyMeUp.Graph;
import keyMeUp.keyIO;
import keyMeUp.keyOps;

public class keyMeUp {

    public static void main(String Args[]) {

        if(Args.length == 0) {
            System.out.println("\n\nUsage Information... \n\nTo use this program run it using one of the following methods:\n"); 
            System.out.println("Method 1:   'Java keyMeUp -i' launches the program in interactive mode. The program will print a menu with options for the user to select. "); 
            System.out.println("            The user can enter a number followed by enter to select various menu options and manipulate the graph and find paths on the keyboard.\n"); 
            System.out.println("Method 2:   'Java keyMeUp -s *keyboardFileName* *stringFileName* *pathFileName*' launches the program in silent mode, the program will retrieve the"); 
            System.out.println("            keyboard, process it using the data in the string file and output the pathway results to the file specified under pathFileName.\n"); ;      
        }
        else if( Args[0].equals("-s") && Args.length != 4) {
            System.out.println("Invalid files specified when attempting to run keyMeUp in silent mode. Ensure there is keyFile, strFile and pathFile names specified in that order");
        }
        else if( Args[0].equals("-s") ) {
            silentMode(Args[1], Args[2], Args[3]);
        }
        else if( Args[0].equals("-i") ) {
            Graph interactGraph = new Graph();
            System.out.println("\n\nStarting in interactive testing environment...\n");
            interactGraph = keyOps.options(interactGraph);
        } 
        return;
    }

    public static void silentMode(String keyFile, String strFile, String pathFile) {
        Graph keyboard = new Graph();
        String pathwayFound = null;
        String toProcess = null;

        keyboard = keyIO.readKeyboard(keyFile, keyboard);
        try{
            toProcess = keyIO.getStringFromFile(strFile);
        }
        catch(IOException IOEx) {
            System.out.println(IOEx.getMessage());
        }
        catch(IllegalArgumentException IAEx) {
            System.out.println(IAEx.getMessage());
        }

        try {
            pathwayFound = keyOps.processString(toProcess, keyboard);
            keyIO.outputPathToFile(pathwayFound, pathFile);
        }
        catch(NullPointerException npEx) {
            System.out.println( npEx.getMessage() );
        }
      
    }
}
