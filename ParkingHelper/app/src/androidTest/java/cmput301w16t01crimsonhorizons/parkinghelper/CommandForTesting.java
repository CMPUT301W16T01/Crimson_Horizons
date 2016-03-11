package cmput301w16t01crimsonhorizons.parkinghelper;

import android.location.Location;

/**
 * Created by Kevin L on 3/10/2016.
 */
public class CommandForTesting extends Commands {
    private Stalls s1;
    private Location local;
    public CommandForTesting(Stalls s1){
        this.s1=s1;
    }
    public CommandForTesting(Location l){
        this.local=l;
    }
    public CommandForTesting(){}
    @Override
    public Boolean execute() {
        return null;
    }

    @Override
    public void unexecute() {

    }

    @Override
    public boolean isReversible() {
        return false;
    }
    public Stalls getStall(){
        return this.s1;
    }
}
