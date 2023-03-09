package ro.tuc.pt;

import org.junit.jupiter.api.Test;
import ro.tuc.pt.logic.Operations;
import ro.tuc.pt.model.Polynomial;
import ro.tuc.pt.utils.Converter;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class IntegralOperationTest {
    @Test
    public void integralTest() {
        Operations operations = new Operations();
        Polynomial firstPolynomial = Converter.convertStringToPolynomial("x^3+12x^2+5");
        Polynomial result = operations.integral(firstPolynomial);
        result.sort(new Polynomial.OrderMonomials());
        System.out.println(result + " ");
        assertTrue(String.valueOf(result).equals("0.25x^4+4.0x^3+5x"),
                "The result of integral of x^3+12x^2+5 is 0.25x^4+4.0x^3+5x");
    }
}
