package gr.aueb.cs.examples.interfaces;

interface Testable {
    void test();

    static void testAgain(){
        System.out.println("Testing again");
    }
}

class A implements Testable {
    void show() {
        System.out.println("In A");
    }

    public void test() {
        this.show();
    }
}

class B extends A {
}

public class Demo {

        public static void main(String [] args)
        {
            A [] a = new A[2];
            a[0] = new A();
            a[1] = new B();
            int i;
            System.out.println(A.class);
            for (i=0; i<2; i++){
                if (a[i].getClass()== A.class)
                    ((Testable)a[i]).test();
            }
            for (i=0; i<2; i++){
                if (a[i] instanceof Testable) {
                    ((Testable) a[i]).test();
                }
            }
    }

}
