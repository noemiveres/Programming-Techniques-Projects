package ro.tuc.pt;

import org.junit.jupiter.api.Test;
import ro.tuc.pt.logic.Operations;
import ro.tuc.pt.model.Polynomial;
import ro.tuc.pt.utils.Converter;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AddOperationTest {
    @Test
    public void addTest() {
        Operations operations = new Operations();
//        Polynomial firstPolynomial = Converter.convertStringToPolynomial("4x^5-3x^4+x^2-8x+1");
//        Polynomial secondPolynomial = Converter.convertStringToPolynomial("3x^4-x^3+x^2+2x-1");
        Polynomial firstPolynomial = Converter.convertStringToPolynomial("5x^3-x+73");
        Polynomial secondPolynomial = Converter.convertStringToPolynomial("x^2+1");
        Polynomial result = operations.add(firstPolynomial, secondPolynomial);
        result.sort(new Polynomial.OrderMonomials());
        //System.out.println(result + " ");
        assertTrue(String.valueOf(result).equals("5x^3+x^2-x+74.0"));
//        assertTrue(String.valueOf(result).equals("4x^5-x^3+2x^2-6x"),
//                "The result of adding 4x^5-3x^4+x^2-8x+1 and 3x^4-x^3+x^2+2x-1 is 4x^5-x^3+2x^2-6x");
    }
}
