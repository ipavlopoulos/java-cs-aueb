package gr.aueb.cs.examples.inheritance;

public class Final {
    public static void main(String[] args) {
        A a = new A();
        a.meth();
        A b = new B();
        b.meth();
    }
}

class A {
    final void meth() {
        System.out.println("This is a final method.");
    }

    void meth(String a){
        System.out.println("This is a final method overloaded.");
    }
}
class B extends A {

    @Override
    void meth(String a){
        System.out.println("This is a final method.");
    }
}