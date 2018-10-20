

public class Main {

    static Hand leftHand = new Hand();
    static Hand rightHand = new Hand();

    //TODO: Change to generic game type
    private static GuitarHeroRules currentApplication;

    public static void main(String[] args){
        motionInit();
        startLoop();
    }

    private static void motionInit() {
        initGame("GuitarHero");
        CameraInput.initCamera();
    }

    private static void startLoop() {
        try {
            Thread.sleep(5000);
        }catch(Throwable e){
            e.printStackTrace();
        }
        boolean running = true;
        while (running) {
            CameraInput.updateCamera();
            KeyboardOutput.pressKey(currentApplication.getAreaFromLeftHandPos());
            KeyboardOutput.pressKey(currentApplication.getAreaFromRightHandPos());
        }
    }

    private static void initGame(String guitarHero) {
        switch (guitarHero) {
            case "GuitarHero":
                GuitarHeroRules.init();
                currentApplication = new GuitarHeroRules();
            default:
                GuitarHeroRules.init();
                currentApplication = new GuitarHeroRules();
        }
    }
}
