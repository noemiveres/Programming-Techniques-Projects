package ro.tuc.pt;

import org.junit.jupiter.api.Test;
import ro.tuc.pt.logic.Operations;
import ro.tuc.pt.model.Polynomial;
import ro.tuc.pt.utils.Converter;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DerivativeOperationTest {
    @Test
    public void derivativeTest() {
        Operations operations = new Operations();
        Polynomial firstPolynomial = Converter.convertStringToPolynomial("x^3-2x^2+6x-5");
        Polynomial result = operations.derivative(firstPolynomial);
        result.sort(new Polynomial.OrderMonomials());
        //System.out.println(result + " ");
        assertTrue(String.valueOf(result).equals("3x^2-4x+6.0"),
                "The result of deriving x^3-2x^2+6x-5 is 3x^2-4x+6");
    }
}
