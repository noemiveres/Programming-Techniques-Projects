package ro.tuc.pt;

import org.junit.jupiter.api.Test;
import ro.tuc.pt.logic.Operations;
import ro.tuc.pt.model.Polynomial;
import ro.tuc.pt.utils.Converter;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SubtractOperationTest {
    @Test
    public void subtractTest() {
        Operations operations = new Operations();
//        Polynomial firstPolynomial = Converter.convertStringToPolynomial("4x^5-3x^4+x^2-8x+1");
//        Polynomial secondPolynomial = Converter.convertStringToPolynomial("3x^4-x^3+x^2+2x-1");
        Polynomial firstPolynomial = Converter.convertStringToPolynomial("5x^3-x+75");
        Polynomial secondPolynomial = Converter.convertStringToPolynomial("x^2+1");
        Polynomial result = operations.subtract(firstPolynomial, secondPolynomial);
        result.sort(new Polynomial.OrderMonomials());
//        System.out.println(result + " ");
        assertTrue(String.valueOf(result).equals("5x^3-x^2-x+74.0"));
//        assertTrue(String.valueOf(result).equals("4x^5-6x^4-x^3-10x+2.0"),
//                "The result of subtracting from 4x^5-3x^4+x^2-8x+1, 3x^4-x^3+x^2+2x-1 is 4x^5-6x^4+x^3-10x+2");
    }
}
