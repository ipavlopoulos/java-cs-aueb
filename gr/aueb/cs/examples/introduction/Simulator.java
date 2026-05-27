package gr.aueb.cs.examples.introduction;

public class Simulator {
    protected static float time;
    protected int x;

    public static void main(String [] args){

        Simulator.time=10;
        System.out.println("Simulator.time:" + Simulator.time);
        Simulator ob1=new Simulator();
        Simulator ob2=new Simulator();

        ob1.x=10;
        ob2.x=20;
        System.out.println("ob1.x and ob2.x are independent, ob1.x: " + ob1.x + ", ob2.x: " + ob2.x);

        ob1.time=66; ob2.time=99;
        System.out.println("static variable time can be accessed through class name, Simulator.time: " + Simulator.time + ", ob1.time: " + ob1.time + ", ob2.time:" + ob2.time);
    }

}
