package ro.tuc.pt;

import org.junit.jupiter.api.Test;
import ro.tuc.pt.logic.Operations;
import ro.tuc.pt.model.Polynomial;
import ro.tuc.pt.utils.Converter;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DivideOperationTest {
    @Test
    public void divideTest() {
        Operations operations = new Operations();
//        Polynomial firstPolynomial = Converter.convertStringToPolynomial("x^3-2x^2+6x-5");
//        Polynomial secondPolynomial = Converter.convertStringToPolynomial("x^2-1");
        Polynomial firstPolynomial = Converter.convertStringToPolynomial("x^3-2x^2+6x");
        Polynomial secondPolynomial = Converter.convertStringToPolynomial("x");
        Polynomial result = operations.divide(firstPolynomial, secondPolynomial);
        result.sort(new Polynomial.OrderMonomials());
//        System.out.println(result + " ");
        assertTrue(String.valueOf(result).equals("x^2-2.0x+6.0"),
                "The result of dividing x^3-2x^2+6x with x is x^2-2.0x+6.0");
    }
}
