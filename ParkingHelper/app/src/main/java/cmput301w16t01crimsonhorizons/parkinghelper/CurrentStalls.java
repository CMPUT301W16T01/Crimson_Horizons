package cmput301w16t01crimsonhorizons.parkinghelper;

import java.util.ArrayList;

/**
 * Created by Kevin L on 3/23/2016.
 * This is to keep a local copy of all the stalls the user currently owns. It is also a singleton
 * @see CurrentAccount
 */
public class CurrentStalls extends Stalls {
    private static ArrayList<Stalls>currentStalls = new ArrayList<>();
    protected CurrentStalls() {
        super();
    }

    public static void setCurrentStalls(ArrayList<Stalls> stalls) {
        currentStalls = stalls;
    }
    public static ArrayList<Stalls> getCurrentStalls(){
        if(currentStalls == null){
            return new ArrayList<Stalls>();
        }
        return currentStalls;
    }
}
