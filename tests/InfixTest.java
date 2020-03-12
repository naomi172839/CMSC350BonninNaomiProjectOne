import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InfixTest {

  @Test
  void correctMath() {
    String addition = "2+2";
    String subtraction = "10-5";
    String multiplication = "2*10";
    String division = "100/10";
    try {
      assertEquals("4", Infix.calculate(addition));
      assertEquals("5", Infix.calculate(subtraction));
      assertEquals("20", Infix.calculate(multiplication));
      assertEquals("10", Infix.calculate(division));
    } catch (DivideByZero | EmptyExpression | InvalidExpression | ParenthesisMismatch e) {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  void calculateIntegerDivision() {
    String e1 = "12/4";
    String e2 = "12/5";
    String e3 = "122/(7+3)";
    try {
      assertEquals("3", Infix.calculate(e1));
      assertEquals("2", Infix.calculate(e2));
      assertEquals("12", Infix.calculate(e3));
    } catch (DivideByZero | EmptyExpression | InvalidExpression | ParenthesisMismatch e) {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  void calculateNormalExpression() {
    String e1 = "12+4";
    String e2 = "(6+4)/2+9-(6*2)";
    String e3 = "12/2+7*2";
    try {
      assertEquals("16", Infix.calculate(e1));
      assertEquals("2", Infix.calculate(e2));
      assertEquals("20", Infix.calculate(e3));
    } catch (DivideByZero | EmptyExpression | InvalidExpression | ParenthesisMismatch e) {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  void calculateExpressionWithWhiteSpace() {
    String e1 = "12 +4";
    String e2 = "(      6+4 )/2 +   9-( 6*  2)                   ";
    String e3 = "                                        12/2+7*2";
    try {
      assertEquals("16", Infix.calculate(e1));
      assertEquals("2", Infix.calculate(e2));
      assertEquals("20", Infix.calculate(e3));
    } catch (DivideByZero | EmptyExpression | InvalidExpression | ParenthesisMismatch e) {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  void shouldThrowDivideByZero() {
    String e1 = "12/0";
    String e2 = "0/12";
    String e3 = "14/(12-12)";
    assertThrows(
        DivideByZero.class,
        () -> Infix.calculate(e1));
    assertThrows(
        DivideByZero.class,
        () -> Infix.calculate(e3));
    try {
      Infix.calculate(e2);
    } catch (ParenthesisMismatch | InvalidExpression | DivideByZero | EmptyExpression e) {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  void shouldThrowEmptyExpression() {
    String e1 = "";
    String e2 = "12+4";
    String e3 = "                        ";
    assertThrows(
            EmptyExpression.class,
            () -> Infix.calculate(e1));
    assertThrows(
            EmptyExpression.class,
            () -> Infix.calculate(e3));
    try {
      Infix.calculate(e2);
    } catch (ParenthesisMismatch | InvalidExpression | DivideByZero | EmptyExpression e) {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  void shouldThrowInvalidExpression() {
    String e1 = "12/3%12+4";
    String e2 = "12+4";
    String e3 = "(12+4)7-13";
    assertThrows(
            InvalidExpression.class,
            () -> Infix.calculate(e1));
    assertThrows(
            InvalidExpression.class,
            () -> Infix.calculate(e3));
    try {
      Infix.calculate(e2);
    } catch (ParenthesisMismatch | InvalidExpression | DivideByZero | EmptyExpression e) {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  void shouldThrowParenthesisMismatch() {
    String e1 = "((12+18)-7";
    String e2 = "(12+4)";
    String e3 = "(13-7+(4+2)))";
    assertThrows(
            ParenthesisMismatch.class,
            () -> Infix.calculate(e1));
    assertThrows(
            ParenthesisMismatch.class,
            () -> Infix.calculate(e3));
    try {
      Infix.calculate(e2);
    } catch (ParenthesisMismatch | InvalidExpression | DivideByZero | EmptyExpression e) {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  void resetStack() {
    //Intentionally blank
  }
}
