//
// Name:       Murillo,Clarissa
// Project:    4
// Due:        4/13/23
// Course:     cs-2400-03-sp23
//
// Description:
//              An expression tree is a binary tree so we derive a class of expression trees
//              from BinaryTree
//
import java.util.Stack;
// ExpressionTree is a subclass
public class ExpressionTree extends BinaryTree<String> implements ExpressionTreeInterface {
    public ExpressionTree(String[] postfix) {
        Stack<BinaryNode<String>> S = new Stack<>();
        for (String token : postfix) {
            if (!isOperator(token)) {
                S.push(new BinaryNode<>(token));
            } else{
                BinaryNode<String> right = S.pop();
                BinaryNode<String> left = S.pop();
                S.push(new BinaryNode<>(token, left, right));
            }
        }
        setRootNode(S.pop());
    }

    public int evaluate() { // we are dealing with int, so I changed double to int
        return evaluate(getRootNode());
    }

    private int evaluate(BinaryNode<String> rootNode) { // changed to private int
        int result;
        if (rootNode == null) {
            result = 0;
        } else if (rootNode.isLeaf()) {
            String operand = rootNode.getData();
            //result = Integer.parseInt(variable) //I could just put this into getValueOf??
            result = getValueOf(operand);
        } else {
            int firstOperand = evaluate(rootNode.getLeftChild());
            int secondOperand = evaluate(rootNode.getRightChild());
            String operator = rootNode.getData();
            result = compute(operator, firstOperand, secondOperand);
        }
        return result;
    }
    private int getValueOf(String operand){ // modified from the book
        //throw new UnsupportedOperationException("getValueOf to be implemented");
        return Integer.parseInt(operand);
    } // end getValueOf


    private int compute(String operator, int firstOperand, int secondOperand) {
        switch (operator) {
            case "+":
                return firstOperand + secondOperand;
            case "-":
                return firstOperand - secondOperand;
            case "*":
                return firstOperand * secondOperand;
            case "/":
                // added this exception, don't know if it's needed
                if(secondOperand==0){
                    throw new ArithmeticException("Error: division by zero.");
                }
                return firstOperand / secondOperand;
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }
    // from class: you can use an iterator or recursive method
    public void displayTree() {
        displayTree(getRootNode());
    } // end displayTree

    private void displayTree(BinaryNode<String> node) {
        if(node == null){
            return;
        }
        displayTree(node.getLeftChild());
        displayTree(node.getRightChild());
        if(node.isLeaf()){
            System.out.println(node.getData());
        }
        else{
            System.out.println(node.getLeftChild().getData()+ " : " + node.getData() +" : " + node.getRightChild().getData());
        }
    } // end private displayTree
    private boolean isOperator(String token) {
        return "+-*/".contains(token);
    }
} // end ExpressionTree