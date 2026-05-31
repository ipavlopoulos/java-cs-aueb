package gr.aueb.cs.examples.introduction;

class Human
{
    boolean alive;
    int age=0;
    protected String name;


    public void setName(String name){
        if (this.name == null) {
            this.name = name;
        }else{
            System.out.println("Already set");
        }
    }

    private void born()
    {
        alive = true;
    }

    public void speak()
    {
        System.out.println("Hello world!");
    }

    void incrementAge()
    {
        age += 1;
    }

    public static void main(String[] args){
        char c='B';
        System.out.println(c+1);
        Human h = new Human(); // new object
        h.setName("John");
        h.setName("Nobody");
        h.name = "";
        System.out.println(h.name); // print a field
        h.speak();
    }
}

class SuperHuman extends Human{

    public static void main(String[] args){
        SuperHuman h = new SuperHuman();
        System.out.println(h.age);
        System.out.println(h.name);
    }
}
