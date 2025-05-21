//
// Name:       Murillo,Clarissa
// Project:    4
// Due:        4/13/23
// Course:     cs-2400-03-sp23
//
// Description:
//              A class of binary tree that can be a superclass of other classes such as the class
//              of expression trees.
//
import java.util.EmptyStackException;
import java.util.Iterator;

public class BinaryTree<T> implements BinaryTreeInterface<T> {
    private BinaryNode<T> root;
    public BinaryTree(){
        root = null;
    } // end default constructor

    public BinaryTree(T rootData){
        root = new BinaryNode<>(rootData);
    } // end constructor
    public BinaryTree(T rootData, BinaryTree<T> leftTree, BinaryTree<T> rightTree){
        initializeTree(rootData, leftTree, rightTree);
    } // end constructor


    public void setTree(T rootData, BinaryTreeInterface<T> leftTree, BinaryTreeInterface<T> rightTree){
        initializeTree(rootData, (BinaryTree<T>) leftTree, (BinaryTree<T>) rightTree);
    } // end setTree


    private void initializeTree(T rootData, BinaryTree<T> leftTree, BinaryTree<T> rightTree){
        //first draft- see later sections for improvemetns
        root = new BinaryNode<>(rootData);
        if(leftTree != null)
            root.setLeftChild(leftTree.root);
        if(rightTree != null)
            root.setRightChild(rightTree.root);
    } // end initializeTree

    //implementations of other methods in treeinterface go here
    public void setRootData(T rootData){
        root.setData(rootData);
    } // end setRootData

    // Below are the methods from TreeInterface.java
    public T getRootData(){
        if(isEmpty())
            throw new EmptyStackException();
        else
            return root.getData();
    } // end getRootData

    public int getHeight()
    {
        int height = 0;
        if (root != null)
            height = root.getHeight();
        return height;
    } // end getHeight
    public int getNumberOfNodes()
    {
        int numberOfNodes = 0;
        if (root != null)
            numberOfNodes = root.getNumberOfNodes();
        return numberOfNodes;
    } // end getNumberOfNodes
    public boolean isEmpty(){
        return root == null;
    } // end isEmpty
    public void clear(){
        root = null;
    } // end clear
    protected void setRootNode(BinaryNode<T> rootNode){
        root = rootNode;
    } // end setRootNode
    // expression tree is a subclass thus it can set the root node and get the root node. Learn inheritance
    protected BinaryNode<T> getRootNode(){
        return root;
    } // end getRootNode

    // Below are the methods from TreeIteratorInterface.java
    public Iterator<T> getPreorderIterator(){
        throw new UnsupportedOperationException("getPreorderIterator to be implemented");
    }
    public Iterator<T> getPostorderIterator(){
        throw new UnsupportedOperationException("getPostorderIterator to be implemented");
    }
    public Iterator<T> getInorderIterator(){
        throw new UnsupportedOperationException("getInorderIterator to be implemented");
    }
    public Iterator<T> getLevelOrderIterator(){
        throw new UnsupportedOperationException("getLevelOrderIterator to be implemented");
    }
} // end BinaryTree
