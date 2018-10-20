

public class Main {

    private static Hand leftHand;
    private static Hand rightHand;

    private static CameraInput camera;

    public static void main(String[] args){

        leftHand = new Hand();
        rightHand = new Hand();

        Game guitarHero = new GuitarHeroRules(leftHand, rightHand);

        while(true) {
            camera.update();
            KeyboardOutput.pressKey(guitarHero.getAreaFromLeftHandPos(leftHand.getX()));
            KeyboardOutput.pressKey(guitarHero.getAreaFromRightHandPos(rightHand.getY()));
        }

    }

    public Hand getLeftHand() {
        return leftHand;
    }

    public Hand getRightHand() {
        return rightHand;
    }

    public void setLeftHand(Hand hand) {
        this.leftHand = hand;
    }

    public void setRightHand(Hand hand) {
        this.rightHand = hand;
    }
}
