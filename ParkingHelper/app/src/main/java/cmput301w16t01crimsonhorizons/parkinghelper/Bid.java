package cmput301w16t01crimsonhorizons.parkinghelper;

import io.searchbox.annotations.JestId;

/**
 * Created by schuman on 2/23/16.
 */
public class Bid {
    private String bidder = "";
    private Double bidAmount;
    private String bidStallID;
    public String Bidder() {
        return bidder;
    }
    public Double BidAmount() {
        return bidAmount;
    }
    public String BidStallID() {
        return bidStallID;
    }

    public Bid(String bidder, Double bidAmount, String bidStallID) {
        this.bidder = bidder;
        this.bidAmount = bidAmount;
        this.bidStallID = bidStallID;
    }

    @JestId
    private String BidID;

    public String getBidID() {
        return BidID;
    }
    public void setBidID(String bidID) {
        BidID = bidID;
    }
}
