package cmput301w16t01crimsonhorizons.parkinghelper;

/**
 * Created by schuman on 2/23/16.
 */
public class Bid {
    private Account bidder;
    private double bidAmount;
    private Stalls bidStall;
    public Account Bidder() {
        return bidder;
    }
    public double BidAmount() {
        return bidAmount;
    }
    public Stalls BidStall() {
        return bidStall;
    }

    public Bid(Account bidder, double bidAmount, Stalls bidStall) {
        this.bidder = bidder;
        this.bidAmount = bidAmount;
        this.bidStall = bidStall;
    }
}
