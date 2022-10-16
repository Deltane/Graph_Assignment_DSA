# Graph_Assignment_DSA
A program designed to read in a set of keyboard keys and path-find the shortest number of steps to type in a given string.

## How To Run
- 	Compile java files using "javac *.java" on the CLI in the location directory.
- 	Launch with "java keyMeUp"

-     Use "keyMeUp -s 'key_fileName.txt' 'string_file_name.txt' 'output_file_name.txt' " to open the file in silent mode. In this 
      mode the program will read the keyboard from the 'key_file_name' file, build it into a graph before reading in a string from 
      'string_file_name' and find the shortest path to type it in. It then outputs the shortest path to the file.

-     Use "keyMeUp -i" to open the file in interactive mode. In this mode the program will display a menu, the user can then use the
      menu to select options and manipulate the graph. User can use 0 to exit from menus and sub-menus.

-     Running the program with out any additional arguments will display usage information.


## Data Structures

### DSAStack.java 
**[Inherits functions from LList.java]**
- Contains functions for simple stack implementation, used by Graph.java in the findPath() and 
	printPath() to trace route.
-	Requires LList.java as it inherits remove, insert and isEmpty functions to use locally.

### DSAQueue.java 
**[Inherits functions from LList.java]**

- Contains functions for simple queue implementation, used by Graph.java in the findPath() and 
	printPath() to create and pull from searching queue while BFSing.
- Requires LList.java as it inherits remove, insert and isEmpty functions to use locally.

### LList.java 
**[Is Super class for DSAQueue and DSAStack]**

-	No dependencies.
- Is super class for DSAStack and DSAQueue, both classes use LList instances to maintain 
	entries. Both share common functions, LList functions have been used to make implementation 
	more streamlined and prevent repition.

### Graph.java

-	Depends on GraphEdge, GraphNode, LList, DSAStack and DSAQueue
-	Functions for implementation of Graph class, instances of GraphNode are stored in a LList of
	nodes.
-	Uses DSAStack and DSAQueue to perform BFS to find pathways, stack is used to traceroute.

### GraphEdge.java

-	GraphEdge has no dependencies.
- GraphEdge holds source and destination links between node. As well as a value for the edge.
	In this implementation (keyMeUp) this is used to track the relative positions of the 
	destination node from the source (up, down, left, right).

### GraphNode.java

-	GraphNode depends on GraphEdge and LList.
-	GraphNode implements the vertices on the graph, tracking node labels and the edges they are
	the source node of. Node values are not used in this implementation.
-	The GraphNode maintains the list of edges they are the source node of and are the SOLE
	tracker of the graphs edges.


## DRIVER FUNCTIONS // UI

### keyMeUp.java
- keyMeUp contains main and does very little other than accept command line arguments and 
	calling the appropriate functions from either keyOps or keyIO.
### keyOps.java
-	keyMeUp & keyOps calls keyOps the UI and processing functions of the graph, string and
	keyboards. 
-	It displays the menu and handles the looping and selection of the menu and sub-menu.
-	Processes strings entered by calling BFS functions.

### keyIO.java
- keyMeUp & keyOps calls keyOps for input and output functions of the graph, string and
	keyboards.
- All IO functions are located here to keep methods grouped together by function and prevent
	unnecessary confusion.
- KeyIO utilises a 2D array to read in keyboards from the file and put them into a graph.


## TEST HARNESSES

### UnitTestLList.java
- When called tests all functions of LList and checks results and error handling.

### UnitTestGraph.java
- When called tests all functions of Graph and checks results and error handling.

### UnitTestDSAStack.java
- When called tests all functions of DSAStack and checks results and error handling.

### UnitTestDSAQueue.java
-	When called tests all functions of DSAQueue and checks results and error handling.

## MISSING REQUIREMENTS // LIMITATIONS
-	The program provides only one pathway (the shortest). It doesn't provide multiple or rank 
	them.
- The user cannot edit the labels of nodes or edges once they are part of the graph.**
- The user cannot remove edges or nodes from the graph.
- The user cannot update the node value once part of the graph (node values are not used as
	part of the keyMeUp implementation)
- Special characters are implemented on keyboard but ouput may be erroneous due to difficulty
	differentiating between duplicates.
