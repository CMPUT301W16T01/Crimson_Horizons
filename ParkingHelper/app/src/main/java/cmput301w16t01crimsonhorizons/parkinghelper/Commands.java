package cmput301w16t01crimsonhorizons.parkinghelper;

import java.util.ArrayList;

/**
 * Created by Kevin L on 3/2/2016.
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
