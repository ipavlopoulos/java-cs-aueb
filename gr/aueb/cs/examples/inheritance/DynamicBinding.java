package gr.aueb.cs.examples.inheritance;

class Sup {
    void who() { System.out.println("who() in Sup"); }
}
class Sub1 extends Sup {
    void who() { System.out.println("who() in Sub1"); }
}
class Sub2 extends Sup {
    void who() { System.out.println("who() in Sub2"); }
}


public class DynamicBinding {
    public static void main(String args[]) {
        Sup superOb = new Sup();
        Sub1 subOb1 = new Sub1();
        Sub2 subOb2 = new Sub2();
        Sup supRef;
        int a;
        supRef = superOb;
        supRef.who();
        supRef = subOb1;
        supRef.who();
        a = 1;
        supRef = (a == 1 ? subOb1 : subOb2);
        supRef.who();
    }
}
