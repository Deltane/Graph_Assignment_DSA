import keyMeUp.Graph;

//Submitted as part of Prac  for DSA Semester 2, 2022
public class UnitTestGraph {

    public static void main(String Args[]) {

        //VARIABLE AND TEST DATA DECLARATION
        String testLabels[] = {"A", "B", "C", "D", "E" };
        int testValues[] = {1, 2, 3, 4, 5};
        String testEdges[][] = { {"A", "B"}, {"B", "C"}, {"C", "D"}, {"D", "E"}, 
                                 {"A", "C"}, {"C", "E"}, {"B", "E"} };
        Graph testGraph = new Graph();
        Object result;

        //Testing Creation and Allocation of Graph Object
        //ERROR TEST 1 : CREATE EDGE WITHOUT VERTEX
        try{
            System.out.println("Test 0.5: Try create an edge without vertex");
            testGraph.addEdge("A", "B", null);
            System.out.println("Passed. Graph creates a nodes to fill in missing vertexes.\n");
        } catch(Exception ex) { System.out.println("Failed.\n"); }

        //TEST 1.5: ADD DUPLICATE VERTEX
        try{
            System.out.println("Test 1.5: Attempt to add duplicate vertex. Should thow exception");
            testGraph.addNode(testLabels[1], testValues[1]);
            System.out.println("Passed. Exception thrown and handled as expected.\n");
        } catch(Exception ex) { System.out.println("FAILED.\n"); }

        //TEST 2: ADD MULTIPLE VERTICES TO GRAPH
        try{
            System.out.println("Test 2: Add multiple vertices to graph.");
            for(int ii = 2; ii < testValues.length; ii++)
                testGraph.addNode(testLabels[ii], testValues[ii]);
            System.out.println("Passed.\n");
        } catch(Exception ex) { System.out.println("FAILED\n"); }
        
        //TEST 3: ADD EDGES
        try{
            System.out.println("Test 3: Add multiple edges to graph.");

            for(int i = 0; i < testEdges.length; i++) {
                testGraph.addEdge(testEdges[i][0], testEdges[i][1], null);
                testGraph.addEdge(testEdges[i][1], testEdges[i][0], null);
            }

            System.out.println("passed.\n");
        } catch(Exception ex) { System.out.println("FAILED\n"); }
        
        
        //Verifying graph data.
        //----------------------------------------------------
        //TEST 4: PRINT GRAPH AS LIST
        try{
            System.out.println("Test 4: Print graph.");
            testGraph.displayAsList();
            System.out.println("passed.\n");
        } catch(Exception ex) { System.out.println("FAILED\n"); }

        //TEST 5: Check VertexCount()
        try{
            System.out.println("Test 5: Check VertexCount() ");
            result = testGraph.getNodeCount();
            if( (int)result == testLabels.length ) {
                System.out.println("passed.");
                System.out.println("Result is: " + result + "\n");
            }
            else
                System.out.print("FAILED\n");

        } catch(Exception ex) { System.out.println("FAILED"); }

        //TEST 6: Check EdgeCount()
        try{
            System.out.println("Test 6: Check EdgeCount()");
            result = testGraph.getEdgeCount();
            if( (int)result == (testEdges.length)*2 ) {
                System.out.println("passed.");
                System.out.println("Result is: " + result + "\n");
            }
            else
                throw new Exception("Wrong number of edges in graph");
        } catch(Exception ex) { System.out.println("FAILED." + ex.getMessage()); }

        //TEST 7: Check hasVertex()
        try{
            System.out.println("Test 7: Check if vertex D exists (expected result: true)");
            if( testGraph.hasNode("D") ) 
                System.out.println("passed.");
            else
                throw new Exception();
        } catch(Exception ex) { System.out.println("FAILED"); }
        
        //TEST 8: Check hasVertex()
        try{
            System.out.println("Test 8: Check if vertex F exists (expected result: false)");
            if( testGraph.hasNode("F") ) 
                System.out.println("Failed.");
            else
                throw new Exception();
        } catch(Exception ex) { System.out.println("passed"); }

        //TEST 9: PRINT GRAPH AS BFS
        try{
            System.out.println("Test 9: Print graph using Breadth Traversal.");
            testGraph.findPath("A", "E");
            System.out.println("passed.");
        } catch(Exception ex) { System.out.println("FAILED" + ex.getMessage()); }
    }

    
}