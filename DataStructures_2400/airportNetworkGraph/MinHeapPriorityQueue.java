//
// Name:        Murillo, Clarissa
// Project:     5
// Due:         5/12/23
// Course:      cs-2400-03-sp23
//
// Description:
//              A class that implements the priority queue.
//
public final class MinHeapPriorityQueue <T extends Comparable<? super T>> implements PriorityQueueInterface<T>{
    private MinHeapInterface<T> pq;

    public MinHeapPriorityQueue(){
        pq = new MinHeap<>();
    } // end default constructor

    public void add(T newEntry){
        pq.add(newEntry);
    } // end add

    public T remove(){
        return pq.removeMin();
    } // end remove

    public T peek(){
        return pq.getMin();
    } // end peek

    public boolean isEmpty(){
        return pq.isEmpty();
    } // end isEmpty

    public int getSize(){
        return pq.getSize();
    } // end getSize

    public void clear(){
        pq.clear(); // is this right???
    }

}
