//
//  Name:     Murillo, Clarissa
//  Project:  2
//  Due:      2/21/2023
//  Course:   cs 2400.03
//
//  Description:
//    ExpressionTest takes an integer infix expression from the command line and tests
//    the methods in Expression.java
//
import java.util.Arrays;


public class ExpressionTest {


    public static void main(String[] args) {
        System.out.println("Expression by C.Murillo\n");
        //no argument passed
        if (args.length == 0) {
            System.out.println("No expression to evaluate");
        }
        //loop for each argument of the command line
        for (String s : args) {
            try {
                String[] infixExpression = s.split(" "); //splits the String s into an array of Strings
                String[] postfixExpression = Expression.convertToPostfix(infixExpression);
                int result = Expression.evaluatePostfix(postfixExpression);
                //prints out the command line argument
                System.out.println(s);
                //returns the postfix expression and outputs the result of the postfix
                System.out.println("     " + Arrays.toString(postfixExpression) + " = " + result);
            } catch (IllegalArgumentException msg) {
                System.out.println("Invalid expression: " + msg.getMessage());//If the user types an invalid expression
            }
        }
    }
}

