package gr.aueb.cs.review;

    class A{
        public void method() {
            System.out.println("A");
        }
    }

    class B extends A {
        public void method() {
            System.out.println("B");
        }
    }

class DynamicDispatchTest {

    public static void main(String[] args){
        A a = new B();
        a.method();
        ((A)a).method();
        ((B)a).method();
    }
}


