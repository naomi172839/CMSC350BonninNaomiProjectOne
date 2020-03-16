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

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InfixTest {

  @Test
  void correctMath() {
    String addition = "2+2"; //Addition
    String subtraction = "10-5"; //Subtraction
    String multiplication = "2*10"; //Multiplication
    String division = "100/10"; //Division
    try {
      assertEquals("4", Infix.calculate(addition));
      assertEquals("5", Infix.calculate(subtraction));
      assertEquals("20", Infix.calculate(multiplication));
      assertEquals("10", Infix.calculate(division));
    } catch (DivideByZero
            | EmptyExpression
            | InvalidExpression
            | ParenthesisMismatch
            | NumberTooLarge e) {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  void correctParentheses() {
    String e1 = "(12+4)"; //Beginning parenthesis, no effect on math
    String e2 = "18+(9+9)"; //End parenthesis
    String e3 = "(14+16+20)-30"; //Multiple digits inside parenthesis
    String e4 = "((12+4)/(2+2))"; //Parenthesis where the evaluation is changed

    try {
      assertEquals("16", Infix.calculate(e1));
      assertEquals("36", Infix.calculate(e2));
      assertEquals("20", Infix.calculate(e3));
      assertEquals("4", Infix.calculate(e4));
    } catch (DivideByZero
            | EmptyExpression
            | InvalidExpression
            | ParenthesisMismatch
        | NumberTooLarge e) {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  void correctPrecedence() {
    String e1 = "2+2-4"; //Same precedence
    String e2 = "2*2-1"; //Beginning more important
    String e3 = "4-2*6"; //End more important
    String e4 = "(12+8*4)-(6*2+4)/(2+2)"; //All operators evaluated throughout
    try {
      assertEquals("0", Infix.calculate(e1));
      assertEquals("3", Infix.calculate(e2));
      assertEquals("-8", Infix.calculate(e3));
      assertEquals("40", Infix.calculate(e4));
    } catch (DivideByZero
            | EmptyExpression
            | InvalidExpression
            | ParenthesisMismatch
            | NumberTooLarge e) {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  void calculateIntegerDivision() {
    String e1 = "12/4"; //Normal
    String e2 = "12/5"; //Drop remainder
    String e3 = "122/(7+3)"; //With parenthesis
    try {
      assertEquals("3", Infix.calculate(e1));
      assertEquals("2", Infix.calculate(e2));
      assertEquals("12", Infix.calculate(e3));
    } catch (DivideByZero
            | EmptyExpression
            | InvalidExpression
            | ParenthesisMismatch
            | NumberTooLarge e) {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  void calculateNormalExpression() {
    String e1 = "12+4"; //Normal, 1 operator
    String e2 = "(6+4)/2+9-(6*2)";  //Normal all operators, with parenthesis
    String e3 = "12/2+7*2"; //Normal, all operators, no parenthesis
    try {
      assertEquals("16", Infix.calculate(e1));
      assertEquals("2", Infix.calculate(e2));
      assertEquals("20", Infix.calculate(e3));
    } catch (DivideByZero
            | EmptyExpression
            | InvalidExpression
            | ParenthesisMismatch
            | NumberTooLarge e) {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  void calculateExpressionWithWhiteSpace() {
    String e1 = "12 +4"; //1 space
    String e2 = "(      6+4 )/2 +   9-( 6*  2)                   "; //Many spaces
    String e3 = "                                        12/2+7*2"; //Lots of begining space
    try {
      assertEquals("16", Infix.calculate(e1));
      assertEquals("2", Infix.calculate(e2));
      assertEquals("20", Infix.calculate(e3));
    } catch (DivideByZero
            | EmptyExpression
            | InvalidExpression
            | ParenthesisMismatch
            | NumberTooLarge e) {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  void shouldThrowDivideByZero() {
    String e1 = "12/0"; //Normal divide by 0
    String e2 = "0/12"; //Should be 0
    String e3 = "14/(12-12)"; //Divide by 0 when not explicit
    assertThrows(DivideByZero.class, () -> Infix.calculate(e1));
    assertThrows(DivideByZero.class, () -> Infix.calculate(e3));
    try {
      Infix.calculate(e2);
    } catch (ParenthesisMismatch
            | InvalidExpression
            | DivideByZero
            | EmptyExpression
            | NumberTooLarge e) {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  void shouldThrowEmptyExpression() {
    String e1 = ""; //Nothing present
    String e2 = "12+4"; // Normal expression
    String e3 = "                        "; //Lots of whitespace
    assertThrows(EmptyExpression.class, () -> Infix.calculate(e1));
    assertThrows(EmptyExpression.class, () -> Infix.calculate(e3));
    try {
      Infix.calculate(e2);
    } catch (ParenthesisMismatch
            | InvalidExpression
            | DivideByZero
            | EmptyExpression
            | NumberTooLarge e) {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  void shouldThrowInvalidExpression() {
    String e1 = "12/3%12+4"; //Invalid operator
    String e2 = "12+4"; //Normal
    String e3 = "(12+4)7-13"; //Missing operator
    assertThrows(InvalidExpression.class, () -> Infix.calculate(e1));
    assertThrows(InvalidExpression.class, () -> Infix.calculate(e3));
    try {
      Infix.calculate(e2);
    } catch (ParenthesisMismatch
            | InvalidExpression
            | DivideByZero
            | EmptyExpression
            | NumberTooLarge e) {
      fail();
    }
  }

  @Test
  void shouldThrowParenthesisMismatch() {
    String e1 = "((12+18)-7"; //Missing at end
    String e2 = "(12+4)"; //Normal
    String e3 = "(13-7+(4+2)))"; //Missing at beginning
    assertThrows(ParenthesisMismatch.class, () -> Infix.calculate(e1));
    assertThrows(ParenthesisMismatch.class, () -> Infix.calculate(e3));
    try {
      Infix.calculate(e2);
    } catch (ParenthesisMismatch
            | InvalidExpression
            | DivideByZero
            | EmptyExpression
            | NumberTooLarge e) {
      fail();
    }
  }

  @Test
  void shouldThrowNumberTooLarge() {
    String e1 = "10000000000000000+14"; //Beginning number too large
    String e2 = "1+2+3+4"; //Normal
    String e3 = "100*100+300*1000+4*1234567890123456789"; //Last number too large
    assertThrows(NumberTooLarge.class, () -> Infix.calculate(e1));
    assertThrows(NumberTooLarge.class, () -> Infix.calculate(e3));
    try {
      Infix.calculate(e2);
    } catch (ParenthesisMismatch
            | InvalidExpression
            | DivideByZero
            | EmptyExpression
            | NumberTooLarge e) {
      e.printStackTrace();
      fail();
    }
  }
}
