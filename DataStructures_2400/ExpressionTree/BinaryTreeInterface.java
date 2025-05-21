//
// Name:       Murillo,Clarissa
// Project:    4
// Due:        4/13/23
// Course:     cs-2400-03-sp23
//
// Description:
//              Interface for a class of binary trees that extends more than one interface.
//
public interface BinaryTreeInterface<T> extends TreeInterface<T>, TreeIteratorInterface<T>{
    public void setRootData(T rootData);
    public void setTree(T rootData, BinaryTreeInterface<T> leftTree,
                        BinaryTreeInterface<T> rightTree);
} // end BinaryTreeInterface
