package cmput301w16t01crimsonhorizons.parkinghelper;

import java.util.concurrent.ExecutionException;

/**
 * Created by Kevin L on 3/2/2016.
 */
public class EditStallSave extends Commands {
    private Stalls stall;
    public EditStallSave( Stalls stall){
        this.stall=stall;
    }

    @Override
    public Boolean execute() {
        Boolean check = false;
        ElasticSearchCtr.updateStallES updateStallES = new ElasticSearchCtr.updateStallES();
        updateStallES.execute(stall);
        try {
            check = updateStallES.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return check;
    }

    @Override
    public void unexecute() {

    }

    @Override
    public boolean isReversible() {
        return false;
    }
}
