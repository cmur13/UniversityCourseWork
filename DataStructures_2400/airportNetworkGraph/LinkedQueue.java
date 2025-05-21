//
// Name:    Murillo, Clarissa
// Project: 5
// Due:     5/12/23
// Course:  cs-2400-03-sp23
//
// Description:
//           A class that implements the QueueInterface
//

import java.util.EmptyStackException;

public final class LinkedQueue<T> implements QueueInterface<T>{
    private Node firstNode;
    private Node lastNode;

    public LinkedQueue(){
        firstNode = null;
        lastNode = null;
    } // end default constructor

    // implementations of the queue operations go here
    public void enqueue(T newEntry){
        Node newNode = new Node(newEntry, null);
        if(isEmpty())
            firstNode = newNode;
        else
            lastNode.setNextNode(newNode);
        lastNode = newNode;
    } // end enqueue

    public T dequeue()
    {
        T front = getFront(); // Might throw EmptyQueueException
        // Assertion: firstNode != null
        firstNode.setData(null);
        firstNode = firstNode.getNextNode();
        if (firstNode == null)
            lastNode = null;
        return front;
    } // end dequeue
    public T getFront()
    {
        if (isEmpty())
            throw new EmptyStackException(); //FIX ME!!!!!!
        else
            return firstNode.getData();
    } // end getFront

    public boolean isEmpty()
    {
        return (firstNode == null) && (lastNode == null);
    } // end isEmpty

    public void clear()
    {
        firstNode = null;
        lastNode = null;
    } // end clear
    private class Node
    {
        private T data; // Entry in stack
        private Node next; // Link to next node

        private Node(T dataPortion)
        {
            this(dataPortion, null);
        } // end constructor

        private Node(T dataPortion, Node linkPortion)
        {
            data = dataPortion;
            next = linkPortion;
        } // end constructor

        private T getData()
        {
            return data;
        } // end getData

        private void setData(T newData)
        {
            data = newData;
        } // end setData

        private Node getNextNode()
        {
            return next;
        } // end getNextNode

        private void setNextNode(Node nextNode)
        {
            next = nextNode;
        } // end setNextNode
    } // end Node
} // end linkedqueue
