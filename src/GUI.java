import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI {

  static final Dimension d = new Dimension(600, 175);

  public GUI() {
    JFrame frame = new JFrame("Infix Expression Evaluator");
    frame.setPreferredSize(d);
    frame.setResizable(false);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.add(infixCalc());
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }

  private JPanel infixCalc() {
    JPanel content = new JPanel(new GridBagLayout());

    GridBagConstraints c = new GridBagConstraints();

    Font f = new Font("Monaco", Font.PLAIN, 16);

    JLabel expressionLabel = new JLabel("Enter Infix Expression:");

    JLabel resultLabel = new JLabel("Result:");

    JTextField expressionText = new JTextField();
    expressionText.setFont(f);
    JTextField resultText = new JTextField();
    resultText.setEditable(false);
    resultText.setFont(f);
    resultText.setBackground(Color.LIGHT_GRAY);
    JButton evaluate = new JButton("Evaluate");

    c.anchor = GridBagConstraints.LINE_END;
    c.fill = GridBagConstraints.NONE;
    c.gridx = 0;
    c.gridy = 0;
    c.gridwidth = 1;
    c.insets = new Insets(5, 0, 5, 5);
    c.weightx = 0.25;
    c.weighty = 1;
    content.add(expressionLabel, c);

    c.anchor = GridBagConstraints.CENTER;
    c.fill = GridBagConstraints.BOTH;
    c.gridx = 1;
    c.gridy = 0;
    c.gridwidth = 6;
    c.insets = new Insets(5, 5, 5, 5);
    c.weightx = 3;
    c.weighty = 1;
    content.add(expressionText, c);

    c.anchor = GridBagConstraints.CENTER;
    c.fill = GridBagConstraints.VERTICAL;
    c.gridx = 0;
    c.gridy = 1;
    c.gridwidth = 7;
    c.insets = new Insets(5, 5, 5, 5);
    c.weightx = 1;
    c.weighty = 0.5;
    evaluate.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            try {
              resultText.setText(Infix.evaluate(expressionText.getText()));
            } catch (DivideByZero divideByZero) {
              JOptionPane.showMessageDialog(content, "You can not divide by 0");
            }
          }
        });
    content.add(evaluate, c);

    c.anchor = GridBagConstraints.LINE_END;
    c.fill = GridBagConstraints.NONE;
    c.gridx = 0;
    c.gridy = 2;
    c.gridwidth = 1;
    c.insets = new Insets(5, 0, 5, 5);
    c.weightx = 0.25;
    c.weighty = 1;
    content.add(resultLabel, c);

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
