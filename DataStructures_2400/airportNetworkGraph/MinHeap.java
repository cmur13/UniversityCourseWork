//
// Name:        Murillo, Clarissa
// Project:     5
// Due:         5/12/23
// Course:      cs-2400-03-sp23
//
// Description:
//              The class MinHeap that implements the MinHeap using an array
//

import java.util.Arrays;

public final class MinHeap<T extends Comparable<? super T>> implements MinHeapInterface<T>{
    private T[] heap; // array of heap entries
    private int lastIndex; // index of last entry
    private boolean integrityOK= false;
    private static final int DEFAULT_CAPACITY = 25; //do I change to 5??
    private static final int MAX_CAPACITY = 10000;

    public MinHeap(){
        this(DEFAULT_CAPACITY); // call next constructor
    } // end default constructor

    public MinHeap(int initialCapacity){
        // is initialCapacity too small?
        if(initialCapacity< DEFAULT_CAPACITY)
            initialCapacity = DEFAULT_CAPACITY;
        else // is initial capacity too big?
            checkCapacity(initialCapacity);
        // the cast is safe because the new array contains all null entries
        @SuppressWarnings("unchecked")
        T[] tempHeap = (T[]) new Comparable[initialCapacity + 1];
        heap = tempHeap;
        lastIndex =0;
        integrityOK = true;
    } // end constructor

    public void add(T newEntry){
        checkIntegrity(); // Ensure initialization of data fields
        int newIndex = lastIndex + 1;
        int parentIndex = newIndex / 2;
        while ( (parentIndex > 0) && newEntry.compareTo(heap[parentIndex]) < 0) // changed this line so its a minHeap
        {
            heap[newIndex] = heap[parentIndex];
            newIndex = parentIndex;
            parentIndex = newIndex / 2;
        } // end while
        heap[newIndex] = newEntry;
        lastIndex++;
        ensureCapacity();
    } // end add

    public T removeMin(){
        checkIntegrity(); // Ensure initialization of data fields
        T root = null;
        if (!isEmpty())
        {
            root = heap[1]; // Return value
            heap[1] = heap[lastIndex]; // Form a semiheap
            lastIndex--; // Decrease size
            reheap(1); // Transform to a heap
        } // end if
        return root;
    } // end removeMax

    public T getMin(){
        checkIntegrity();
        T root = null;
        if(isEmpty())
            root = heap[1];
        return root;
    } // end getMax

    public boolean isEmpty(){
        return lastIndex < 1;
    } // end isEmpty

    public int getSize(){
        return lastIndex;
    } // end getSize

    public void clear(){
        checkIntegrity();
        while(lastIndex > -1){
            heap[lastIndex] = null;
            lastIndex --;
        } // end while
        lastIndex =0;
    } // end clear

    // private methods are below
    private void reheap(int rootIndex)
    {
        boolean done = false;
        T orphan = heap[rootIndex];
        int leftChildIndex = 2 * rootIndex;
        while (!done && (leftChildIndex <= lastIndex) )
        {
            int smallerChildIndex = leftChildIndex; // Assume smaller
            int rightChildIndex = leftChildIndex + 1;
            if ((rightChildIndex <= lastIndex) && heap[rightChildIndex].compareTo(heap[smallerChildIndex]) < 0)
            {
                smallerChildIndex = rightChildIndex;
            } // end if
            if (orphan.compareTo(heap[smallerChildIndex]) > 0)
            {
                heap[rootIndex] = heap[smallerChildIndex];
                rootIndex = smallerChildIndex;
                leftChildIndex = 2 * rootIndex;
            }
            else
                done = true;
        } // end while
        heap[rootIndex] = orphan;
    } // end reheap

    private void ensureCapacity() {
        if (lastIndex >= heap.length - 1) {
            int newLength = 2 * heap.length;
            checkCapacity(newLength);
            heap = Arrays.copyOf(heap, newLength);
        }
    }
    private void checkCapacity(int capacity) {
        if (capacity > MAX_CAPACITY) {
            throw new IllegalStateException("Attempt to create a heap whose capacity exceeds allowed maximum of " + MAX_CAPACITY);
        }
    } // end checkCapacity
    private void checkIntegrity() {
        if(!integrityOK) {
            throw new SecurityException("MinHeap object is corrupt.");
        }
    } // end checkIntegrity
} // end MinHeap