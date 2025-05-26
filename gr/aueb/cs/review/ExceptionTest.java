package gr.aueb.cs.review;

class ExceptionTest {
    static int a;
    static int b;
    public static void TestMethod(int a, int b) throws Exception{
        try {
            System.out.println(a/b);
        }
        catch (ArithmeticException e){
            System.out.println("Error in TestMethod:ArithmeticException");
        }
        finally {
            System.out.println("Finally in TestMethod");
        }
    };
    public static void main(String[] args) throws Exception{
        TestMethod(a,b);
    }
}