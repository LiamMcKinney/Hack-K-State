import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GuitarHeroRules implements Game {

    private static final double Y_POS_UP = 180;
    private static final double Y_POS_DOWN = 540;

    private static final double X_POS_0 = 160;
    private static final double X_POS_1 = 320;
    private static final double X_POS_2 = 480;

    private static Map<Integer, Integer> leftHandMap = new HashMap<>();
    private static Map<Integer, Integer> rightHandMap = new HashMap<>();

    /**
     * Sets many game specific hand to key inputs
     */
    static void init() {
        //defining game specific hand to key relations
        leftHandMap.put(0, KeyEvent.VK_1);
        leftHandMap.put(1, KeyEvent.VK_2);
        leftHandMap.put(2, KeyEvent.VK_3);
        leftHandMap.put(3, KeyEvent.VK_4);

        rightHandMap.put(0, KeyEvent.VK_ENTER);
        rightHandMap.put(1, KeyEvent.VK_P);
    }

    /**
     * Converts the hand's fingers that are down on the left hand to key presses
     * @param list The list of fingers that are down
     * @return the list of keys to be pressed
     */
    public ArrayList<Integer> getKeysFromLeftHand(ArrayList<Integer> list) {
        ArrayList<Integer> pressedKeys = new ArrayList<>();
        for(int i = 0; i < list.size(); i++) {
            pressedKeys.add(leftHandMap.get(i));
        }
        return pressedKeys;
    }

    /**
     * Converts the hand's fingers that are down on the right hand to key presses
     * @param list The list of fingers that are down
     * @return the list of keys to be pressed
     */
    public ArrayList<Integer> getKeysFromRightHand(ArrayList<Integer> list) {
        ArrayList<Integer> pressedKeys = new ArrayList<>();
        for(int i = 0; i < list.size(); i++) {
            pressedKeys.add(rightHandMap.get(i));
        }
        return pressedKeys;
    }

    /**
     * Converts the position on the screen of the left hand to a key press
     * @return The key press
     */
    public int getAreaFromLeftHandPos() {
        if (Main.leftHand.getX() < X_POS_0) {
            KeyboardOutput.releaseKey(leftHandMap.get(1));
            KeyboardOutput.releaseKey(leftHandMap.get(2));
            KeyboardOutput.releaseKey(leftHandMap.get(3));
            return leftHandMap.get(0);
        } else if (Main.leftHand.getX() < X_POS_1) {
            KeyboardOutput.releaseKey(leftHandMap.get(0));
            KeyboardOutput.releaseKey(leftHandMap.get(2));
            KeyboardOutput.releaseKey(leftHandMap.get(3));
            return leftHandMap.get(1);
        } else if (Main.leftHand.getX() < X_POS_2) {
            KeyboardOutput.releaseKey(leftHandMap.get(0));
            KeyboardOutput.releaseKey(leftHandMap.get(1));
            KeyboardOutput.releaseKey(leftHandMap.get(3));
            return leftHandMap.get(2);
        } else {
            KeyboardOutput.releaseKey(leftHandMap.get(0));
            KeyboardOutput.releaseKey(leftHandMap.get(1));
            KeyboardOutput.releaseKey(leftHandMap.get(2));
            return leftHandMap.get(3);
        }
    }

    /**
     * Converts the position on the screen of the right hand to a key press
     * @return The key press
     */
    public int getAreaFromRightHandPos() {
        if (Main.leftHand.getY() < Y_POS_UP || Main.leftHand.getY() > Y_POS_DOWN) {
            KeyboardOutput.releaseKey(rightHandMap.get(1));
            return rightHandMap.get(0);
        } else {
            KeyboardOutput.releaseKey(rightHandMap.get(0));
            return rightHandMap.get(1);
        }
    }

    /**
     * @return the conversion map from pos / fingers to key presses
     */
    public Map<Integer, Integer> getLeftHandButtons() {
        return leftHandMap;
    }

    /**
     * @return the conversion map from pos / fingers to key presses
     */
    public Map<Integer, Integer> getRightHandButtons() {
        return rightHandMap;
    }

}
