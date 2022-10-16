package keyMeUp;
import java.util.*;

public class keyOps {

    public static String processString(String inStr, Graph keyboard) {
        String pathwayFound = null;
        String source = "a";
        String dest;
        boolean capsLock = false;
        char nextInString;

        try {
            if(inStr == null)
                throw new NullPointerException();

            pathwayFound = "\nPath for String: " + inStr + "\n";
            
            for(int ii = 0; ii < inStr.length(); ii++) {
                nextInString = inStr.charAt(ii);
                
                if( keyboard.hasNode(Character.toString(nextInString)) || Character.toString(nextInString).equals(" ") ) {
                    if( (Character.isUpperCase(nextInString) && !capsLock) ) {
                        dest = "CAPS";
                        pathwayFound = pathwayFound + keyboard.findPath(source, dest) + " [SELECT] " + "\n";
                        capsLock = true;
                        source = dest;
                    }
                    if( (Character.isLowerCase(nextInString) && capsLock) ) {
                        dest = "CAPS";
                        pathwayFound = pathwayFound + keyboard.findPath(source, dest) + " [SELECT] " + "\n";
                        capsLock = false;
                        source = dest;
                    }
                    dest = Character.toString(nextInString);
                    if ( dest.equals(" ") ) {
                        dest = "SPACE";
                    }
                    pathwayFound = pathwayFound + keyboard.findPath(source, dest) + " [SELECT] " + "\n";
                    source = dest;
                }
                else{ pathwayFound = pathwayFound + "Couldn't find char: " + nextInString + " on the keyboard.\n"; }
            }
        } 
        catch(NullPointerException nEx) {
            System.out.println("Cannot process string entered. Value of string input is null.");
        }
        return pathwayFound;
    }

    public static int menu(int option) {
        Scanner uInput = new Scanner (System.in);
        try {
            System.out.println("Select an option from the menu: \n1. Load keyboard from file.\n2. Use node operations menu (find, insert, delete, update nodes)");
            System.out.println("3. Use edge operations menu (find, insert, delete, update edges)\n4. Display graph.\n5. Display graph information\n");
            System.out.println("6. Enter string for pathfinding. \n7. Generate pathways. \n8. Display pathway. \n9. Save keyboard. \n10. Load edited keyboard.\n0. Exit.");
            option = uInput.nextInt();
            uInput.nextLine();
        }
        catch(InputMismatchException ex) {
            System.out.println("Please enter a valid integer. User input recieved: " + ex.getMessage() + "\n");
            uInput.nextLine();
        }
        return option;
    }

    public static int nodeMenu(int option) {
        Scanner uInput = new Scanner (System.in);
        try {
            System.out.println("Select an option from the menu: \n1. Find node.\n2. Insert node.\n3. Update node.\n4. Delete node.\n0. Exit");
            option = uInput.nextInt();
            uInput.nextLine();
        }
        catch(InputMismatchException ex) {
            System.out.println("Please enter a valid integer. User input recieved: " + ex.getMessage() + "\n");
            uInput.nextLine();
        }

        return option;
    }

    public static int edgeMenu(int option) {
        Scanner uInput = new Scanner (System.in);
        try {
            System.out.println("Select an option from the menu: \n1. Find edge.\n2. Insert edge.\n3. Update edge.\n4. Delete edge.\n0. Exit");
            option = uInput.nextInt();
            uInput.nextLine();
        }
        catch(InputMismatchException ex) {
            System.out.println("Please enter a valid integer. User input recieved: " + ex.getMessage() + "\n");
            uInput.nextLine();
        }

        return option;
    }

    public static Graph options(Graph intGraph) {
        int option = 12;
        String fileName = null;
        String stringToSearch = null;
        String pathwayFound = null;
        do {
            option = menu(option);
            switch (option) {
                case 1: //Load keyboard file
                    try{
                        fileName = keyIO.retrieveFileName();
                        if(fileName == null)
                            throw new IllegalArgumentException();

                        intGraph = keyIO.readKeyboard(fileName, intGraph);
                    }
                    catch(IllegalArgumentException IOEx) {
                        System.out.println("Cannot load keyboard file, invalid filename given.");
                    }
                break;
                case 2: //node operations menu
                    intGraph = nodeOps(intGraph);
                break;
                case 3: //Edge operations menu
                    intGraph = edgeOps(intGraph);
                break;
                case 4: //Display Graph
                    intGraph.displayAsList();
                break;
                case 5: //Display graph information
                    System.out.println("Number of edges: " + intGraph.getEdgeCount());
                    System.out.println("Number of nodes: " + intGraph.getNodeCount());
                break;
                case 6: //Enter a string for pathfinding
                    stringToSearch = keyIO.getStringFromUser();
                break;
                case 7: //Generate pathway
                    System.out.println("Generating pathway...");
                    pathwayFound = processString(stringToSearch, intGraph);
                    System.out.println("Would you like to print them to the screen now?\n1. Yes.\n");
                    Scanner uInput = new Scanner(System.in);
                    option = uInput.nextInt();
                    uInput.nextLine();
                    if(option == 1) {
                        System.out.println(pathwayFound);
                    }
                    break;
                case 8: //Display pathway
                    System.out.println(pathwayFound);
                    System.out.println("Would you like to save path to a file?\n1. Yes\n2. No");
                    fileOptions(intGraph, pathwayFound);
                break;
                case 9: //Save key board.
                    try {
                        fileName = keyIO.retrieveFileName();
                        keyIO.serialiseGraph(intGraph, fileName);
                    }
                    catch(IllegalArgumentException IAEx) {
                        System.out.println("Cannot save keyboard to specified file. " + IAEx.getMessage());
                    }
                break;
                case 10: //Load customised keyboard.
                    try {
                        fileName = keyIO.retrieveFileName();
                        if(fileName == null)
                            throw new IllegalArgumentException();
                        
                        intGraph = keyIO.deserialiseGraph(fileName);
                    }
                    catch(IllegalArgumentException IOEx) {
                        System.out.println("Cannot load keyboard file, invalid filename given.");
                    }
                break;       
            }
        } while (option != 0);
        return intGraph;
    }

    public static Graph nodeOps(Graph updatedGraph) {
        int option = 11;
        Scanner uInput = new Scanner(System.in);
        String nodeLbl;
        String newLbl = null;
        Object nodeVal;
        
        do {
            option = nodeMenu(option);
            switch (option) {
                case 1: //Find
                    System.out.println("Enter the label of the node to find.\n");
                    nodeLbl = uInput.nextLine();
                    System.out.println(updatedGraph.getNodeString(nodeLbl));
                break;
                case 2: //Insert
                    System.out.println("Enter the label of the node to insert.\n");
                    nodeLbl = uInput.nextLine();
                    System.out.println("Enter the value of the node to insert.\n");
                    nodeVal = uInput.nextLine();
                    updatedGraph.addNode(nodeLbl, nodeVal);
                break;
                case 3: //Update              
                    System.out.println("Enter the label of the node to update.\n");
                    nodeLbl = uInput.nextLine();

                    if(updatedGraph.hasNode(nodeLbl)) {
                        System.out.println("Enter new value for node.\n");
                        nodeVal = uInput.nextLine();
                        updatedGraph.updateNodeValue(nodeLbl, newLbl);
                    }
                    else {
                        System.out.println("Cannot find node in graph.");
                    }
                case 4: //Delete
                    //TODO deleteNode();
                break;
            }
        } while( option != 0);
        return updatedGraph;
    }
   
    public static Graph edgeOps(Graph updatedGraph) {
        Scanner uInput = new Scanner(System.in);
        int option = 11;
        String source, dest, edgeLbl;
        Object edgeVal;
        do {
            option = edgeMenu(option);
            switch (option) {
                case 1: //Find
                    System.out.println("Enter the label of the source node.\n");
                    source = uInput.nextLine();
                    System.out.println("Enter the label of the destination node.\n");
                    dest = uInput.nextLine();
                    edgeLbl = source + " --> " + dest;
                    System.out.println(updatedGraph.getEdgeString(edgeLbl));
                break;
                case 2: //Insert
                    System.out.println("Enter the label of the source node.\n");
                    source = uInput.nextLine();
                    System.out.println("Enter the label of the destination node.\n");
                    dest = uInput.nextLine();
                    System.out.println("Enter the value of the label.\n");
                    edgeVal = uInput.nextLine();
                    updatedGraph.addEdge(source, dest, edgeVal);
                break;
                case 3: //Update
                    System.out.println("Enter the label of the source node.\n");
                    source = uInput.nextLine();
                    System.out.println("Enter the label of the destination node.\n");
                    dest = uInput.nextLine();
                    System.out.println("Enter new edge value.\n");
                    edgeVal = uInput.nextLine();
                    updatedGraph.updateEdgeValue(edgeVal, source + " --> " + dest);
                case 4: //Delete
                    //TODO deleteNode();
                break;
            }
        } while( option != 0);
        return updatedGraph;
    }

    public static void fileOptions(Graph intGraph, String path) {
        int option = 3;
        Scanner uInput = new Scanner(System.in);
        option = uInput.nextInt();
        uInput.nextLine();
        if(option == 1) {
            String outputFile = keyIO.retrieveFileName();
            keyIO.outputPathToFile(path, outputFile);
        }
    }
}
