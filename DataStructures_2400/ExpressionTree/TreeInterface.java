//
// Name:       Murillo,Clarissa
// Project:    4
// Due:        4/13/23
// Course:     cs-2400-03-sp23
//
// Description:
//              An interface of methods common to all trees. This interface uses the
//              generic type T as the type of data in the nodes of the tree.
//
public interface TreeInterface<T> {
    public T getRootData();
    public int getHeight();
    public int getNumberOfNodes();
    public boolean isEmpty();
    public void clear();
} // end TreeInterface
