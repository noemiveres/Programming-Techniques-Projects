package ro.tuc.pt.utils;

import ro.tuc.pt.model.Monomial;
import ro.tuc.pt.model.Polynomial;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Converter {
    public static Number convertCoefficient(Matcher matcher) {
        String x_PATTERN_PLUS = "[+]?x";
        String x_PATTERN_MINUS = "-x";
        String COEFFICIENT_WITH_x_PATTERN = "[+-]?\\d+x";
        Pattern patternCoeff = Pattern.compile(COEFFICIENT_WITH_x_PATTERN);
        Matcher matcherCoeff = patternCoeff.matcher(matcher.group(0));
        if (matcherCoeff.find()) {    // cx
            //System.out.println(matcherCoeff.group(0).substring(0, matcherCoeff.group(0).length() - 1));
            return Integer.valueOf(matcherCoeff.group(0).substring(0, matcherCoeff.group(0).length() - 1));
        } else {
            Pattern patternCoeff1 = Pattern.compile(x_PATTERN_MINUS);
            Matcher matcherCoeff1 = patternCoeff1.matcher(matcher.group(0));
            if (matcherCoeff1.find()) {    // -x
                //System.out.println("-1");
                return -1;
            } else {
                patternCoeff1 = Pattern.compile(x_PATTERN_PLUS);
                matcherCoeff1 = patternCoeff1.matcher(matcher.group(0));
                if (matcherCoeff1.find()){  // +x
                    //System.out.println("1");
                    return 1;
                } else {
                    //System.out.println(matcher.group(0));
                    return Integer.valueOf(matcher.group(0));
                }
            }
        }
    }

    public static int convertPower(Matcher matcher) {
        String EXPONENTIAL_PATTERN = "\\^\\d+";
        String x_PATTERN = "x";
        Pattern patternExp = Pattern.compile(EXPONENTIAL_PATTERN);
        Matcher matcherExp = patternExp.matcher(matcher.group(0));
        if (matcherExp.find()) {        // degree > 1
            //System.out.println(matcherExp.group(0).substring(1));
            return Integer.parseInt(matcherExp.group(0).substring(1));
        } else {
            Pattern patternExp1 = Pattern.compile(x_PATTERN);
            Matcher matcherExp1 = patternExp1.matcher(matcher.group(0));
            if (matcherExp1.find()) {    // degree = 1
                //System.out.println("1");
                return 1;
            } else {                 // degree = 0
                //System.out.println("0");
                return 0;
            }
        }
    }

    public static Polynomial convertStringToPolynomial(String polynomialString) {
        Polynomial newPolynomial = new Polynomial();
        String POLYNOMIAL_PATTERN = "^([+-]?\\d*?x\\^\\d+[+-]??|[+-]?\\d*x|[+-]?\\d*)+";
        String MONOMIAL_PATTERN = "[+-]?\\d*x\\^\\d+[+-]??|[+-]?\\d*x|[+-]?\\d+";
        Pattern pattern = Pattern.compile(POLYNOMIAL_PATTERN);
        Matcher matcher = pattern.matcher(polynomialString);
        if (matcher.matches()) {
//            System.out.println("The polynomial is valid \n" + matcher.group());
            Pattern patternMono = Pattern.compile(MONOMIAL_PATTERN);
            matcher = patternMono.matcher(polynomialString);
            while (matcher.find()) {
//                System.out.println(matcher.group(0));
                Number coeff = convertCoefficient(matcher);
                int power = convertPower(matcher);
                Monomial newMonomial = new Monomial(coeff, power);
                newPolynomial.add(newMonomial);
            }
        } else {
//            System.out.println("The polynomial is not valid");
            return new Polynomial();
        }
        newPolynomial.sort(new Polynomial.OrderMonomials());
        return newPolynomial;
    }

}
