package cmput301w16t01crimsonhorizons.parkinghelper;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by Kevin L on 3/5/2016.
 * Command object that updates_add stall
 * The search command is called by any view classes that needs stalls from elastic search
 * On execute it simply calls notifyViews.
 */
public class UpdateCommand extends Commands{
    private String[] email;
    public UpdateCommand(String[] infomration){
        this.email=infomration;
    }
    public ArrayList<Stalls> search(){
        ElasticSearchCtr.GetStall getStall = new ElasticSearchCtr.GetStall();
        getStall.execute(email);
        ArrayList<Stalls>returned_stalls = new ArrayList<>();
        try {
            returned_stalls = getStall.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return returned_stalls;
    }

    @Override
    public Boolean execute() {
        notifyViews();
        return true;
    }

    @Override
    public void unexecute() {

    }

    @Override
    public boolean isReversible() {
        return false;
    }
}
