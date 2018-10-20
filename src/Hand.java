import java.util.ArrayList;

public class Hand {
<<<<<<< HEAD
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
=======
>>>>>>> 6d21b80e2cdec25618c39d166d40ecaaad5d9590
}
