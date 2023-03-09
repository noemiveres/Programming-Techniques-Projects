package ro.tuc.pt.model;

public class Monomial {
    private Number coefficient;
    private int power;

    public Monomial(Number coefficient, int power) {
        this.coefficient = coefficient;
        this.power = power;
    }

    public Number getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(Number coefficient) {
        this.coefficient = coefficient;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public String toString(){
        String monomial;
        if(coefficient.floatValue()==0){
            monomial = "";
        } else if(power == 0){
            monomial = coefficient.floatValue() + "";
        }
        else if(power==1){
            if(coefficient.floatValue()==-1){
                monomial = "-x";
            } else if(coefficient.floatValue()==1){
                monomial = "x";
            } else{
                monomial = coefficient +"x";
            }
        } else{
            if(coefficient.floatValue()==-1){
                monomial = "-x^"+String.valueOf(power);
            } else if(coefficient.floatValue()==1){
                monomial = "x^"+String.valueOf(power);
            } else{
                monomial = String.valueOf(coefficient)+"x^"+String.valueOf(power);
            }
        }
        return monomial;
    }
}
