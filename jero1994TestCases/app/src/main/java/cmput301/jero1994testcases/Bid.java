package cmput301.jero1994testcases;

/**
 * Created by jero1 on 2016-02-12.
 */
public class Bid {
    private Thing thing;
    private User bidder;

    public Bid(User bidder, Thing thing) {
        this.bidder = bidder;
        this.thing = thing;
        thing.AddBid(this);
    }

    public User GetBidder() {
        return this.bidder;
    }
    public Thing GetThing() {
        return this.thing;
    }

    public void Remove() {
        this.thing.RemoveBid(this);
    }

    @Override
    public String toString() {
        return "Bid";
    }
}
