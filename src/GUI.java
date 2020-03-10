import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {

    static final int WIDTH=600,HEIGHT=400;

    public GUI() {
        super("Default Title");
        setFrame(WIDTH, HEIGHT);
    }

    public GUI(String title){
        super(title);
        setFrame(WIDTH, HEIGHT);
    }

    public GUI(String title, int width, int height){
        super(title);
        setFrame(width, height);
    }

    private void setFrame(int width, int height) {
        setSize(width, height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(infixCalc());
    }

    private JPanel infixCalc(){
        JPanel content = new JPanel(new GridLayout(3,0));
        JPanel expressionRow = new JPanel(new GridBagLayout());
        JPanel evaluateRow = new JPanel(new GridBagLayout());
        JPanel resultRow = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        JLabel expressionLabel = new JLabel("Enter Infix Expression");
        JLabel resultLabel = new JLabel("Result");
        JTextField expressionText = new JTextField();
        JTextField resultText = new JTextField();
        JButton evaluate = new JButton("Evaluate");

        c.anchor = GridBagConstraints.LINE_START;
        c.fill = GridBagConstraints.NONE;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.insets = new Insets(5,5,5,5);
        c.weightx=0.5;
        expressionRow.add(expressionLabel, c);

        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 3;
        c.insets = new Insets(5,5,5,5);
        c.weightx=3;
        expressionRow.add(expressionText, c);

        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.NONE;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.insets = new Insets(5,5,5,5);
        c.weightx=0;
        evaluateRow.add(evaluate, c);

        c.anchor = GridBagConstraints.LINE_START;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.insets = new Insets(5,5,5,5);
        c.weightx=0.5;
        resultRow.add(resultLabel, c);

        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 3;
        c.insets = new Insets(5,5,5,5);
        c.weightx=3;
        resultRow.add(resultText, c);

        content.add(expressionRow);
        content.add(evaluate);
        content.add(resultRow);
        return content;
    }
}
