package cmput301w16t01crimsonhorizons.parkinghelper;

import java.util.ArrayList;

/**
 * Created by schuman on 2/23/16.
 */
public class OwnStalls {
    private ArrayList<Stalls> ownStallList = new ArrayList<>();

    public Stalls getStall(Integer index){
        return ownStallList.get(index);
    }

    public void addStall(Stalls newStall){
        ownStallList.add(newStall);
    }

    public void removeStall(Integer index){
        ownStallList.remove(index);
    }
}
