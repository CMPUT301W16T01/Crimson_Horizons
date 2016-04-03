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
    public Boolean execute() {
        Boolean check = false;
        ElasticSearchCtr.DeleteStall deleteStall = new ElasticSearchCtr.DeleteStall();
        ElasticSearchCtr.GetBid getBid = new ElasticSearchCtr.GetBid();
        ElasticSearchCtr.DeleteBid deleteBid = new ElasticSearchCtr.DeleteBid();
        ElasticSearchCtr.GetReview getReview = new ElasticSearchCtr.GetReview();
        ElasticSearchCtr.DeleteReview deleteReview = new ElasticSearchCtr.DeleteReview();

        getBid.execute(new String[] { "bidStallID", stall.getStallID() });
        getReview.execute(new String[] { "StallID", stall.getStallID() });
        deleteStall.execute(stall);
        try {
            check = deleteStall.get();
            //Deletes all bids on the stall
            for (Bid b : getBid.get()) {
                deleteBid.execute(b);
            }
            for (Review r : getReview.get()) {
                deleteReview.execute(r);
            }
        } catch (InterruptedException | ExecutionException e) {
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
