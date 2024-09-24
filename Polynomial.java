import java.io.*;
import java.util.*;

public class Polynomial {
    double[] coefficients;
    int[] exponents;

    public Polynomial() {
        this.coefficients = new double[]{0};
        this.exponents = new int[]{0};
    }

    public Polynomial(double[] coefficients, int[] exponents) {
        this.coefficients = coefficients;
        this.exponents = exponents;
    }

    public Polynomial(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String polynomialStr = reader.readLine();
        reader.close();
        
        ArrayList<Double> coefList = new ArrayList<>();
        ArrayList<Integer> expList = new ArrayList<>();

        String[] terms = polynomialStr.split("(?=[+-])");
        for (String term : terms) {
            if (term.contains("x")) {
                
                String[] parts = term.split("x");

                double coef;
                if (parts[0].isEmpty() || parts[0].equals("+")){
                    coef = 1;
                }
                else if (parts[0].equals("-")){
                    coef = -1;
                }
                else{
                    coef = Double.parseDouble(parts[0]);
                }
                

                int exp;
                if (parts.length == 1){
                    exp = 1;
                }
                else{
                    exp = Integer.parseInt(parts[1]);
                }

                coefList.add(coef);
                expList.add(exp);

            } else {
                
                coefList.add(Double.parseDouble(term));
                expList.add(0);
            }
        }

        double[] finalCoefficients = new double[coefList.size()];
        int[] finalExponents = new int[expList.size()];

        for (int i = 0; i < coefList.size(); i++){
            finalCoefficients[i] = coefList.get(i);
        }
        for (int i = 0; i < expList.size(); i++){
            finalExponents[i] = expList.get(i);
        }

        this.coefficients = finalCoefficients;
        this.exponents = finalExponents;

    }

    public Polynomial add(Polynomial other) {
        Set<Integer> uniqueExponents = new HashSet<>();

        for (int exp : this.exponents) {
            uniqueExponents.add(exp);
        }
        for (int exp : other.exponents) {
            uniqueExponents.add(exp);
        }

        List<Double> resultCoefficients = new ArrayList<>();
        List<Integer> resultExponents = new ArrayList<>();


        for (Integer exp : uniqueExponents) {
            double coef = 0;

            for (int i = 0; i < this.exponents.length; i++) {
                if (this.exponents[i] == exp) {
                    coef += this.coefficients[i];
                }
            }

            for (int i = 0; i < other.exponents.length; i++) {
                if (other.exponents[i] == exp) {
                    coef += other.coefficients[i];
                }
            }

            if (coef != 0) {
                resultCoefficients.add(coef);
                resultExponents.add(exp);
            }
        }
        
        double[] finalCoefficients = new double[resultCoefficients.size()];
        int[] finalExponents = new int[resultExponents.size()];

        for (int i = 0; i < resultCoefficients.size(); i++){
            finalCoefficients[i] = resultCoefficients.get(i);
        }
        for (int i = 0; i < resultExponents.size(); i++){
            finalExponents[i] = resultExponents.get(i);
        }


        return new Polynomial(finalCoefficients,finalExponents);
    }
    
    public Polynomial multiply(Polynomial other) {
        Set<Integer> uniqueExponents = new HashSet<>();
        List<Double> resultCoefficients = new ArrayList<>();
        List<Integer> resultExponents = new ArrayList<>();

        for (int i = 0; i < this.coefficients.length; i++) {
            for (int j = 0; j < other.coefficients.length; j++) {
                int newExp = this.exponents[i] + other.exponents[j];
                double newCoef = this.coefficients[i] * other.coefficients[j];


                if (uniqueExponents.contains(newExp)) {
                    for (int k = 0; k < resultExponents.size(); k++) {
                        if (resultExponents.get(k) == newExp) {
                            resultCoefficients.set(k, resultCoefficients.get(k) + newCoef);
                            break;
                        }
                    }
                } else {
                    resultCoefficients.add(newCoef);
                    resultExponents.add(newExp);
                    uniqueExponents.add(newExp);
                }
            }
        }

        double[] finalCoefficients = new double[resultCoefficients.size()];
        int[] finalExponents = new int[resultExponents.size()];

        for (int i = 0; i < resultCoefficients.size(); i++){
            finalCoefficients[i] = resultCoefficients.get(i);
        }
        for (int i = 0; i < resultExponents.size(); i++){
            finalExponents[i] = resultExponents.get(i);
        }

        return new Polynomial(finalCoefficients,finalExponents);
    }

    public double evaluate(double x) {
        double result = 0;
        for (int i = 0; i < this.coefficients.length; i++) {
            result += this.coefficients[i] * Math.pow(x, this.exponents[i]);
        }
        return result;
    }

    public boolean hasRoot(double x) {
        return evaluate(x) == 0;
    }

    public void saveToFile(String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            String polynomialStr = ""; 

            for (int i = 0; i < coefficients.length; i++) {
                double coef = coefficients[i];
                int exp = exponents[i];

                if (i == 0) {
                    polynomialStr += coef;
                } else {
                    if (coef < 0) {
                        polynomialStr += coef; 
                    } else {
                        polynomialStr += "+" + coef;
                    }
                }

                
                if (exp > 0) {
                    polynomialStr += "x";
                    if (exp > 1) {
                        polynomialStr += exp;
                    }
                }
            }
            writer.write(polynomialStr);
        }
    }

}