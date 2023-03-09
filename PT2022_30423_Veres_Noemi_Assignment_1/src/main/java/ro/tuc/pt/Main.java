package ro.tuc.pt;

import ro.tuc.pt.gui.View;
import ro.tuc.pt.model.Monomial;
import ro.tuc.pt.model.Polynomial;
import ro.tuc.pt.utils.Converter;

import javax.swing.*;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {

        JFrame frame = new View("Simple polynomial calculator using MVC");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.pack();
        frame.setVisible(true);
//        Polynomial mypoly = Converter.convertStringToPolynomial("30x^7-4x^13+2x^6+3x-7+x^3+x^7");
//        String mystring = String.valueOf(mypoly);
//        System.out.println(mystring);
    }
}
