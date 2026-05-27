
package gr.aueb.cs.examples.abstraction;

public abstract class Employee {
    private String firstName;
    private String lastName;
    private String socialSecurityNumber;

    // three-argument constructor
    public Employee( String first, String last, String ssn ) {
        firstName = first;
        lastName = last;
        socialSecurityNumber = ssn;
    }

    public void setFirstName( String first ) {
        firstName = first; }

    public String getFirstName() {
        return firstName; }

    public String getLastName() {
        return lastName;
    }

    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    public String toString() {
        return String.format( "%s %s\nsocial security number: %s",
                getFirstName(), getLastName(), getSocialSecurityNumber() );
    } // end method toString

    // abstract method overridden by subclasses
    public abstract double earnings(); // no implementation here


}