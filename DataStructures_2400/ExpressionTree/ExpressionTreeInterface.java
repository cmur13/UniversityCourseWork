//
// Name:       Murillo,Clarissa
// Project:    4
// Due:        4/13/23
// Course:     cs-2400-03-sp23
//
// Description:
//              An interface for an expression tree that extends the interface for
//              a binary tree and adds a declaration for the method evaluate.
//
public interface ExpressionTreeInterface extends BinaryTreeInterface<String> {
    /** Computes the value of the expression in this tree.
        @return The value of the expression. */
    public int evaluate();
} // end ExpressionTreeInterface
