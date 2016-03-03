package cmput301w16t01crimsonhorizons.parkinghelper;

/**
 * Created by Kevin L on 3/2/2016.
 */
public abstract class Commands {


    abstract public Boolean execute();
    abstract public void unexecute();
    abstract public boolean isReversible();
}
