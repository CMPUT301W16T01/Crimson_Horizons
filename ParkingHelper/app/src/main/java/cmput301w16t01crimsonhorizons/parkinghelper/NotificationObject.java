package cmput301w16t01crimsonhorizons.parkinghelper;

import io.searchbox.annotations.JestId;

/**
 * Created by Kevin L on 3/25/2016.
 */
public class NotificationObject {
    private String Owner;
    private String Bidder;
    private String BidAmt;
    private String Date;
    @JestId
    private String StallID;

    public String getStallID() {
        return StallID;
    }

    public void setStallID(String stallID) {
        StallID = stallID;
    }

    public String getOwner() {
        return Owner;
    }

    public void setOwner(String owner) {
        Owner = owner;
    }

    public String getBidder() {
        return Bidder;
    }

    public void setBidder(String bidder) {
        Bidder = bidder;
    }

    public String getBidAmt() {
        return BidAmt;
    }

    public void setBidAmt(String bidAmt) {
        BidAmt = bidAmt;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
