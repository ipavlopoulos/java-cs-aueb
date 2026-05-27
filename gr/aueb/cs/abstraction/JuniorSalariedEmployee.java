package gr.aueb.cs.abstraction;

abstract public class JuniorSalariedEmployee extends SalariedEmployee {

    public JuniorSalariedEmployee(String firstName, String lastName) {
        super(firstName, lastName, "", 2.0);
    }

    public abstract void beingAbsstract();


}
