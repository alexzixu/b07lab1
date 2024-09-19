import java.util.Arrays;

public class Polynomial {
    double[] coefficients;

    public Polynomial() {
        this.coefficients = new double[]{0};
    }

    public Polynomial(double[] coefficients) {
    	this.coefficients = coefficients;
    }

    public Polynomial add(Polynomial other) {
        int len1 = this.coefficients.length;
        int len2 = other.coefficients.length;
        int resultLength = Math.max(len1, len2);
        double[] resultPolynomial = new double[resultLength];

        for (int i = 0; i < len1; i++) {
            resultPolynomial[i] += this.coefficients[i];
        }

        for (int i = 0; i < len2; i++) {
            resultPolynomial[i] += other.coefficients[i];
        }

        return new Polynomial(resultPolynomial);
    }

    public double evaluate(double x) {
        double result = 0;
        for (int i = 0; i < this.coefficients.length; i++) {
        	
        	double x_part = 1;
        	for (int j = 0; j < i; j++) {
        		x_part*=x;
        	}
        	
            result += x_part * coefficients[i];
        }
        return result;
    }

    public boolean hasRoot(double x) {
        return evaluate(x) == 0;
    }


}
