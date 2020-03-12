import org.jetbrains.annotations.NotNull;

import java.util.Stack;

/*
The Infix class contains the code to actually evaluate the expression.  In addition, it also contains 3 custom
checked exceptions: EmptyExpression, InvalidExpression, and DivideByZero.  It has 2 class variables, operand and
operator, both of which are stacks.
 */
public class Infix {

  private static Stack<String> operand = new Stack<>();
  private static Stack<String> operator = new Stack<>();

  //The only method exposed to the public.  Takes the infix expression as a string for an argument and returns
  //The final answer in the form of a string.  Throws all three exceptions.
  public static String calculate(String expression)
      throws DivideByZero, EmptyExpression, InvalidExpression {
    evaluate(tokenize(expression));
    return operand.pop();
  }
  public static void resetStack() {
    operand = new Stack<>();
    operator = new Stack<>();
  }

  //This method takes the expression as a string for an argument and returns an array containing the tokenized
  //expression.  This method checks if the expression is empty or contains invalid characters and throws the
  //appropriate exception.  Valid characters are: [0-9] * / + - ( )
  @NotNull
  private static String[] tokenize(@NotNull String expression) throws EmptyExpression, InvalidExpression {
    if (expression.isBlank()) {  //checks if expression contains anything
      throw new EmptyExpression("Empty Expression");
    }
    //Strips the whitespace from the expression.
    expression = expression.replaceAll("\\s", "");
    //Creates an array with size length of the expression
    String[] tokenExpression = new String[expression.length()];
    //Creates a new string builder for multi digit numbers
    StringBuilder tokenHolder = new StringBuilder();
    int arrayPosition = 0; //To track position in tokenExpression
    //Converts the expression to an array of characters and iterates through
    for (char c : expression.toCharArray()) {
      if (!Character.toString(c).matches("[*/\\-+()\\d]")) { //Checks for invalid characters
        throw new InvalidExpression(expression);
      }
      if (Character.isDigit(c)) {  //Checks if character is a digit
        tokenHolder.append(c);
      } else {
        if (!tokenHolder.toString().equals("")) { //If tokenHolder contains anything, it is added to tokenExpression
          tokenExpression[arrayPosition] = tokenHolder.toString();
          tokenHolder = new StringBuilder();  //Resets tokenHolder to be blank
          arrayPosition++;
        }
        tokenExpression[arrayPosition] = Character.toString(c);  //Adds operator to tokenExpression
        arrayPosition++;
      }
    }
    if (!tokenHolder.toString().equals("")) {  //Ensures that the final number gets added to tokenExpression
      tokenExpression[arrayPosition] = tokenHolder.toString();
    }
    return tokenExpression;
  }

  //This method contains the actual algorithm to evaluate the infix expression.  It takes a tokenized infix expression
  //in the form of the array.  Follows the algorithm provided in class.
  private static void evaluate(@NotNull String[] array) throws DivideByZero {
    for (String token : array) {  //Iterates through the tokenExpression
      //Since tokenExpression may be larger than necessary, we ensure we are not checking any null values
      if (token == null) {
        break;
      }
      if (token.matches("\\d+")) {  //Checks if token is a digit
        operand.push(token);
      } else if (token.matches("\\(")) {  //Checks if token is a (
        operator.push(token);
      } else if (token.matches("\\)")) {  //Checks if token is a )
        while (!operator.peek().matches("\\(")) {  //Evaluates the inside of the parentheses
          String rightOperand = operand.pop();
          String leftOperand = operand.pop();
          String op = operator.pop();
          operand.push(calculate(op, leftOperand, rightOperand));
        }
        operator.pop();  //Removes the ( from the stack
      } else if (token.matches("[*/+\\-]")) {  //Checks if token is an operator
        while (!operator.isEmpty() && precedence(token, operator.peek())) {  //Evaluates expression, uses precedence
          String rightOperand = operand.pop();
          String leftOperand = operand.pop();
          String op = operator.pop();
          operand.push(calculate(op, leftOperand, rightOperand));
        }
        operator.push(token);
      }
    }
    while (!operator.isEmpty()) {  //Continues to evaluate expression while operators remain
      String rightOperand = operand.pop();
      String leftOperand = operand.pop();
      String op = operator.pop();
      operand.push(calculate(op, leftOperand, rightOperand));
    }
  }

  //This method takes 3 Strings as an argument:An operator, a left operand, and a right operand.  It will return
  //a string containing the value of the operation
  @NotNull
  private static String calculate(@NotNull String operator, String op1, String op2) throws DivideByZero {
    Integer leftOperand = Integer.parseInt(op1);  //Converts to integer
    Integer rightOperand = Integer.parseInt(op2);  //Converts to integer
    int result = 0;
    //Addition
    if (operator.matches("\\+")) {
      result = leftOperand + rightOperand;
    }
    //Subtraction
    if (operator.matches("-")) {
      result = leftOperand - rightOperand;
    }
    //Division, catches divide by 0 errors
    if (operator.matches("/")) {
      try {
        result = leftOperand / rightOperand;
      } catch (ArithmeticException e) {
        throw new DivideByZero(e.getMessage());
      }
    }
    //Multiplication
    if (operator.matches("\\*")) {
      result = leftOperand * rightOperand;
    }

    return Integer.toString(result);
  }

  //Checks operator precedence.  Takes to operators as strings as arguments.  Returns a true if the second operator is
  //of higher precedence
  private static boolean precedence(String operator1, @NotNull String operator2) {

    if (operator2.matches("[*/]")) {
      return true;
    } else return operator2.matches("[+\\-]") && operator1.matches("[+\\-]");
  }
}

class DivideByZero extends Exception {
  DivideByZero(String e) {
    super(e);
  }
}

class EmptyExpression extends Exception {
  EmptyExpression(String e) {
    super(e);
  }
}

class InvalidExpression extends Exception {
  InvalidExpression(String e) {
    super(e);
  }
}
