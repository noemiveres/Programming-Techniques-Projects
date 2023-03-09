package ro.tuc.pt.gui;

import javax.swing.*;
import java.awt.*;

public class View extends JFrame {
    private JPanel contentPane;
    private JPanel polynomialsPanel;
    private JLabel firstPolynomialLabel;
    private JTextField firstPolynomialTextField;
    private JLabel secondPolynomialLabel;
    private JTextField secondPolynomialTextField;
    private JLabel operationsLabel;
    private JComboBox operationsComboBox;
    private JButton computeButton;
    private JPanel resultPanel;
    private JLabel resultLabel;
    private JLabel resultValueLabel;
    private JPanel remainderPanel;
    private JLabel remainderLabel;
    private JLabel remainderValueLabel;

    Controller controller = new Controller(this);

    public View(String name) {
        super(name);
        this.prepareGui();
    }

    public void prepareGui(){
        this.setSize(500, 500);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.contentPane = new JPanel(new GridLayout(3, 2));
        this.preparePolynomialsPanel();
        this.prepareResultPanel();
        this.prepareRemainderPanel();
        this.setContentPane(this.contentPane);
    }

    private void prepareResultPanel() {
        this.resultPanel = new JPanel();
        this.resultPanel.setLayout(new GridLayout(1,1));
        this.resultLabel = new JLabel("Result", JLabel.CENTER);
        this.resultValueLabel = new JLabel("", JLabel.CENTER);
        this.resultPanel.add(this.resultLabel);
        this.resultPanel.add(this.resultValueLabel);
        this.contentPane.add(this.resultPanel);
    }

    private void prepareRemainderPanel() {
        this.remainderPanel = new JPanel();
        this.remainderPanel.setLayout(new GridLayout(1,1));
        this.remainderLabel = new JLabel("Remainder", JLabel.CENTER);
        this.remainderValueLabel = new JLabel("", JLabel.CENTER);
        this.remainderPanel.add(this.remainderLabel);
        this.remainderPanel.add(this.remainderValueLabel);
        this.contentPane.add(this.remainderPanel);
    }

    private void preparePolynomialsPanel() {
        this.polynomialsPanel = new JPanel();
        this.polynomialsPanel.setLayout(new GridLayout(4, 2));
        this.firstPolynomialLabel = new JLabel("First polynomial", JLabel.CENTER);
        this.polynomialsPanel.add(this.firstPolynomialLabel);
        this.firstPolynomialTextField = new JTextField();
        this.polynomialsPanel.add(this.firstPolynomialTextField);
        this.secondPolynomialLabel = new JLabel("Second polynomial", JLabel.CENTER);
        this.polynomialsPanel.add(secondPolynomialLabel);
        this.secondPolynomialTextField = new JTextField();
        this.polynomialsPanel.add(secondPolynomialTextField);
        this.operationsLabel = new JLabel("Select operation", JLabel.CENTER);
        this.polynomialsPanel.add(this.operationsLabel);
        String[] operations = new String[]{"Add", "Subtract", "Multiply","Divide","Derivative","Integral"};
        this.operationsComboBox = new JComboBox(operations);
        this.polynomialsPanel.add(operationsComboBox);
        this.computeButton = new JButton("Compute");
        this.computeButton.setActionCommand("COMPUTE");
        this.computeButton.addActionListener(this.controller);
        this.polynomialsPanel.add(this.computeButton);
        this.contentPane.add(this.polynomialsPanel);
    }

    public JTextField getFirstPolynomialTextField() {
        return firstPolynomialTextField;
    }

    public JTextField getSecondPolynomialTextField() {
        return secondPolynomialTextField;
    }

    public JComboBox getOperationsComboBox() {
        return operationsComboBox;
    }

    public JLabel getResultValueLabel() {
        return resultValueLabel;
    }

    public JLabel getRemainderValueLabel() {
        return remainderValueLabel;
    }
}
