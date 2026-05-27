package gr.aueb.cs.examples.introduction;

public class BreakAndContinue {

    public int x, y;

    private void change(){this.x = this.y=5;}

    public BreakAndContinue(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void setY(int y){
        this.y=y;
    }
    public int getY(){return this.y;}
    public void setX(int x){
        this.x=x;
        this.change();
    }
    public int getX(){return this.x;}

    public static void script(){
        BreakAndContinue b = new BreakAndContinue(0,0);
        b.change();
        b.x = -10;
        System.out.println(b.y);
    }

    public static void main(String[] args){
            for(int i = 0; i < 100; i++) {
                if(i == 74) {
                    break;
                }
                // Out of for loop
                if(i % 9 != 0) {
                    System.out.println("In here");
                    continue;
                }
                // Next iteration
                System.out.println(i);
            }
        }
    }