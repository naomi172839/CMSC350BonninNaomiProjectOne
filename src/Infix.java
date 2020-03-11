import java.util.Arrays;
import java.util.Stack;

public class Infix {

    private static Stack<String> operand = new Stack();
    private static Stack<String> operator = new Stack();

    public static String evaluate(String expression) {
        evaluate(tokenize(expression));
        return operand.pop();
    }

    private static String[] tokenize(String expression) {
        expression = expression.replaceAll("\\s", "");
        expression = expression+"@";
        String[] tokenExpression = new String[expression.length()];
        String tokenHolder = "";
        int arrayPosition = 0;
        for (char c : expression.toCharArray()) {
            if (Character.isDigit(c)) {
                tokenHolder = tokenHolder + c;
            } else {
                if (!tokenHolder.equals("")) {
                    tokenExpression[arrayPosition] = tokenHolder;
                    tokenHolder = "";
                    arrayPosition++;
                }
                tokenExpression[arrayPosition] = Character.toString(c);
                arrayPosition++;
            }
        }
        return tokenExpression;
    }

    private static void evaluate(String[] array) {
        for(String token : array) {
            if(token.equals("@")) {
                break;
            }
            if(token.matches("\\d+")){
                operand.push(token);
            } else if(token.matches("\\(")) {
                operator.push(token);
            } else if(token.matches("\\)")){
                while(!operand.peek().matches("\\(")) {
                    String rightOperand = operand.pop();
                    String leftOperand = operand.pop();
                    String op = operator.pop();
                    operand.push(calculate(op,leftOperand,rightOperand));
                }
            } else if(token.matches("[*/+\\-]")){
                while(!operator.isEmpty() && precidence(token,operator.peek())) {
                    String rightOperand = operand.pop();
                    String leftOperand = operand.pop();
                    String op = operator.pop();
                    operand.push(calculate(op,leftOperand,rightOperand));
                }
                operator.push(token);
            }
        }
        while(!operator.isEmpty()) {
            String rightOperand = operand.pop();
            String leftOperand = operand.pop();
            String op = operator.pop();
            operand.push(calculate(op,leftOperand,rightOperand));
        }
    }

    private static String calculate(String operator, String op1, String op2) {
        Integer leftOperand = Integer.parseInt(op1);
        Integer rightOperand = Integer.parseInt(op2);
        int result = 0;
        if(operator.matches("\\+")) {
            result = leftOperand + rightOperand;
        }
        if(operator.matches("-")) {
            result = leftOperand - rightOperand;
        }
        if(operator.matches("/")) {
            result = leftOperand / rightOperand;
        }
        if (operator.matches("\\*")) {
            result = leftOperand * rightOperand;
        }

        return Integer.toString(result);
    }

    private static boolean precidence(String operator1, String operator2) {

        if(operator2.matches("[*/]")) {
            return true;
        }
        else if(operator2.matches("[+\\-]") && operator1.matches("[+\\-]")) {
            return true;
        }
        else {
            return false;
        }

    }
}