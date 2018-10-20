import java.awt.*;
import java.util.ArrayList;

public class KeyboardOutput {

    /**
     * Presses a list of buttons
     * @param buttons the list of buttons
     */
    public static void pressKeys(ArrayList<Integer> buttons) {
        try {
            Robot robot = new Robot();

            for(int i = 0; i < buttons.size(); i++) {
                robot.keyPress(buttons.get(i));
            }

        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public static void pressKey(int button) {
        try {
            Robot robot = new Robot();

            robot.keyPress(button);

        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    /**
     * Releases a list of buttons
     * @param buttons the list of buttons
     */
    public static void releaseKeys(ArrayList<Integer> buttons) {
        try {
            Robot robot = new Robot();

            for(int i = 0; i < buttons.size(); i++) {
                robot.keyRelease(buttons.get(i));
            }

        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public static void releaseKey(int button) {
        try {
            Robot robot = new Robot();

            robot.keyRelease(button);

        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

}
