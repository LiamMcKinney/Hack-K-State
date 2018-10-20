import java.util.ArrayList;

class Hand {
    private double x;
    private double y;
    private static boolean[] fingers;

    Hand(){
        fingers = new boolean[3];
    }

    /**
     * Iterates through the fingers array, and returns a list of which
     * @return list of fingers down
     */
    static ArrayList<Integer> getFingersDown() {
        ArrayList<Integer> fingersDown = new ArrayList<>();
        for (int i = 0; i < fingers.length; i++) {
            if(fingers[i]) {
                fingersDown.add(i);
            }
        }
        return fingersDown;
    }

    double getX() {
        return x;
    }

    double getY() {
        return y;
    }

    void setX(int x) { this.x = x;}

    void setY(int y) { this.y = y;}
}


