import java.io.File;

public class Driver {
  public static void main(String [] args) {
    Polynomial p = new Polynomial();
    System.out.println(p.evaluate(3));
    double [] c1Coeff = {6, 5};
    int[] c1Exp = {0, 3};
    Polynomial p1 = new Polynomial(c1Coeff, c1Exp);
    p1.printPoly();
    double [] c2Coeff = {-2,-9};
    int[] c2Exp = {1, 4};
    Polynomial p2 = new Polynomial(c2Coeff, c2Exp);
    p2.printPoly();
    Polynomial s = p1.add(p2);
    s.printPoly();
    System.out.println("s(0.1) = " + s.evaluate(0.1));
    if(s.hasRoot(1))
      System.out.println("1 is a root of s");
    else
      System.out.println("1 is not a root of s");

    System.out.println("////////////////////////");
    System.out.println("////////////////////////");
    System.out.println("////////////////////////");
    System.out.println("////////////////////////");
    System.out.println("////////////////////////");
    System.out.println("");
    System.out.println("Testing multiplication");

    double [] q1Coeff = {1, -3};
    int[] q1Exp = {1, 0};
    double [] q2Coeff = {1, 3};
    int[] q2Exp = {1, 0};
    
    Polynomial q1 = new Polynomial(q1Coeff, q1Exp);
    Polynomial q2 = new Polynomial(q2Coeff, q2Exp);
    Polynomial res1 = q1.multiply(q2);
    Polynomial res2 = q2.multiply(q1);

    System.out.print("(");
    q1.printPoly();
    System.out.print(")(");
    q2.printPoly();
    System.out.print(") = ");
    res1.printPoly();
    System.out.print(" WHICH SHOULD EQUAL TO ");
    res2.printPoly();

    res2.saveToFile("output.txt");

    System.out.println("////////////////////////");
    System.out.println("////////////////////////");
    System.out.println("////////////////////////");
    System.out.println("////////////////////////");

    System.out.println("TESTING READING FROM FILE");

    double [] q3Coeff = {1, -4, 1};
    int[] q3Exp = {3, 1, 0};
    double [] q4Coeff = {-7, 11};
    int[] q4Exp = {4, 1};
    
    Polynomial q3 = new Polynomial(q3Coeff, q3Exp);
    Polynomial q4 = new Polynomial(q4Coeff, q4Exp);
    Polynomial res3 = q3.multiply(q4);

    System.out.print("(");
    q3.printPoly();
    System.out.print(")(");
    q4.printPoly();
    System.out.print(") = ");
    res3.printPoly();

    res3.saveToFile("output.txt");

    Polynomial p111 = new Polynomial(new File("output.txt"));

    System.out.println("////////////////////////");
    System.out.println("////////////////////////");
    System.out.println("////////////////////////");

    p111.printPoly();
  } 

}
