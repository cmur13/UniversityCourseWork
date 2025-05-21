//
//  Name:     Murillo, Clarissa
//  Project:  2
//  Due:      2/21/2023
//  Course:   cs 2400.03
//
//  Description:
//    Expression converts an infix expression to its postfix expression and evaluates that
//    postfix expression
//
public class Expression {

    private static final String operators = "+-*/^";
    private static final int[] order = {1, 1, 2, 2, 3};
    //infixExpression- each element is a token that can be an operator or an integer literal
    //Operator can be +, -, *, /, or ^
    public static String[] convertToPostfix(String[] infixExpression) throws RuntimeException {
        LinkedStack<String> stack = new LinkedStack<>();
        String[] postfixExpression = new String[infixExpression.length];
        int postfixIndex = 0;
        for (int i = 0; i < infixExpression.length; i++) {
            String token = infixExpression[i];
            int tokenPrecedence = getPrecedence(token);
            if (isOperator(token)) {
                while (!stack.isEmpty() && getPrecedence(stack.peek()) >= tokenPrecedence) {
                    postfixExpression[postfixIndex++] = stack.pop();
                }
                stack.push(token);
            } else {
                postfixExpression[postfixIndex++] = token;
            }
        }
        while (!stack.isEmpty()) {
            postfixExpression[postfixIndex++] = stack.pop();
        }
        return postfixExpression;
    }

    public static int evaluatePostfix(String[] postfixExpression) throws RuntimeException {
        LinkedStack<Integer> stack = new LinkedStack<>();
        for (int i = 0; i < postfixExpression.length; i++) {
            String token = postfixExpression[i];
            if (isOperator(token)) {
                int result = 0;
                //remove and return the stack object
                int operand2 = stack.pop();
                int operand1 = stack.pop();
                switch (token) {
                    case "+":
                        result = operand1 + operand2;
                        break;
                    case "-":
                        result = operand1 - operand2;
                        break;
                    case "*":
                        result = operand1 * operand2;
                        break;
                    case "/":
                        result = operand1 / operand2;
                        break;
                    case "^":
                        result = (int) Math.pow(operand1, operand2);
                        break;
                }
                stack.push(result);
            } else {
                stack.push(Integer.parseInt(token));
            }
        }
        return stack.pop();
    }
    //gets the order of the operators in place
    private static int getPrecedence(String token) {
        int index = operators.indexOf(token);
        if (index >= 0) {
            return order[index];
        }
        return -1;
    }

    private static boolean isOperator(String token) {

        return operators.contains(token);
    }

}



