package cmput301w16t01crimsonhorizons.parkinghelper;

import android.os.Parcelable;

import java.io.Serializable;

import io.searchbox.annotations.JestId;

/**
 * Created by schuman on 2/23/16.
 */
public class Stalls implements Serializable{
    private String Owner = "";
    private String Description = "";
    private String Status = "";
    private Double BidAmt = 0.00;

    @JestId
    private String StallID;

    public Double getBidAmt() {
        return BidAmt;
    }

    public void setBidAmt(Double bidAmt) {
        BidAmt = bidAmt;
    }

    public String getOwner() {
        return Owner;
    }

    public void setOwner(String owner) {
        Owner = owner;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getStallID() {
        return StallID;
    }

    public void setStallID(String stallID) {
        StallID = stallID;
    }


}
