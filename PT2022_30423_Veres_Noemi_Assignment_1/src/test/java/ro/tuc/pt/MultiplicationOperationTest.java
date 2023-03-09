package ro.tuc.pt;

import org.junit.jupiter.api.Test;
import ro.tuc.pt.logic.Operations;
import ro.tuc.pt.model.Polynomial;
import ro.tuc.pt.utils.Converter;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MultiplicationOperationTest {
    @Test
    public void multiplicationTest() {
        Operations operations = new Operations();
        Polynomial firstPolynomial = Converter.convertStringToPolynomial("3x^2-x+1");
        Polynomial secondPolynomial = Converter.convertStringToPolynomial("x-2");
        Polynomial result = operations.multiply(firstPolynomial, secondPolynomial);
        result.sort(new Polynomial.OrderMonomials());
        //System.out.println(result + " ");
        assertTrue(String.valueOf(result).equals("3x^3-7x^2+3x-2.0"),
                "The result of multiplying 3x^2-x+1 and x-2 is 3x^3-7x^2+3x-2");
    }
}
