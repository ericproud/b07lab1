public class Polynomial {
  private double[] coefficients;

  public Polynomial() {
      this.coefficients = new double[1];
  }

  public Polynomial(double[] coefficients) {
      this.coefficients = coefficients;
  }

  public Polynomial add(Polynomial otherPolynomial) {
    int maxLength = Math.max(this.coefficients.length, otherPolynomial.coefficients.length);
    double[] resultCoefficients = new double[maxLength];

    for (int i = 0; i < maxLength; i++) {
      if (i < this.coefficients.length) {
        resultCoefficients[i] += this.coefficients[i];
      }
      if (i < otherPolynomial.coefficients.length) {
        resultCoefficients[i] += otherPolynomial.coefficients[i];
      }
    }
    return new Polynomial(resultCoefficients);
  }

  public double evaluate(double x) {
    double result = 0;
    for (int i = 0; i < coefficients.length; i++) {
      result += coefficients[i] * Math.pow(x, i);
    }
    return result;
  }

  public boolean hasRoot(double x) {
    return this.evaluate(x) == 0;
  }
}