//
// Name:       Murillo,Clarissa
// Project:    4
// Due:        4/13/23
// Course:     cs-2400-03-sp23
//
// Description:
//              A class of nodes suitable for a binary tree.
//
public class BinaryNode<T> {
    private T data;
    private BinaryNode<T> leftChild;
    private BinaryNode<T> rightChild;


    public BinaryNode() {
        this(null);
    }


    public BinaryNode(T dataPortion) {
        this(dataPortion, null, null);
    } // end constructor


    public BinaryNode(T dataPortion, BinaryNode<T> newLeftChild, BinaryNode<T> newRightChild) {
        data =dataPortion;
        leftChild =newLeftChild;
        rightChild =newRightChild;
    } // end constructor
    public T getData(){
        return data;
    }
    public void setData(T newData){
        data = newData;
    }
    public BinaryNode<T> getLeftChild(){
        return leftChild;
    }
    public void setLeftChild(BinaryNode<T> newLeftChild){
        leftChild = newLeftChild;
    } // end setLeftChild
    public boolean hasLeftChild(){
        return leftChild != null;
    }
    public BinaryNode<T> getRightChild(){
        return rightChild;
    }
    public void setRightChild(BinaryNode<T> newRightChild){
        rightChild = newRightChild;
    } // end setLeftChild
    public boolean hasRightChild(){
        return rightChild != null;
    }
    public boolean isLeaf(){
        return(leftChild == null) && (rightChild == null);
    } // end ifLeaf
    public int getNumberOfNodes()
    {
        int leftNumber = 0;
        int rightNumber = 0;
        if (leftChild != null)
            leftNumber = leftChild.getNumberOfNodes();
        if (rightChild != null)
            rightNumber = rightChild.getNumberOfNodes();
        return 1 + leftNumber + rightNumber;
    } // end getNumberOfNodes
    public int getHeight()
    {
        return getHeight(this); // Call private getHeight
    } // end getHeight
    private int getHeight(BinaryNode<T> node)
    {
        int height = 0;
        if (node != null)
            height = 1 + Math.max(getHeight(node.getLeftChild()),
                    getHeight(node.getRightChild()));
        return height;
    } // end getHeight


    public BinaryNode<T> copy(){
        BinaryNode<T> newRoot = new BinaryNode<>(data);
        if (leftChild != null)
            newRoot.setLeftChild(leftChild.copy());
        if (rightChild != null)
            newRoot.setRightChild(rightChild.copy());
        return newRoot;


    }


}

