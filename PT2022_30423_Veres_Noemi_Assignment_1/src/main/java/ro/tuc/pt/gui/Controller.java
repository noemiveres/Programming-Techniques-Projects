package ro.tuc.pt.gui;

import ro.tuc.pt.logic.Operations;
import ro.tuc.pt.model.Monomial;
import ro.tuc.pt.model.Polynomial;
import ro.tuc.pt.utils.Converter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller implements ActionListener {

    private View view;

    private Operations operations = new Operations();

    public Controller(View v) {
        this.view = v;
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command == "COMPUTE") {
            Polynomial firstPolynomial = Converter.convertStringToPolynomial(view.getFirstPolynomialTextField().getText());
            Polynomial secondPolynomial = Converter.convertStringToPolynomial(view.getSecondPolynomialTextField().getText());
            String operation = String.valueOf(view.getOperationsComboBox().getSelectedItem());
            Polynomial result = new Polynomial();
            switch (operation) {
                case "Add":
                    result = operations.add(firstPolynomial, secondPolynomial);
                    break;
                case "Subtract":
                    result = operations.subtract(firstPolynomial, secondPolynomial);
                    break;
                case "Multiply":
                    result = operations.multiply(firstPolynomial, secondPolynomial);
                    break;
                case "Divide":
                    result = operations.divide(firstPolynomial, secondPolynomial);
                    break;
                case "Derivative":
                    result = operations.derivative(firstPolynomial);
                    break;
                case "Integral":
                    result = operations.integral(firstPolynomial);
                    break;
            }
            result.sort(new Polynomial.OrderMonomials());
            view.getResultValueLabel().setText(result + " ");
        }
    }
}
