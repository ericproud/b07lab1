import java.util.HashMap;
import java.util.Map;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Polynomial {
  private double[] nonZeroCoefficients;
  private int[] exponents;

  public Polynomial() {
      this.nonZeroCoefficients = new double[1];
      this.exponents = new int[1];
  }

  public Polynomial(double[] nonZeroCoefficients, int[] exponents) {
      this.nonZeroCoefficients = nonZeroCoefficients;
      this.exponents = exponents;
  }

  public Polynomial(File file) {
    String polyStr = "";
    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
      polyStr = reader.readLine();
    } catch (IOException error) {
        System.err.println("Error reading from file");
    }
    
    String regex = "[+-]";
    String[] terms = polyStr.split(regex);

    double[] newCoefficients = new double[terms.length];
    int[] newExponents = new int[terms.length];

    regex = "[x]";
    for (int i = 0; i < terms.length; i++) {
      String[] coefficientAndExponent = terms[i].split(regex);

      newCoefficients[i] = Double.parseDouble(coefficientAndExponent[0]);

      if (coefficientAndExponent.length == 1) {
        newExponents[i] = 1;
      }
      else {
        newExponents[i] = Integer.parseInt(coefficientAndExponent[1]);
      }
    }
    this.nonZeroCoefficients = newCoefficients;
    this.exponents = newExponents;
  }

  public Polynomial add(Polynomial otherPolynomial) {
    HashMap<Integer, Double> coefficientsAndExponents = new HashMap<Integer, Double>();
    
    coefficientsAndExponents = populateCoefficientsAndExponents(coefficientsAndExponents);
    coefficientsAndExponents = populateCoefficientsAndExponents(coefficientsAndExponents, otherPolynomial);

    double[] newCoefficients = makeCoefficientsArray(coefficientsAndExponents);
    int[] newExponents = makeExponentsArray(coefficientsAndExponents);

    return new Polynomial(newCoefficients, newExponents);
  }

  public double evaluate(double x) {
    double result = 0;
    for (int i = 0; i < this.nonZeroCoefficients.length; i++) {
      result += this.nonZeroCoefficients[i] * Math.pow(x, exponents[i]);
    }
    return result;
  }

  public boolean hasRoot(double x) {
    double result = this.evaluate(x);
    return (result < 0.000001) && (result > -0.000001);
  }

  public Polynomial multiply(Polynomial otherPolynomial) {
    HashMap<Integer, Double> coefficientsAndExponents = new HashMap<Integer, Double>();

    for (int i = 0; i < this.exponents.length; i++) {
      for (int j = 0; j < otherPolynomial.exponents.length; j++) {
        int resultExponent = this.exponents[i] + otherPolynomial.exponents[j];
        double resultCoefficient = this.nonZeroCoefficients[i] * otherPolynomial.nonZeroCoefficients[j];

        coefficientsAndExponents = populateCoefficientsAndExponents(coefficientsAndExponents, resultExponent, resultCoefficient);
      }
    }
    double[] newCoefficients = makeCoefficientsArray(coefficientsAndExponents);
    int[] newExponents = makeExponentsArray(coefficientsAndExponents);

    return new Polynomial(newCoefficients, newExponents);
  }

  public void saveToFile(String filePath) {
    String polyStr = "";
    for (int i = 0; i < this.exponents.length; i++) {
      if (this.nonZeroCoefficients[i] >= 0 && i != 0) {
        polyStr += "+";
      }
      polyStr += this.nonZeroCoefficients[i];
      if (this.exponents[i] != 0) {
        polyStr += "x";
      }
      if (this.exponents[i] != 1 && this.exponents[i] != 0) {
        polyStr += Integer.toString(this.exponents[i]);
      }
    }

    try (FileWriter writer = new FileWriter(filePath)) {
        writer.write(polyStr);
        writer.close();
    } catch (IOException error) {
        System.err.println("Error saving polynomial to file: " + error.getMessage());
    }
  }

  //HELPER FUNCTIONS
  private HashMap<Integer, Double> populateCoefficientsAndExponents(HashMap<Integer, Double> coefficientsAndExponents, Polynomial otherPolynomial) {
    for (int i = 0; i < otherPolynomial.exponents.length; i++) {
      Double doubleCoefficient = Double.valueOf(otherPolynomial.nonZeroCoefficients[i]);
      Integer integerExponent = Integer.valueOf(otherPolynomial.exponents[i]);

      if (coefficientsAndExponents.get(otherPolynomial.exponents[i]) == null) {
        coefficientsAndExponents.put(integerExponent, doubleCoefficient);
      }
      else {
        coefficientsAndExponents.compute(integerExponent, (key, value) -> value + doubleCoefficient);
      }
    }
    return coefficientsAndExponents;
  }

  private HashMap<Integer, Double> populateCoefficientsAndExponents(HashMap<Integer, Double> coefficientsAndExponents) {
    for (int i = 0; i < this.exponents.length; i++) {
      Double doubleCoefficient = Double.valueOf(this.nonZeroCoefficients[i]);
      Integer integerExponent = Integer.valueOf(this.exponents[i]);

      if (coefficientsAndExponents.get(this.exponents[i]) == null) {
        coefficientsAndExponents.put(integerExponent, doubleCoefficient);
      }
      else {
        coefficientsAndExponents.compute(integerExponent, (key, value) -> value + doubleCoefficient);
      }
    }
    return coefficientsAndExponents;
  }

  private HashMap<Integer, Double> populateCoefficientsAndExponents(HashMap<Integer, Double> coefficientsAndExponents, int exponent, double coefficient) {
    Double doubleCoefficient = Double.valueOf(coefficient);
    Integer integerExponent = Integer.valueOf(exponent);
      
    if (coefficientsAndExponents.get(integerExponent) == null) {
      coefficientsAndExponents.put(integerExponent, doubleCoefficient);
    }
    else {
      coefficientsAndExponents.merge(integerExponent, doubleCoefficient, Double::sum);
    }
    return coefficientsAndExponents;
  }

  private double[] makeCoefficientsArray(HashMap<Integer, Double> coefficientsAndExponents) {
    int nonZeroCount = 0;
    for (double coeff : coefficientsAndExponents.values()) {
      if (Math.abs(coeff) > 0.000001) {
        nonZeroCount++;
      }
    }
    double[] newCoefficients = new double[nonZeroCount];
    
    int i = 0;
    for (Map.Entry<Integer, Double> coefficientAndExponent: coefficientsAndExponents.entrySet()) {
      if (coefficientAndExponent.getValue().intValue() != 0) {
        newCoefficients[i] = coefficientAndExponent.getValue().doubleValue();
        i++;
      }
    }
    return newCoefficients;
  }

  private int[] makeExponentsArray(HashMap<Integer, Double> coefficientsAndExponents) {
    int nonZeroCount = 0;
    for (double coeff : coefficientsAndExponents.values()) {
      if (Math.abs(coeff) > 0.000001) {
        nonZeroCount++;
      }
    }
    int[] newExponents = new int[nonZeroCount];

    int i = 0;
    for (Map.Entry<Integer, Double> coefficientAndExponent: coefficientsAndExponents.entrySet()) {
      if (coefficientAndExponent.getValue().intValue() != 0) {
        newExponents[i] = coefficientAndExponent.getKey().intValue();
        i++;
      }
    }
    return newExponents;
  }

  public void printPoly() {
    String polyStr = "";
    for (int i = 0; i < this.exponents.length; i++) {
      if (this.nonZeroCoefficients[i] >= 0 && i != 0 ) {
        polyStr += "+";
      }
      polyStr += this.nonZeroCoefficients[i];
      if (this.exponents[i] != 0) {
        polyStr += "x";
      }
      if (this.exponents[i] != 1 && this.exponents[i] != 0) {
        polyStr += Integer.toString(this.exponents[i]);
      }
    }
    System.out.print(polyStr);
  }
}