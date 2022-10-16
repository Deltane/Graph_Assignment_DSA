package keyMeUp;
import java.io.Serializable;

public class Graph implements Serializable {
    LList nodes;

    public Graph() { 
        nodes = new LList();
     }

    /* addNode [Mutator]
     * Desc: Adds vertex to graph and assigns a label and value.
     */
    public void addNode(String label, Object value) {
        if( !hasNode(label) ) {
            GraphNode newNode = new GraphNode(label, value);
            nodes.insertLast(newNode);
        }
    }

    public void addEdge(String label1, String label2, Object edgeValue) {
        String edgeLbl = label1 + " --> " + label2;
        //Check that both nodes exist. Create nodes if they don't
        if( !(label1.equals(label2)) ) {
            if( !(hasNode(label1)) ) { addNode(label1, null); }
            if( !(hasNode(label2)) ) { addNode(label2, null); }
            if( !(hasEdge(edgeLbl)) ) {
            //Create new edge data struct to store the edge value
            GraphEdge newEdge = new GraphEdge(getNode(label1), getNode(label2), edgeValue);

            //Add the new edge to the source nodes adjacency list (bcus edges have relative directions stored)
            getNode(label1).addEdgeToAL(newEdge);
            }
        }
    }

    public boolean hasNode(String label) {
        boolean hasNode = false;
        GraphNode current = null;
        for(Object ob : nodes) {
            current = (GraphNode)ob;
            if( current.getLabel().equals(label) )
                hasNode = true;
        }
        return hasNode;
    }

    public boolean hasEdge(String edgeLbl) {
        boolean hasEdge = false;
        GraphEdge current = null;
        LList list;

        for(Object ob : nodes) {
            list = ((GraphNode)ob).getEdges();
            for(Object edge : list) {
                current = (GraphEdge)edge;
                if( current.getEdgeLbl().equals(edgeLbl) )
                    hasEdge = true;
            }
        }
        return hasEdge;
    }

    public int getEdgeCount() {
        int count = 0;
        LList ls = new LList();

        for(Object node : nodes) { //Get a vertex from the local nodes list
            ls = ((GraphNode)node).getEdges();
            for( Object edge : ls ) {count++;} //Get edges from the current node
        }
        return count;
    }

    public int getNodeCount() {
        int count = 0;
        for(Object node : nodes) {count++;}
        return count;
    }

    public GraphNode getNode(String label) {
        GraphNode target = null;

        try { target = findNode(label); }
        catch(MissingNodeException missingNo) {
            System.err.println(missingNo.getMessage() + missingNo.getError());
        }
        return target;
    }

    public GraphEdge getEdge(String label) {
        GraphEdge target = null;
        try { target = findEdge(label); }
        catch(MissingNodeException missingNo) {
            System.err.println("Cannot find edge in graph instance.");
        }
        return target;
    }
    
    public String getNodeString(String label) {
        GraphNode target = null;
        String targetString = "";

        try { 
            target = findNode(label);
            targetString = targetString + "\nNode Label: " + target.getLabel() + "\nNode Value: " + target.getValue() + "\nNode Edges: ";
            for( Object ob : target.getEdges() ) {
                GraphEdge currentEdge = (GraphEdge)ob;
                targetString = targetString + "\n" + currentEdge.getEdgeLbl();
            }
        }
        catch(MissingNodeException missingNo) {
            System.err.println(missingNo.getMessage() + missingNo.getError());
        }
        return targetString;
    }

    public String getEdgeString(String label) {
        GraphEdge target = null;
        String targetString = "";

        try { 
            target = findEdge(label);
            targetString = targetString + "\nEdge Label: " + target.getEdgeLbl() + "\nEdge Value: " + target.getValue();
        }
        catch(MissingNodeException missingNo) {
            System.err.println(missingNo.getMessage() + missingNo.getError());
        }
        return targetString;
    }

    private GraphEdge findEdge(String label) throws MissingNodeException {
        GraphEdge target = null;
        GraphNode node = null;
        GraphEdge current = null;

        if( hasEdge(label) ) {
            for(Object ob : nodes) {
                node = (GraphNode)ob;
                for(Object edge : node.getEdges()) {
                    current = (GraphEdge)edge;
                    if( (current.getEdgeLbl().equals(label)) ) { target = current; }
                }
            }
        }
        else
            throw new MissingNodeException("Cannot find node with label: " + label);
        return target;
    }

    private GraphNode findNode(String label) throws MissingNodeException {
        GraphNode target = null;
        GraphNode current = null;
        if(hasNode(label)) {
            for(Object ob : nodes) {
                current = (GraphNode)ob;
                if( (current.getLabel().equals(label)) ) { target = current; }
            }
        }
        else
            throw new MissingNodeException("Cannot find node with label: " + label);
        return target;
    }

    public void updateNodeValue(String nodeLbl, Object newVal) {
        for(Object ob : nodes) {
            if( ((GraphNode)ob).getLabel().equals(nodeLbl) )
                ((GraphNode)ob).setValue(newVal);
        }
    }

    public void updateEdgeValue(Object val, String edgeLbl) {
        getEdge(edgeLbl).setValue(val);
    }

    public void displayAsList() {
        LList ls = new LList();

        for( Object current : nodes ) {
            ls = ((GraphNode)current).getEdges(); //Get adjacency list of current vertex.
            
            System.out.println("\nVertex Name: " + ((GraphNode)current).getLabel());
            System.out.println("Vertex Data: " + ((GraphNode)current).getValue());
            System.out.print("Edges: \n");

                for( Object curAdjVert : ls ) { //GO THROUGH ADJACENCY LIST AND PRINT NODE
                    System.out.println( ((GraphEdge)curAdjVert).getEdgeLbl() + " : " + ((GraphEdge)curAdjVert).getValue() );
                } 
            }
        System.out.println("\n");
    }

    public String findPath(String srcLabel, String targetLabel) {
        String pathwayFound = "";
        GraphEdge currentEdge;
        if( !(srcLabel.equals(targetLabel)) ) {
            DSAStack toPrint = findPathway(getNode(srcLabel), targetLabel);

            for(Object ob : toPrint) {
                currentEdge = (GraphEdge)ob;
                pathwayFound = pathwayFound + " " + currentEdge.graphToString() + " ";
            }
        }
        else {
            pathwayFound = " " + srcLabel;
        }
        return pathwayFound;
    }

    //Search for a target node path
    private DSAStack findPathway(GraphNode src, String targetLabel) {
        DSAStack pathway = new DSAStack();
        DSAQueue toSearch = new DSAQueue();
        DSAStack route = new DSAStack();
        GraphNode current;
        boolean found = false;

        src.setVisited();
        toSearch.enqueue(src); //Push first node onto queue so we can begin searching through its adjacencies

        while( !(toSearch.isEmpty()) && !found) {
            current = (GraphNode)(toSearch.dequeue()); //Grab node from queue

            for( Object nextEdge : current.getEdges() ) { //Traverse the nodes list of adjacencies and check each one
                GraphEdge thisEdge = (GraphEdge)nextEdge;
                
                if( (thisEdge.getDestination().getLabel()).equals(targetLabel) ) { 
                    found = true; 
                    pathway.push(thisEdge);
                }
                else if ( !(thisEdge.getDestination().getVisited()) && !(found)) { //If the next adjacent node isnt visited then it can be traversed to.
                    thisEdge.getDestination().setVisited();
                    toSearch.enqueue(thisEdge.getDestination());
                    pathway.push(thisEdge);
                }
            }
        }

        for(Object node : nodes) { 
            current = (GraphNode)node;
            current.clearVisited();
            
            for(Object neighbours : current.getEdges() ) {
                GraphEdge curEdge = (GraphEdge)neighbours;
                curEdge.getDestination().clearVisited();
            }
        }

        if( found ) {
            route.push(pathway.pop()); // put destination on route
            for(Object edg : pathway) {
                GraphEdge source = (GraphEdge)route.top();
                GraphEdge routeStep = (GraphEdge)edg;
                if( source.getSourceLbl().equals(routeStep.getDestination().getLabel()) ) {
                    route.push(routeStep);
                }
            }
        }
        else { pathway = null; }

        return route;
    }

    public class GraphNode implements Serializable {
        String label;
        Object value;
        boolean visited;
        LList adjList;

        GraphNode(String newLabel, Object newValue) {
            label = newLabel;
            value = newValue;
            visited = false;
            adjList = new LList();
        }

        private void addEdgeToAL(GraphEdge adj) {
            adjList.insertLast(adj);
        }

        // ---------------------------------- Getters ----------------------------------
        private String getLabel() { return label; }
        
        private boolean getVisited() { return visited; }

        private LList getEdges() { return adjList; }

        private Object getValue() { return value; }

        // ---------------------------------- Setters ----------------------------------
        
        private void setVisited() { visited = true; }

        private void setValue(Object newVal) { value = newVal; }

        private void setLbl(String newLbl) { label = newLbl; }

        private void clearVisited() { visited = false; }

    }

    public class GraphEdge implements Serializable {
        GraphNode destination;
        String source, edgeLbl;
        Object value;

        GraphEdge(GraphNode src, GraphNode dest, Object val) {
            source = src.getLabel();
            destination = dest;
            value = val;
            edgeLbl = src.getLabel() + " --> " + dest.getLabel();
        }

        GraphEdge(GraphNode src, GraphNode dest) {
            source = src.getLabel();
            destination = dest;
            value = null;
            edgeLbl = src.getLabel() + " --> " + dest.getLabel();
        }

        public void setValue(Object newVal) { value = newVal; }

        public void setSourceLbl(String newLbl) { source = newLbl; }

        public void setEdgeLbl(String newLbl) { 
            edgeLbl = source + " --> " + destination.getLabel();
        }

        public void setDestination(GraphNode newDest) { 
            destination = newDest;
        }

        public Object getValue() { return value; }

        public Object getEdgeLbl() { return edgeLbl; }

        public String getSourceLbl() { return source; }

        public GraphNode getDestination() { return destination; }

        private String graphToString() {
            String str = "";

            str = source + " --> " + value + " --> " + destination.getLabel();

            return str;
        }

    }

    public class DuplicateNodeException extends Exception {
        private String error;

        public DuplicateNodeException(String errorMessage) {
            super(errorMessage);
            this.error = "There is already a node with this name in the graph.";
        }

        public String getError() {
            return this.error;
        }
    }

    public class MissingNodeException extends Exception {
        private String error;

        public MissingNodeException(String errorMessage) {
            super(errorMessage);
            this.error = "This vertex does not exist within this structure.";
        }
        
        public String getError() {
            return this.error;
        }
    }

}