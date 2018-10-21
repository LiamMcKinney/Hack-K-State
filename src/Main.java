import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Main {

    static Hand leftHand = new Hand();
    static Hand rightHand = new Hand();

    //TODO: Change to generic game type
    private static Game currentApplication;
    static boolean running = true;

    public static void main(String[] args){
        motionInit();
        startLoop();
    }

    private static void motionInit() {
        initGame("GuitarHero");
        CameraInput.initCamera();
        Script.executeScript("m111");
}


    private static void startLoop() {
        try {
            Thread.sleep(5000);

            while (true) {
                if (running) {
                    CameraInput.updateCamera();
                    KeyboardOutput.pressKey(currentApplication.getAreaFromLeftHandPos());
                    KeyboardOutput.pressKey(currentApplication.getAreaFromRightHandPos());
                    //Thread.sleep(150);
                    //System.out.println(leftHand.getX());
                    //System.out.println(rightHand.getX());
                }
            }
        }catch(Throwable e){
            e.printStackTrace();
        }
    }

    private static void initGame(String game) {
        switch (game) {
            case "GuitarHero":
                GuitarHeroRules.init();
                currentApplication = new GuitarHeroRules();
                break;
            case "Mario":
                MarioRules.init();
                currentApplication = new MarioRules();
                break;
            default:
                GuitarHeroRules.init();
                currentApplication = new GuitarHeroRules();
        }
    }
}
