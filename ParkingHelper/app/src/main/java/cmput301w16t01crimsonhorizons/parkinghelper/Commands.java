package cmput301w16t01crimsonhorizons.parkinghelper;

import java.util.ArrayList;

/**
 * Created by Kevin L on 3/2/2016.
 * This is part of command pattern
 * So we can manage undo/redo as well as offline behaviour
 *
 * This is the supre class. It contains some methods that currently does nothing, and if the
 * child class needs to use it must override.
 * Not all child class will use all methods, but all abstract methods must exist.
 */
public abstract class Commands extends Model<ViewInterface>{
    abstract public Boolean execute();
    abstract public void unexecute();
    abstract public boolean isReversible();
    //Template method. Any command object will be able to call this UpdateStall()
    // Updating data requires retrieving information, so in that object they must provide the
    //appropriate search string.
    //The search string array must be as follows {what it is trying to match,what field it belongs to}
    //Example want to serach for all stalls that has Available under the status field
    //Search string is {"Available","Status"}

    public ArrayList<Stalls> UpdateStall(String[] info){
        ArrayList<Stalls> stall = new ArrayList<>();
        stall = search(info);
        return stall;
    }
    public ArrayList<Stalls> search(String[] search){return new ArrayList<>();}
}
