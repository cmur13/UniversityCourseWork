//
// Name:    Murillo, Clarissa
// Project: 5
// Due:     5/12/23
// Course:  cs-2400-03-sp23
//
// Description:
//           A class that implements the ADT directed graph. This class stores the airport and distance information
//

import java.util.Iterator;

/** A class that implements the ADT directed graph */


public class DirectedGraph<T> implements GraphInterface<T>{
    private DictionaryInterface<T, VertexInterface<T>> vertices;
    private int edgeCount;

    public DirectedGraph(){
        vertices = new HashedDictionary<>(); //changed to HashedDictionary
        edgeCount = 0;
    } // end default constructor

    public boolean addVertex(T vertexLabel){
        VertexInterface<T> addOutcome = vertices.add(vertexLabel, new Vertex<>(vertexLabel));
        return addOutcome == null; // Was addition to dictionary successful?
    } // end addVertex
    public boolean addEdge(T begin, T end, double edgeWeight) // begin and end are basically labels such as LAX
    {
        boolean result = false;
        VertexInterface<T> beginVertex = vertices.getValue(begin);
        VertexInterface<T> endVertex = vertices.getValue(end);
        if ((beginVertex != null) && (endVertex != null) )
            result = beginVertex.connect(endVertex, edgeWeight);
        if (result)
            edgeCount++;
        return result;
    } // end addEdge
    public boolean addEdge(T begin, T end)
    {
        return addEdge(begin, end, 0);
    } // end addEdge

    public boolean removeEdge(T begin, T end) {
        boolean result = false;
        VertexInterface<T> beginVertex = vertices.getValue(begin);
        VertexInterface<T> endVertex = vertices.getValue(end);
        if((beginVertex != null) && (endVertex!=null))
            result = beginVertex.disconnect(endVertex);
        if(result)
            edgeCount--;
        return result;
    } // end removeEdge

    public boolean hasEdge(T begin, T end) // given two IATA codes
    {
        boolean found = false;
        VertexInterface<T> beginVertex = vertices.getValue(begin);
        VertexInterface<T> endVertex = vertices.getValue(end);
        if ((beginVertex != null) && (endVertex != null))
        {
            Iterator<VertexInterface<T>> neighbors = beginVertex.getNeighborIterator();
            while (!found && neighbors.hasNext())
            {
                VertexInterface<T> nextNeighbor = neighbors.next();
                if (endVertex.equals(nextNeighbor))
                    found = true;
            } // end while
        } // end if
        return found;
    } // end hasEdge
    public boolean isEmpty() {
        return vertices.isEmpty();
    } // end isEmpty

    public void clear() {
        vertices.clear();
        edgeCount = 0;
    } // end clear

    public int getNumberOfVertices() {
        return vertices.getSize();
    } // end getNumberOfVertices

    public int getNumberOfEdges() {
        return edgeCount;
    } // end getNumberOfEdges

    // purpose is to call before you traverse, or before you call a path
    protected void resetVertices() {
        Iterator<VertexInterface<T>> vertexIterator = vertices.getValueIterator();
        while (vertexIterator.hasNext())
        {
            VertexInterface<T> vertex = vertexIterator.next();
            vertex.unvisit();
            vertex.setCost(0);
            vertex.setPredecessor(null);
        } // end while
    } // end resetVertices

    // We don't need the method getBreadthFirstTraversal
    public QueueInterface<T> getBreadthFirstTraversal(T origin){
        throw new UnsupportedOperationException("getBreadthFirstTraversal to be implemented");
    } // end getBreadthFirstTraversal

    public QueueInterface<T> getDepthFirstTraversal(T origin){
        throw new UnsupportedOperationException("getDepthFirstTraversal to be implemented");
    }
    public StackInterface<T> getTopologicalOrder(){
        throw new UnsupportedOperationException("getTopologicalOrder to be implemented");
    }

    /*
   // this class will be in directed graph
   private class EntryPQ implements Comparable<EntryPQ>{
       private VertexInterface<T> vertex;
       private VertexInterface<T> previousVertex;
       private double cost; // cost to nextVertex

    boolean done = false;
    resetVertices();
    PriorityQueueInterface<EntryPQ> pq = new HeapPriorityQueue<>(); // use minheap
    VertexInterface<T> originVertex = vertices.getValue(begin);
    VertexInterface<T> endVertex = vertices.getValue(end);

    pq.add(new EntryPq(originVertex, 0, null);

    */
    public double getCheapestPath(T begin, T end, StackInterface<T> path){
        boolean done = false;
        resetVertices();
        PriorityQueueInterface<EntryPQ> pq = new MinHeapPriorityQueue<>(); // use minheap
        VertexInterface<T> originVertex = vertices.getValue(begin);
        VertexInterface<T> endVertex = vertices.getValue(end);

        pq.add(new EntryPQ(originVertex, 0, null));
        while(!done && !pq.isEmpty()){
            EntryPQ frontEntry = pq.remove();
            VertexInterface<T> frontVertex = frontEntry.getVertex(); //IS THIS RIGHT???

            if(!frontVertex.isVisited()){
                frontVertex.visit();
                frontVertex.setCost(frontEntry.getCost());
                frontVertex.setPredecessor(frontEntry.getPredecessor()); //FIX THIS!!!!
                if(frontVertex.equals(endVertex)){
                    done = true;
                }
                else{
                    Iterator<VertexInterface<T>> neighbors = frontVertex.getNeighborIterator();
                    Iterator<Double> weights = frontVertex.getWeightIterator();
                    while (neighbors.hasNext()) {
                        VertexInterface<T> nextNeighbor = neighbors.next();
                        Double nextWeight = weights.next();
                        if (!nextNeighbor.isVisited())
                        {
                            double nextCost = nextWeight + frontVertex.getCost();
                            pq.add(new EntryPQ(nextNeighbor, nextCost, frontVertex));
                        } // end if
                    } // end while
                } // end of else
            } // end if
        } // end while
        double pathCost = endVertex.getCost();
        path.push(endVertex.getLabel());
        VertexInterface<T> vertex = endVertex;
        while(vertex.hasPredecessor()){
            vertex = vertex.getPredecessor();
            path.push(vertex.getLabel());
        } // end while
        return pathCost;
    } // end getCheapestPath
    private class EntryPQ implements Comparable<EntryPQ>
    {
        private VertexInterface<T> vertex;
        private double cost;
        private VertexInterface<T> previousVertex;

        // do I need this?
        public EntryPQ(VertexInterface<T> aVertex)
        {
            this(aVertex, 0, null);
        }

        public EntryPQ(VertexInterface<T> aVertex, double aCost, VertexInterface<T> predecessor)
        {
            vertex = aVertex;
            cost = aCost;
            previousVertex = predecessor;
        }
        public VertexInterface<T> getVertex() {

            return vertex;
        }
        public double getCost() {

            return cost;
        }

        public int compareTo(EntryPQ other)
        {
            double result = cost - other.cost;
            if (result < 0)
                return -1;
            else if (result == 0)
                return 0;
            else
                return 1;
        }
        public VertexInterface<T> getPredecessor() {
            return previousVertex;
        }
    }


    // you need this method for airportApp.java
    public int getShortestPath(T begin, T end, StackInterface<T> path) {
        //call the method resetVertices
        resetVertices();
        boolean done = false;
        QueueInterface<VertexInterface<T>> vertexQueue = new LinkedQueue<>();
        VertexInterface<T> originVertex = vertices.getValue(begin);
        VertexInterface<T> endVertex = vertices.getValue(end);
        originVertex.visit();
        // Assertion: resetVertices() has executed setCost(0)
        // and setPredecessor(null) for originVertex
        vertexQueue.enqueue(originVertex);
        while (!done && !vertexQueue.isEmpty())
        {
            VertexInterface<T> frontVertex = vertexQueue.dequeue();
            Iterator<VertexInterface<T>> neighbors = frontVertex.getNeighborIterator();
            while (!done && neighbors.hasNext())
            {
                VertexInterface<T> nextNeighbor = neighbors.next();
                if (!nextNeighbor.isVisited())
                {
                    nextNeighbor.visit();
                    nextNeighbor.setCost(1 + frontVertex.getCost());
                    nextNeighbor.setPredecessor(frontVertex);
                    vertexQueue.enqueue(nextNeighbor);
                } // end if
                if (nextNeighbor.equals(endVertex))
                    done = true;
            } // end while
        } // end while
        // Traversal ends; construct shortest path
        int pathLength = (int)endVertex.getCost();
        path.push(endVertex.getLabel());

        VertexInterface<T> vertex = endVertex;
        while (vertex.hasPredecessor())
        {
            vertex = vertex.getPredecessor();
            path.push(vertex.getLabel());
        } // end while
        return pathLength;
    } // end getShortestPath
} // end DirectedGraph