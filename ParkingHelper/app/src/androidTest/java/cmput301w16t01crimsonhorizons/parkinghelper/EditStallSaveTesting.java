package cmput301w16t01crimsonhorizons.parkinghelper;

/**
 * Created by Kevin L on 3/11/2016.
 */
public class EditStallSaveTesting extends EditStallSave {
    public EditStallSaveTesting(Stalls stall){
        super(stall);
    }
    public Stalls testexecute(){
        super.stall.setOwner("changed");
        return super.stall;
    }
}
