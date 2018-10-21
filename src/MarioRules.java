import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MarioRules implements Game {
    static Map<Integer, Integer> leftHandMap = new HashMap<Integer, Integer>();
    int jumpY = 40;
    int leftX = 60;
    int rightX = 260;
    int fireY = 200;

    public static void init(){
        leftHandMap.put(0, KeyEvent.VK_Z);
        leftHandMap.put(1, KeyEvent.VK_LEFT);
        leftHandMap.put(2, KeyEvent.VK_RIGHT);
        leftHandMap.put(3, KeyEvent.VK_X);

    }

    @Override
    public Map<Integer, Integer> getRightHandButtons() {
        return null;
    }

    @Override
    public Map<Integer, Integer> getLeftHandButtons() {
        return leftHandMap;
    }

    @Override
    public ArrayList<Integer> getKeysFromLeftHand(ArrayList<Integer> list) {
        return null;
    }

    @Override
    public ArrayList<Integer> getKeysFromRightHand(ArrayList<Integer> list) {
        return null;
    }

    @Override
    public int getAreaFromRightHandPos() {
        return 0;
    }

    @Override
    public int getAreaFromLeftHandPos() {
        //Hand lh = Main.leftHand;
        Hand rh = Main.leftHand;
        if(rh.getY() == 0){
            return 0;
        }else if(rh.getY() < jumpY){
            KeyboardOutput.releaseKey(leftHandMap.get(1));
            KeyboardOutput.releaseKey(leftHandMap.get(2));
            KeyboardOutput.releaseKey(leftHandMap.get(3));
            System.out.println("jump");
            return leftHandMap.get(0);
        }else if(rh.getY() > fireY){
            KeyboardOutput.releaseKey(leftHandMap.get(0));
            KeyboardOutput.releaseKey(leftHandMap.get(1));
            KeyboardOutput.releaseKey(leftHandMap.get(2));
            System.out.println("fire");
            return leftHandMap.get(3);
        }else if(rh.getX() < leftX){
            KeyboardOutput.releaseKey(leftHandMap.get(0));
            KeyboardOutput.releaseKey(leftHandMap.get(2));
            KeyboardOutput.releaseKey(leftHandMap.get(3));
            System.out.println("left");
            return leftHandMap.get(1);
        }else if(rh.getX() > rightX){
            KeyboardOutput.releaseKey(leftHandMap.get(0));
            KeyboardOutput.releaseKey(leftHandMap.get(1));
            KeyboardOutput.releaseKey(leftHandMap.get(3));
            System.out.println("right");
            return leftHandMap.get(2);
        }else {
            System.out.println("nothing");
            return 0;

        }
    }
}
