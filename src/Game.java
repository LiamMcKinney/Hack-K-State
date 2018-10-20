import java.util.ArrayList;
import java.util.Map;

public interface Game {

    Map<Integer, Integer> getLeftHandButtons();

    Map<Integer, Integer> getRightHandButtons();

    ArrayList<Integer> getKeysFromLeftHand(ArrayList<Integer> list);

    ArrayList<Integer> getKeysFromRightHand(ArrayList<Integer> list);

    int getAreaFromLeftHandPos();

    int getAreaFromRightHandPos();
}
