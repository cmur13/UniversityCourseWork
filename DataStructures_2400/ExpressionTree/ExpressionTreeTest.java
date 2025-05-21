//
// Name:       Murillo,Clarissa
// Project:    4
// Due:        4/13/23
// Course:     cs-2400-03-sp23
//
// Description:
//              This class will convert a valid postfix expression into an expression tree
//              and use the evaluate operation to output the result from the given expression.
//
public class ExpressionTreeTest {
    public static void main(String[] args) {
        System.out.println("Expression Tree by C. Murillo\n");
        // takes in command line arguments
        for (String s : args) {
            ExpressionTreeInterface tree = new ExpressionTree(s.split("\\s+"));
            System.out.println("Input: " + s);
            System.out.println("Value: " + tree.evaluate()); // uses evaluate from ExpressionTree to print out result
            System.out.println("\nPostorder Traversal: ");
            ((ExpressionTree) tree).displayTree(); // why does it only work when I cast it??
        } // end for
    }
} // end ExpressionTreeTest