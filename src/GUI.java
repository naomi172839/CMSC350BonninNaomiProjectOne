import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.EmptyStackException;

/*
The GUI class is responsible for creating the GUI that the user interacts with.  It has no public methods and contains
one constructor which takes no arguments.  Upon calling the constructor, the GUI is created and displayed to the user.
Closing the GUI will terminate the application.
 */
public class GUI {

  static final Dimension d = new Dimension(600, 175);

  // Constructor to create and show the GUI
  public GUI() {
    JFrame frame = new JFrame("Infix Expression Evaluator");
    frame.setPreferredSize(d);
    frame.setResizable(false);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.add(infixCalc()); // This line adds the actual content to the frame
    frame.pack();
    frame.setLocationRelativeTo(null); // Centers the window on the screen
    frame.setVisible(true);
  }

  // Method to create the actual content for the application
  @NotNull
  private JPanel infixCalc() {
    // Creates a panel to hold the conent
    JPanel content = new JPanel(new GridBagLayout());

    // A constraints object to hold layout information
    GridBagConstraints c = new GridBagConstraints();

    // Different font for readability
    Font f = new Font("Monaco", Font.PLAIN, 16);

    JLabel expressionLabel = new JLabel("Enter Infix Expression:");
    expressionLabel.setFont(f);

    JLabel resultLabel = new JLabel("Result:");
    resultLabel.setFont(f);

    // Create expression textfield, changes the font and adds a tooltip
    JTextField expressionText = new JTextField();
    expressionText.setFont(f);
    expressionText.setToolTipText(
        "Please enter a valid integer infix expression. Valid symbols: +-*/()");

    // Creates result textfield, makes it uneditable, change font and makes transparent.
    JTextField resultText = new JTextField();
    resultText.setEditable(false);
    resultText.setFont(f);
    resultText.setBackground(new Color(0, 0, 0, 0));

    JButton evaluate = new JButton("Evaluate");

    // Sets the constraints for the expression label and adds it to the panel
    c.anchor = GridBagConstraints.LINE_END;
    c.fill = GridBagConstraints.NONE;
    c.gridx = 0;
    c.gridy = 0;
    c.gridwidth = 1;
    c.insets = new Insets(5, 0, 5, 5);
    c.weightx = 0.25;
    c.weighty = 1;
    content.add(expressionLabel, c);

    // Sets the constraints for the expression textfield and adds it to the panel
    c.anchor = GridBagConstraints.CENTER;
    c.fill = GridBagConstraints.BOTH;
    c.gridx = 1;
    c.gridy = 0;
    c.gridwidth = 6;
    c.insets = new Insets(5, 5, 5, 5);
    c.weightx = 3;
    c.weighty = 1;
    content.add(expressionText, c);

    // Sets constraints for the evaluate button, creates an action listener, and adds it to the
    // panel
    c.anchor = GridBagConstraints.CENTER;
    c.fill = GridBagConstraints.VERTICAL;
    c.gridx = 0;
    c.gridy = 1;
    c.gridwidth = 7;
    c.insets = new Insets(5, 5, 5, 5);
    c.weightx = 1;
    c.weighty = 0.5;
    // Adds a new action listener to the button to determine when an event occurs
    evaluate.addActionListener(
        e -> {
          try {
            resultText.setText(Infix.calculate(expressionText.getText()));
            Infix.resetStack();
          } catch (DivideByZero divideByZero) { // This is a custom checked exception
            JOptionPane.showMessageDialog(content, "You can not divide by 0");
          } catch (EmptyExpression emptyExpression) { // This is another custom checked exception
            JOptionPane.showMessageDialog(content, "Please enter an expression");
          } catch (InvalidExpression
              | EmptyStackException
              | ParenthesisMismatch invalidExpression) { // If expresion is invalid, reset prog.
            JOptionPane.showMessageDialog(content, "The entered expression is invalid");
            Infix.resetStack();
            resultText.setText("");
          }
        });
    content.add(evaluate, c);

    // Sets the constraints for the result label and adds it to the panel
    c.anchor = GridBagConstraints.LINE_END;
    c.fill = GridBagConstraints.NONE;
    c.gridx = 0;
    c.gridy = 2;
    c.gridwidth = 1;
    c.insets = new Insets(5, 0, 5, 5);
    c.weightx = 0.25;
    c.weighty = 1;
    content.add(resultLabel, c);

    // Sets the constraints for the result textfield and adds it to the panel
    c.anchor = GridBagConstraints.CENTER;
    c.fill = GridBagConstraints.BOTH;
    c.gridx = 1;
    c.gridy = 2;
    c.gridwidth = 6;
    c.insets = new Insets(5, 5, 5, 5);
    c.weightx = 3;
    c.weighty = 1;
    content.add(resultText, c);

    return content;
  }
}
