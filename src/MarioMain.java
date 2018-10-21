public class MarioMain {
    static Hand leftHand = new Hand();
    static Hand rightHand = new Hand();

    //TODO: Change to generic game type
    private static MarioRules currentApplication;
    static boolean running = true;

    public static void main(String[] args){
        motionInit();
        startLoop();
    }

    private static void motionInit() {
        initGame("GuitarHero");
        CameraInput.initCamera();
        Script.executeScript("\n");
    }


    private static void startLoop() {
        try {
            Thread.sleep(5000);
        }catch(Throwable e){
            e.printStackTrace();
        }
        while (true) {
            if (running) {
                CameraInput.updateCamera();
                KeyboardOutput.pressKey(currentApplication.getAreaFromLeftHandPos());
                KeyboardOutput.pressKey(currentApplication.getAreaFromRightHandPos());
            }
        }
    }

    private static void initGame(String guitarHero) {
        switch (guitarHero) {
            case "GuitarHero":
                currentApplication = new MarioRules();
            default:
                currentApplication = new MarioRules();
        }
    }
}
