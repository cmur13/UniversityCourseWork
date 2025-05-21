//
//
// Name:    Murillo, Clarissa
// Project: 5
// Due:     5/12/23
// Course:  cs-2400-03-sp23
//
// Description:
//           A class that implements the interface ListWithIteratorInterface
//

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedListWithIterator<T> implements ListWithIteratorInterface<T>{
    private Node firstNode;
    private Node lastNode;
    private int numberOfEntries;

    public LinkedListWithIterator(){
        initializeDataFields();
    } // end default constructor;
    private void initializeDataFields() {
        firstNode = null;
        numberOfEntries = 0;
    } // end initializeDataFields

    // implementations of the ADT list go here
    public void add (T newEntry) {
        Node newNode = new Node(newEntry);
        if (numberOfEntries == 0) {  // replaced the statement if(isEmpty){
            firstNode = newNode;
        } else {
            lastNode.setNextNode(newNode);
        }
        lastNode = newNode;
        numberOfEntries ++;
    }

    public void add(int newPosition, T newEntry) {
        if (newPosition < 1 || newPosition > numberOfEntries + 1) {
            throw new IndexOutOfBoundsException();
        }
        Node newNode = new Node(newEntry);
        // Below is saying that if numberOfEntries is empty, set the firstNode to newNode
        // and lastNode to newNode
        if (numberOfEntries == 0) {
            firstNode = newNode;
            lastNode = newNode;
        } else if (newPosition == 1) {
            newNode.setNextNode(firstNode);
            firstNode = newNode;
        } else if (newPosition == numberOfEntries + 1) {
            lastNode.setNextNode(newNode);
            lastNode = newNode;
        } else {
            Node currentNode = firstNode;
            for (int i = 1; i < newPosition; i++) {
                currentNode = currentNode.getNextNode();
            }
            currentNode.setNextNode(new Node(newEntry, currentNode.getNextNode()));
        }
        numberOfEntries++;
    }//  end
    public T remove(int givenPosition)
    {
        T result = null;
        if ((givenPosition >= 1) && (givenPosition <= numberOfEntries)) {
            if (givenPosition == 1){
                result = firstNode.getData();    // save entry to be removed
                firstNode = firstNode.getNextNode();
            } else {
                Node nodeBefore = getNodeAt(givenPosition - 1);
                Node nodeToRemove = nodeBefore.getNextNode();
                result = nodeToRemove.data;  // save entry to be removed
                Node nodeAfter = nodeToRemove.getNextNode();
                nodeBefore.setNextNode(nodeAfter);
            } // end if
            numberOfEntries--;
            return result;
        } // end if
        else{
            throw new IndexOutOfBoundsException("illegal position given to remove");
        }
    } // end remove

    //All other methods below from ListInterface will throw an UnsupportedOperationException

    public void clear(){
        throw new UnsupportedOperationException("clear() to be implemented");
    }

    public T replace(int givenPosition, T newEntry){
        throw new UnsupportedOperationException("replace() to be implemented");
    } //end replace

    public T getEntry(int givenPosition){
        throw new UnsupportedOperationException("getEntry() to be implemented");
    } //end getEntry

    public T[] toArray(){
        throw new UnsupportedOperationException("toArray() to be implemented");
    } //end toArray

    public boolean contains(T anEntry){
        throw new UnsupportedOperationException("contains() to be implemented");
    } //end contains

    public int getLength(){
        throw new UnsupportedOperationException("getLength() to be implemented");
    } //end getLength

    public boolean isEmpty(){
        throw new UnsupportedOperationException("isEmpty() to be implemented");
        /*
        boolean result;
        if(numberOfEntries ==0){
            result = true;
        }
        else{
            result = false;
        }// end if
        return result;
         */
    } //end isEmpty

    //

    public Iterator<T> iterator(){
        return new IteratorForLinkedList();
    } // end iterator

    public Iterator<T> getIterator(){
        return iterator();
    }
    private class IteratorForLinkedList implements Iterator<T>{
        private Node nextNode;
        private IteratorForLinkedList(){
            nextNode = firstNode;
        } // end default constructor
        //implementation of the method int eh interface Iterator go here.
        public T next()
        {
            T result;
            if (hasNext())
            {
                result = nextNode.getData();
                nextNode = nextNode.getNextNode(); // Advance iterator
            }
            else
                throw new NoSuchElementException("Illegal call to next(); " +
                        "iterator is after end of list.");
            return result; // Return next entry in iteration
        } // end next
        public boolean hasNext()
        {
            return nextNode != null;
        } // end hasNext
        public void remove()
        {
            throw new UnsupportedOperationException("remove() is not supported " +
                    "by this iterator");
        } // end remove
    } // end private class

    private class Node{
        private T data;
        private Node next;

        private Node(T dataPortion){
            this (dataPortion, null);
        } // end constructor
        private Node(T dataPortion, Node nextNode){
            data = dataPortion;
            next = nextNode;
        } // end constructors
        private T getData(){
            return data;
        } //end getData
        private void setData(T newData){
            data = newData;
        } //end setData
        private Node getNextNode(){
            return next;
        } //end getNextNode
        private void setNextNode(Node nextNode){
            next = nextNode;
        } // end setNextNode
    } // end Node
    private Node getNodeAt(int givenPosition)
    {
        Node currentNode = firstNode;

        for (int counter = 1; counter < givenPosition; counter++)
            currentNode = currentNode.getNextNode();
        return currentNode;
    } // end getNodeAt
} // end LinkedListWithIterator
