package cmput301w16t01crimsonhorizons.parkinghelper;

/**
 * Created by schuman on 2/23/16.
 */
public class StallsWithBids {
    private Account Bidder;
    private Integer BidAmount;
    private Stalls BidStall;

    public Stalls getBidStall() {
        return BidStall;
    }

    public void setBidStall(Stalls bidStall) {
        BidStall = bidStall;
    }

    public Account getBidder() {
        return Bidder;
    }

    public void setBidder(Account bidder) {
        Bidder = bidder;
    }

    public Integer getBidAmount() {
        return BidAmount;
    }

    public void setBidAmount(Integer bidAmount) {
        BidAmount = bidAmount;
    }

}
