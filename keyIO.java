package keyMeUp;
import java.io.*;
import java.util.*;

public class keyIO {
    public static void outputPathToFile(String pathwayFound, String fileName) {
        FileOutputStream fileStrm = null;
        PrintWriter pw;
    
        try {
            fileStrm = new FileOutputStream(fileName);
            pw = new PrintWriter(fileStrm);
            pw.println(pathwayFound);
            pw.close();
        }
        catch (IOException ex) {
            System.out.println("Error in writing to file: " + ex.getMessage());
        } 
    }

    //Submitted as part of Prac 4 for DSA Semester 2, 2022
    public static void serialiseGraph(Graph graphToSave, String filename) {
        FileOutputStream fileStrm;
        ObjectOutputStream objStrm;

        try {
            fileStrm = new FileOutputStream(filename);
            objStrm = new ObjectOutputStream(fileStrm);
            
            objStrm.writeObject(graphToSave);

            objStrm.close();
            fileStrm.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println("File not found.");
        }
        catch(IOException ex) {
            System.out.println("Error initialising stream.");
        }
        catch(Exception ex) {
            throw new IllegalArgumentException("Unable to save object to file.");
        }
    }

    //Submitted as part of Prac 4 for DSA Semester 2, 2022
    public static Graph deserialiseGraph(String filename) throws IllegalArgumentException {
        FileInputStream fileStrm;
        ObjectInputStream objStrm;
        Graph list = null;

        try {
            fileStrm = new FileInputStream(filename);
            objStrm = new ObjectInputStream(fileStrm);
            list = (Graph)objStrm.readObject();
            objStrm.close();
            fileStrm.close();
        }
        catch (ClassNotFoundException ex) {
            System.out.println("Class LList not found" + ex.getMessage());
        }
        catch (Exception ex) {
            throw new IllegalArgumentException("Unable to load object from file.");
        }
        return list;
    }

    public static String retrieveFileName() throws IllegalArgumentException {
        String name;
        Scanner uInput = new Scanner (System.in);
            System.out.println("Enter a file name: ");
            name = uInput.nextLine();
            if(name == null)
                throw new IllegalArgumentException("Name of file cannot be null!");
        return name;
    }

    public static String getStringFromUser() throws IllegalArgumentException {
        String str = null;
        Scanner uInput = new Scanner (System.in);
        System.out.println("Enter a string to enter on a keyboard: ");
        str = uInput.nextLine();
        if(str == null)
            throw new IllegalArgumentException("String cannot be null!");

        return str;
    }

    public static String getStringFromFile(String fileName) throws IllegalArgumentException, IOException {
        String toProcess = null;
        
        Scanner uInput = new Scanner (System.in);
        FileInputStream input = new FileInputStream(fileName);
        InputStreamReader read = new InputStreamReader(input);
        BufferedReader buffer = new BufferedReader(read);

        toProcess = buffer.readLine();

        if(toProcess == null)
            throw new IllegalArgumentException("String cannot be null!");
        return toProcess;
    }

    public static Graph readKeyboard(String keyFile, Graph keyboard) {
        try {
            FileInputStream input = new FileInputStream(keyFile);
            InputStreamReader read = new InputStreamReader(input);
            BufferedReader buffer = new BufferedReader(read);
            String[][] rawKeyboard;
            String[] columns;
            boolean wrap = false;
            String wrapDir = null;
            String line;
            int lineNum = 0;
            int row = 0;

            try (BufferedReader lineBuff = new BufferedReader(new FileReader(keyFile))) {
                while(lineBuff.readLine() != null) {lineNum++;} //count number of lines
            }
            
            line = buffer.readLine();
            if( "w".equals(line) ) {
                wrap = true;
                wrapDir = buffer.readLine();
                lineNum = lineNum - 2;
                line = buffer.readLine();
            }
            columns = parseLine(line);
            rawKeyboard = new String[lineNum][columns.length];

            for(int columnNo = 0; columnNo < columns.length; columnNo++) {
                rawKeyboard[row][columnNo] = columns[columnNo];
            }
            row++;

            while (row < lineNum) {
                line = buffer.readLine();
                columns = parseLine(line);
                for(int columnNo = 0; columnNo < columns.length; columnNo++) {
                    rawKeyboard[row][columnNo] = columns[columnNo];
                }
                row++;
            }
            input.close();
            
            if(!wrap)
                keyboard = createStandardKey(keyboard, rawKeyboard, lineNum, columns.length);
            else
                keyboard = createWrapKey(keyboard, rawKeyboard, lineNum, columns.length, wrapDir);
        }
        catch(IOException ioEx) {
            System.err.println("Error in file processing " + ioEx.getMessage());
        }
        return keyboard;
    }

    public static Graph createWrapKey(Graph newKeyboard, String[][] rawKeyboard, int lineNum, int colLength, String wrapDir) {
        //Adding edges and nodes to graph from 2d array, edges wrap around
        //Loop through the rows in 2D array
        for(int rowNo = 0; rowNo < lineNum; rowNo++) {
            //Loop through columns in 2d array
            for(int colNo = 0; colNo < colLength; colNo++) {

                //if the current key at the right edge of the keyboard AND keyboard wraps around the right
                if( (colNo == colLength - 1) && (wrapDir.contains("r")) )
                    newKeyboard.addEdge(rawKeyboard[rowNo][colNo], rawKeyboard[rowNo][0], "Right"); //Wrap around to the left
                else if ( colNo != colLength - 1)
                    newKeyboard.addEdge(rawKeyboard[rowNo][colNo], rawKeyboard[rowNo][colNo+1], "Right");
                
                //if the colNo is at the left edge of the keyboard AND keyboard wraps around the left
                if( (colNo == 0) && (wrapDir.contains("l")) )
                    newKeyboard.addEdge(rawKeyboard[rowNo][colNo], rawKeyboard[rowNo][colLength-1], "Left"); //wrap around to the right
                else if(colNo != 0) {
                    newKeyboard.addEdge(rawKeyboard[rowNo][colNo], rawKeyboard[rowNo][colNo-1], "Left");
                }

                //if the rowNo+1 is at the bottom of the keyboard AND keyboard wraps around the bottom
                if( (rowNo == lineNum - 1) && (wrapDir.contains("b")))
                    newKeyboard.addEdge(rawKeyboard[rowNo][colNo], rawKeyboard[0][colNo], "Down"); //wrap around to the top
                else if(rowNo != lineNum - 1)
                    newKeyboard.addEdge(rawKeyboard[rowNo][colNo], rawKeyboard[rowNo+1][colNo], "Down");
                
                //if the rowNo-1 is at the top of the keyboard AND keyboard wraps around the top
                if( (rowNo == 0) && (wrapDir.contains("t")) )
                    newKeyboard.addEdge(rawKeyboard[rowNo][colNo], rawKeyboard[lineNum-1][colNo], "Up");
                else if (rowNo != 0)
                    newKeyboard.addEdge(rawKeyboard[rowNo][colNo], rawKeyboard[rowNo-1][colNo], "Up");
            }
        }  
        return newKeyboard;
    }
 
    public static Graph createStandardKey(Graph newKeyboard, String[][] rawKeyboard, int lineNum, int colLength) {
        //Adding edges and nodes to graph from 2d array
        //Loop through the rows in 2D array
        for(int rowNo = 0; rowNo < lineNum; rowNo++) {
            //Loop through columns in 2d array
            for(int colNo = 0; colNo < colLength; colNo++) {

                //if the columnNo+1 IS NOT larger than the number of columns
                if( (colNo+1) < colLength)
                    newKeyboard.addEdge(rawKeyboard[rowNo][colNo], rawKeyboard[rowNo][colNo+1], "Right");
                //if the colNo-1 is not less than 0
                if( (colNo-1) >= 0)
                    newKeyboard.addEdge(rawKeyboard[rowNo][colNo], rawKeyboard[rowNo][colNo-1], "Left");
                //if the rowNo+1 doesnt exceed the edge
                
                //determine up down edges
                if( (rowNo+1) < lineNum)
                    newKeyboard.addEdge(rawKeyboard[rowNo][colNo], rawKeyboard[rowNo+1][colNo], "Down");
                //if the rowNo-1 is not less than 0
                if( (rowNo-1) >= 0)
                    newKeyboard.addEdge(rawKeyboard[rowNo][colNo], rawKeyboard[rowNo-1][colNo], "Up");
            }
        }
        return newKeyboard;
    }

    public static String[] parseLine(String line) {
        String[] keys = null;
        keys = line.split(" ", 0);
        return keys;
    }
}
