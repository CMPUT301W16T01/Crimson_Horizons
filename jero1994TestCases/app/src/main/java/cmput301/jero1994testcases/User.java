package cmput301.jero1994testcases;

import java.util.ArrayList;

/**
 * Created by jero1 on 2016-02-12.
 */
public class User {
    private ArrayList<Thing> userThings = new ArrayList<Thing>();
    private ArrayList<Bid> userBids = new ArrayList<Bid>();

    public User() {}

    public ArrayList<Thing> GetThings() {
        return this.userThings;
    }
    public ArrayList<Thing>GetThingsWithBids() {
        ArrayList<Thing> result = new ArrayList<Thing>();
        for (Thing t : userThings) {
            if (t.GetBids().size() > 0) { result.add(t); }
        }
        return result;
    }
    public ArrayList<Bid> GetBids() {
        return this.userBids;
    }

    public void AddThing(String placeholderInfo) {
        Thing thing = new Thing(this);
        userThings.add(thing);
    }
    public void MakeBid(Thing thing) {
        Bid bid = new Bid(this, thing);
        this.userBids.add(bid);
    }

    public void AcceptBid(Bid bid) {
        bid.GetThing().ClearBids();
        bid.GetThing().LendTo(bid.GetBidder());
    }
    public void RejectBid(Bid bid) {
        bid.Remove();
    }
}
