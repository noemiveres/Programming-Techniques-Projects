package ro.tuc.pt.logic;

import ro.tuc.pt.model.Monomial;
import ro.tuc.pt.model.Polynomial;

public class Operations {

    public Polynomial add(Polynomial firstPolynomial, Polynomial secondPolynomial) {
        for (Monomial mono2 : secondPolynomial) {
            boolean added = false;
            for (Monomial mono1 : firstPolynomial) {
                if (mono1.getPower() == mono2.getPower()) {
                    Number newCoeff = mono1.getCoefficient().intValue() + mono2.getCoefficient().intValue();
                        mono1.setCoefficient(newCoeff);
                    added = true;
                    break;
                }
            }
            if(!added){
                firstPolynomial.add(mono2);
            }
        }
        return firstPolynomial;
    }

    public Polynomial subtract(Polynomial firstPolynomial, Polynomial secondPolynomial) {
        for (Monomial mono2 : secondPolynomial) {
            boolean added = false;
            for (Monomial mono1 : firstPolynomial) {
                if (mono1.getPower() == mono2.getPower()) {
                    mono1.setCoefficient(mono1.getCoefficient().intValue() - mono2.getCoefficient().intValue());
                    added = true;
                    break;
                }
            }
            if(!added){
                mono2.setCoefficient(-mono2.getCoefficient().floatValue());
                firstPolynomial.add(mono2);
            }
        }
        return firstPolynomial;
    }

    public Polynomial multiply(Polynomial firstPolynomial, Polynomial secondPolynomial) {
        Monomial mono1 = firstPolynomial.get(0);
        Polynomial res1 = new Polynomial();
        for (Monomial mono2 : secondPolynomial){
            res1.add(new Monomial(mono1.getCoefficient().intValue()* mono2.getCoefficient().intValue(),
                    mono1.getPower()+ mono2.getPower()));
        }
        int ind = 1;
        int size = firstPolynomial.size();
        while (ind < size){
            mono1 = firstPolynomial.get(ind);
            Polynomial res2 = new Polynomial();
            for (Monomial mono2 : secondPolynomial){
                res2.add(new Monomial(mono1.getCoefficient().intValue()* mono2.getCoefficient().intValue(),
                        mono1.getPower()+ mono2.getPower()));
            }
            res1 = add(res1,res2);
            ind++;
        }
        return res1;
    }

    public Polynomial divide(Polynomial firstPolynomial, Polynomial secondPolynomial) {
        Polynomial dividend, divisor;
        Polynomial quotient = new Polynomial();
        if (firstPolynomial.get(0).getPower() > secondPolynomial.get(0).getPower()){
            dividend = firstPolynomial;
            divisor = secondPolynomial;
        } else {
            dividend = secondPolynomial;
            divisor = firstPolynomial;
        }
        //while(dividend.get(0).getPower() >= divisor.get(0).getPower()){
            for (Monomial mono1 : dividend){
                Monomial q = new Monomial(mono1.getCoefficient().floatValue()/secondPolynomial.get(0).getCoefficient().floatValue(),
                        mono1.getPower()-secondPolynomial.get(0).getPower());
                quotient.add(q);
                Polynomial multi = new Polynomial();
                multi.add(q);
                multi = multiply(multi,divisor);
                Polynomial remainder = subtract(dividend,multi);
                dividend = remainder;
            }
        //}
        return quotient;
    }

    public Polynomial derivative(Polynomial polynomial) {
        for (Monomial mono : polynomial){
            Number newCoeff = mono.getCoefficient().intValue() * mono.getPower();
            mono.setCoefficient(newCoeff);
            if(mono.getPower()!=0) {
                int newPow = mono.getPower() - 1;
                mono.setPower(newPow);
            }
        }
        return polynomial;
    }

    public Polynomial integral(Polynomial polynomial) {
        for (Monomial mono : polynomial){
            if(mono.getPower()!=0){
                Number newCoeff = mono.getCoefficient().floatValue() / (mono.getPower()+1);
                mono.setCoefficient(newCoeff);
            }
            int newPow = mono.getPower() + 1;
            mono.setPower(newPow);
        }
        return polynomial;
    }

}
