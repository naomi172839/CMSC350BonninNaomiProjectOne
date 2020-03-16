/*
 * Copyright (c) 2020.
 * Author: Naomi Bonnin
 * School: University of Maryland Global Campus
 * Class: CMSC 350
 * Assignment: Project 1
 * Last Update: 3/16/20, 5:49 PM
 * Description:  The goal of this project was to create a program that correctly evaluates a given infix expression and displays the result to the user.  The project makes use of stacks and uses the provided algorithm.  There are several methods of validating an infix expression included.
 */

package edu.umuc.student.nbonnin.project1;

import org.jetbrains.annotations.NotNull;

import java.util.Stack;

/*
The Infix class contains the code to actually evaluate the expression.  In addition, it also contains 3 custom
checked exceptions: EmptyExpression, InvalidExpression, and DivideByZero.  It has 2 class variables, operand and
operator, both of which are stacks.  Note that there is no accounting for integer overflow.
 */
public class Infix {

  private static Stack<String> operand = new Stack<>();
  private static Stack<String> operator = new Stack<>();

  // The only method exposed to the public.  Takes the infix expression as a string for an argument
  // and returns
  // The final answer in the form of a string.  Throws all four exceptions.
  public static String calculate(String expression)
      throws DivideByZero, EmptyExpression, InvalidExpression, ParenthesisMismatch, NumberTooLarge {
    evaluate(tokenize(expression));
    return operand.pop();
  }

  // This method takes the expression as a string for an argument and returns an array containing
  // the tokenized
  // expression.  This method checks if the expression is empty or contains invalid characters and
  // throws the
  // appropriate exception.  Valid characters are: [0-9] * / + - ( )
  @NotNull
  private static String[] tokenize(@NotNull String expression)
      throws EmptyExpression, InvalidExpression, ParenthesisMismatch {
    if (expression.isBlank()) { // checks if expression contains anything
      throw new EmptyExpression("Empty Expression");
    }
    // Strips the whitespace from the expression.
    expression = expression.replaceAll("\\s", "");
    // Creates an array with size length of the expression
    String[] tokenExpression = new String[expression.length()];
    // Creates a new string builder for multi digit numbers
    StringBuilder tokenHolder = new StringBuilder();
    int arrayPosition = 0; // To track position in tokenExpression
    int pCount = 0, oCount = 0, dCount = 0;
    // Converts the expression to an array of characters and iterates through
    for (char c : expression.toCharArray()) {
      if (!Character.toString(c).matches("[*/\\-+()\\d]")) { // Checks for invalid characters
        throw new InvalidExpression(expression);
      }
      if (Character.isDigit(c)) { // Checks if character is a digit
        tokenHolder.append(c);
      } else {
        if (!tokenHolder
            .toString()
            .equals("")) { // If tokenHolder contains anything, it is added to tokenExpression
          tokenExpression[arrayPosition] = tokenHolder.toString();
          tokenHolder = new StringBuilder(); // Resets tokenHolder to be blank
          arrayPosition++;
          dCount++;
        }
        tokenExpression[arrayPosition] = Character.toString(c); // Adds operator to tokenExpression
        if (c == '(' || c == ')') {
          pCount++;
        } else {
          oCount++;
        }
        arrayPosition++;
      }
    }
    if (!tokenHolder
        .toString()
        .equals("")) { // Ensures that the final number gets added to tokenExpression
      tokenExpression[arrayPosition] = tokenHolder.toString();
      dCount++;
    }
    if (pCount % 2 == 1) {
      throw new ParenthesisMismatch(Integer.toString(pCount));
    }
    if (dCount - oCount != 1) {
      throw new InvalidExpression(Integer.toString(dCount - oCount));
    }
    return tokenExpression;
  }

  // This method contains the actual algorithm to evaluate the infix expression.  It takes a
  // tokenized infix expression
  // in the form of the array.  Follows the algorithm provided in class.
  private static void evaluate(@NotNull String[] array) throws DivideByZero, NumberTooLarge {
    for (String token : array) { // Iterates through the tokenExpression
      // Since tokenExpression may be larger than necessary, we ensure we are not checking any null
      // values
      //noinspection ConstantConditions
      if (token == null) {
        break;
      }
      if (token.matches("\\d+")) { // Checks if token is a digit
        operand.push(token);
      } else if (token.matches("\\(")) { // Checks if token is a (
        operator.push(token);
      } else if (token.matches("\\)")) { // Checks if token is a )
        while (!operator.peek().matches("\\(")) { // Evaluates the inside of the parentheses
          operand.push(math(operator.pop(), operand.pop(), operand.pop()));
        }
        operator.pop(); // Removes the ( from the stack
      } else if (token.matches("[*/+\\-]")) { // Checks if token is an operator
        while (!operator.isEmpty()
            && precedence(token, operator.peek())) { // Evaluates expression, uses precedence
          operand.push(math(operator.pop(), operand.pop(), operand.pop()));
        }
        operator.push(token);
      }
    }
    while (!operator.isEmpty()) { // Continues to evaluate expression while operators remain
      operand.push(math(operator.pop(), operand.pop(), operand.pop()));
    }
  }

  // This method takes 3 Strings as an argument:An operator, a left operand, and a right operand.
  // It will return
  // a string containing the value of the operation
  @NotNull
  private static String math(@NotNull String operator, String op1, String op2)
      throws DivideByZero, NumberTooLarge {
    Integer rightOperand, leftOperand;
    try {
      rightOperand = Integer.parseInt(op1); // Converts to integer
      leftOperand = Integer.parseInt(op2); // Converts to integer
    } catch (NumberFormatException e) {
      throw new NumberTooLarge(e.getMessage());
    }
    int result = 0;
    // Addition
    if (operator.matches("\\+")) {
      result = leftOperand + rightOperand;
    }
    // Subtraction
    if (operator.matches("-")) {
      result = leftOperand - rightOperand;
    }
    // Division, catches divide by 0 errors
    if (operator.matches("/")) {
      try {
        result = leftOperand / rightOperand;
      } catch (ArithmeticException e) {
        throw new DivideByZero(e.getMessage());
      }
    }
    // Multiplication
    if (operator.matches("\\*")) {
      result = leftOperand * rightOperand;
    }

    return Integer.toString(result);
  }

  // Checks operator precedence.  Takes to operators as strings as arguments.  Returns a true if the
  // second operator is
  // of higher precedence
  private static boolean precedence(String operator1, @NotNull String operator2) {

    if (operator2.matches("[*/]")) {
      return true;
    } else return operator2.matches("[+\\-]") && operator1.matches("[+\\-]");
  }
}

/*
Below are all of the custom exceptions implemented.  Note that many of these could
be combined into one exception and is better design in my opinion.  However given that
the assignment asked for a custom exception rather than just to catch the ArithmeticException
I elected to create more custom exceptions to really show a mastery of the concept.
 */
class DivideByZero extends Exception {  //For when division by 0 occurs
  DivideByZero(String e) {
    super(e); //Passes the message, allows use of Exception.getMessage()
  }
}

class EmptyExpression extends Exception { //For when nothing or only spaces is entered
  EmptyExpression(String e) {
    super(e); //Same as above, though unnecessary at the moment
  }
}

class InvalidExpression extends Exception {  //For when an expression is invalid, i.e. improper operators
  InvalidExpression(String e) {
    super(e); //Can pass on invalid character
  }
}

class ParenthesisMismatch extends Exception { //For mismatched parenthesis
  ParenthesisMismatch(String e) {
    super(e);
  }
}

class NumberTooLarge extends Exception { //For input outside int range
  NumberTooLarge(String e) {
    super(e);
  }
}
