import java.util.ArrayList;

public class Hand {
    public double x;
    public double y;
    public boolean[] fingers;



    public Hand(){
        fingers = new boolean[3];
    }

    /**
     * Iterates through the fingers array, and returns a list of which
     * @return list of fingers down
     */
    public ArrayList<Integer> getFingersDown() {
        ArrayList<Integer> fingersDown = new ArrayList<Integer>();
        for (int i = 0; i < fingers.length; i++) {
            if(fingers[i]) {
                fingersDown.add(i);
            }
        }
        return fingersDown;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(int x) { this.x = x;}

    public void setY(int y) { this.y = y;}
}


