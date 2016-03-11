package cmput301w16t01crimsonhorizons.parkinghelper;

import android.text.BoringLayout;

import java.util.concurrent.ExecutionException;

/**
 * Created by Kevin L on 3/2/2016.
 *
 * Command object that is to delete a stall.
 */
public class DeleteStall extends Commands {
    protected Stalls stall;
    public DeleteStall( Stalls stall){
        this.stall=stall;
    }

    @Override
    /**
     * on execute, it will use ElasticSearchCtr to delete the stall and will return true/false
     */
    public Boolean execute() {
        Boolean check = false;
        ElasticSearchCtr.DeleteStall deleteStall = new ElasticSearchCtr.DeleteStall();
        deleteStall.execute(stall);
        try {
            check = deleteStall.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return check;
    }

    /**
     * Implemented if it is undoable, in this case it is not.
     */
    @Override
    public void unexecute() {

    }
    @Override
    public boolean isReversible() {
        return false;
    }
}
