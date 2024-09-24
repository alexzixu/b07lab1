import java.io.*;
import java.util.*;

public class Driver {
	public static void main(String [] args) {
		try {
            
            double[] coefficients1 = {5, -3, 7};
            int[] exponents1 = {0, 2, 3};
            Polynomial poly1 = new Polynomial(coefficients1, exponents1);

            File inputFile = new File("inputPolynomial.txt"); 
            Polynomial poly2 = new Polynomial(inputFile);


            Polynomial sum = poly1.add(poly2);

            Polynomial product = poly1.multiply(poly2);

            double xValue = 2;
            double evaluation = poly1.evaluate(xValue);
            System.out.println("Evaluation of Polynomial 1 at x = " + xValue + ": " + evaluation);

            
            boolean hasRoot = poly2.hasRoot(-2);
            System.out.println("Does Polynomial 2 have a root at x = -2? " + hasRoot);

            
            sum.saveToFile("outputSum.txt");
            System.out.println("Sum polynomial saved to outputPolynomial.txt");

			product.saveToFile("outputProduct.txt");
			System.out.println("Product polynomial saved to outputPolynomial.txt");

        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
	}
}