public class Hand {
    public double x;
    public double y;
    public boolean[] fingers;

    public Hand(){
        fingers = new boolean[5];
    }

    public void setX(double newX){
        x  = newX;
    }
}
