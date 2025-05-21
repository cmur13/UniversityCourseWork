//
//  Name:     Murillo, Clarissa
//  Project:  5
//  Due:      5/12/23
//  Course:   cs-2400-03-sp23
//
//  Description:
//            A class of vertices for a graph
//

import java.util.Iterator;
import java.util.NoSuchElementException;

/** A class of vertices for a graph */
public class Vertex<T> implements VertexInterface<T> {
    private T label;
    private ListWithIteratorInterface<Edge> edgeList; // Edges to neighbors, you can use implementation from homework1
    private boolean visited; // true if visited
    private VertexInterface<T> previousVertex; // On path to this vertex
    private double cost; // of path to this vertex

    public Vertex(T vertexLabel){
        label = vertexLabel;
        edgeList = new LinkedListWithIterator<>();
        visited = false;
        previousVertex = null;
        cost = 0;
    } // end constructor

    // implementations of the vertex operations go here

    public boolean connect(VertexInterface<T> endVertex, double edgeWeight){
        boolean result = false;
        if(!this.equals(endVertex)){ //Vertices are distinct
            Iterator<VertexInterface<T>> neighbors = getNeighborIterator();
            boolean duplicateEdge = false;
            while(!duplicateEdge && neighbors.hasNext()){
                VertexInterface<T> nextNeighbor = neighbors.next();
                if(endVertex.equals(nextNeighbor))
                    duplicateEdge = true;
            } // end while
            if(!duplicateEdge){
                edgeList.add(new Edge(endVertex, edgeWeight));
                result = true;
            } // end if
        } // end if
        return result;
    } // end connect
    public boolean connect(VertexInterface<T> endVertex){
        return connect(endVertex, 0); // set to zero so that there is an edge
    } // end connect

    public boolean disconnect(VertexInterface<T> endVertex) {
        boolean result = false;
        int index = 0;
        if (!this.equals(endVertex)) {
            Iterator<VertexInterface<T>> neighbors = getNeighborIterator();
            boolean foundEdge = false;

            while (!foundEdge && neighbors.hasNext()) {
                VertexInterface<T> nextNeighbor = neighbors.next();

                if(endVertex.equals(nextNeighbor))
                    foundEdge = true;
                index ++;
            }
            if(foundEdge){
                edgeList.remove(index);
                result = true;
            }
        } // end if
        return result;
    }

    public Iterator<VertexInterface<T>> getNeighborIterator(){
        return new NeighborIterator();
    } // end getNeighborIterator

    public Iterator<Double> getWeightIterator(){
        return new WeightIterator();
    }
    public boolean hasNeighbor(){
        return !edgeList.isEmpty();
    } // end hasNeighbor

    public VertexInterface<T> getUnvisitedNeighbor(){
        VertexInterface<T> result = null;

        Iterator<VertexInterface<T>> neighbors = getNeighborIterator();
        while(neighbors.hasNext() && result == null){
            VertexInterface<T> nextNeighbor = neighbors.next();
            if(!nextNeighbor.isVisited())
                result = nextNeighbor;
        } // end while
        return result;
    } // end getUnvisitedNeighbor

    public void setPredecessor(VertexInterface<T> predecessor){
        previousVertex = predecessor;
    }
    public VertexInterface<T> getPredecessor(){
        return previousVertex;
    }

    public boolean hasPredecessor(){
        return previousVertex != null;
    }
    public void setCost(double newCost){
        cost = newCost;
    }

    public double getCost(){
        return cost;
    }

    public T getLabel(){
        return label;
    } // end getLabel
    public void visit(){
        visited = true;
    } // end visit
    public void unvisit(){
        visited = false;
    } // end unvisit

    public boolean isVisited(){
        return visited;
    } // end visited

    public boolean equals(Object other) {
        boolean result;

        if ((other == null) || (getClass() != other.getClass()))
            result = false;
        else
        {
            // The cast is safe within this else clause
            @SuppressWarnings("unchecked")
            Vertex<T> otherVertex = (Vertex<T>)other;
            result = label.equals(otherVertex.label);
        }

        return result;
    }

    protected class Edge{
        private VertexInterface<T> vertex; // Vertex at end of edge
        private double weight;

        protected Edge(VertexInterface<T> endVertex, double edgeWeight){
            vertex = endVertex;
            weight = edgeWeight;
        } // end constructor
        protected Edge(VertexInterface<T> endVertex){
            vertex = endVertex;
            weight =0;
        } // end constructor
        protected VertexInterface<T> getEndVertex(){
            return vertex;
        } // end getEndVertex
        protected double getWeight(){
            return weight;
        } // end getWeight
    } // end Edge

    private class NeighborIterator implements Iterator<VertexInterface<T>>{
        private Iterator<Edge> edges;
        private NeighborIterator(){
            edges = edgeList.getIterator();
        } // end default constructor;

        public boolean hasNext(){
            return edges.hasNext();
        } // end hasNext
        public VertexInterface<T> next(){
            VertexInterface<T> nextNeighbor = null;
            if(edges.hasNext()){
                Edge edgeToNextNeighbor = edges.next();
                nextNeighbor = edgeToNextNeighbor.getEndVertex();
            }
            else
                throw new NoSuchElementException();
            return nextNeighbor;
        } // end next
        public void remove(){
            throw new UnsupportedOperationException();
        } // end remove
    } // end NeighborIterator
    private class WeightIterator implements Iterator<Double> {
        private Iterator<Edge> edges;
        private WeightIterator() {
            edges = edgeList.getIterator();
        } // end defaultIterator
        public boolean hasNext() {
            return edges.hasNext();
        }
        public Double next() {
            Double edgeWeight = null;
            if (edges.hasNext()) {
                Edge edgeToNextNeighbor = edges.next();
                edgeWeight = edgeToNextNeighbor.getWeight();
            }
            else
                throw new NoSuchElementException();
            return edgeWeight;
        }
    }
}