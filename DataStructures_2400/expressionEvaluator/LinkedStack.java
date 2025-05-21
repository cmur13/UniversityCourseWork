//
//  Name:     Murillo, Clarissa
//  Project:  2
//  Due:      2/21/2023
//  Course:   cs 2400.03
//
//  Description:
//    LinkedStack implements the stack ADT methods
//
import java.util.EmptyStackException;

public final class LinkedStack<T> implements StackInterface<T>
{
    private Node topNode; // References the first node in the chain

    public LinkedStack()
    {
        topNode = null;
    } // end default constructor

    public void push(T newEntry)
    {
        Node newNode = new Node(newEntry, topNode);
        topNode = newNode;
    } // end push

    public T peek()
    {
        if (isEmpty())
            throw new EmptyStackException();
        else
            return topNode.getData();
    } // end peek

    public T pop()
    {
        T top = peek(); // Might throw EmptyStackException
        assert topNode != null;
        topNode = topNode.getNextNode();

        return top;
    } // end pop

    public boolean isEmpty()
    {
        return topNode == null;
    } // end isEmpty

    public void clear()
    {
        topNode = null;
    } // end clear

    // Private class
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
} // end LinkedStack

