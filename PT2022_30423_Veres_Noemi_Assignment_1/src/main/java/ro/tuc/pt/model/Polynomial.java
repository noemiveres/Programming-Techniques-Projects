package ro.tuc.pt.model;

import ro.tuc.pt.utils.Converter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.IntFunction;

public class Polynomial extends ArrayList<Monomial> {
    private ArrayList<Monomial> monomials;

    public Polynomial(){
        monomials = new ArrayList<Monomial>();
    }

//    public Polynomial(String input){
//        monomials = Converter.convertStringToPolynomial(input);
//    }

    public static class OrderMonomials implements Comparator<Monomial>{
        @Override
        public int compare(Monomial mono1, Monomial mono2){
            return Integer.compare(mono2.getPower(), mono1.getPower());
        }
    }

    public ArrayList<Monomial> getMonomials() {
        return monomials;
    }

    public void setMonomials(ArrayList<Monomial> monomials) {
        this.monomials = monomials;
    }

    public String toString(){
        String stringPolynomial = "";
        for(Monomial mono : this){  // monomials
                if(mono.getCoefficient().doubleValue() > 0){
                    stringPolynomial += "+";
                }
            stringPolynomial += mono.toString();
        }
        if (stringPolynomial.charAt(0) == '+'){
            stringPolynomial = stringPolynomial.substring(1); // delete + from the beginning
        }
        return stringPolynomial;
    }

    @Override
    public <T> T[] toArray(IntFunction<T[]> generator) {
        return super.toArray(generator);
    }
}
