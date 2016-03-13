package cmput301w16t01crimsonhorizons.parkinghelper;

import android.media.Image;

import java.io.Serializable;
import java.util.ArrayList;

import io.searchbox.annotations.JestId;

/**
 * Created by schuman on 2/23/16.
 * Edited Kevin
 * Class of what each stall would have
 * Initialize all variables.
 */
public class Stalls implements Serializable{
    private String Owner = "";
    private String Description = "";
    private String Status = "";
    private Double BidAmt = 0.00;
    private String Bidder = "";
    private String Borrower = "";
    //// TODO: 3/10/2016 MAKE THE IMAGE STORE PROPERLY
    private String image;

    public String getBorrower() {
        return Borrower;
    }
    public void setBorrower(String borrower) {
        Borrower = borrower;
    }

    ArrayList<String>LstBidders = new ArrayList<>();

    public ArrayList<String > getLstBidders() {
        return LstBidders;
    }

    public void setLstBidders(ArrayList<String > lstBidders) {
        LstBidders = lstBidders;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @JestId
    private String StallID;

    public String getBidder() {
        return Bidder;
    }

    public void setBidder(String bidder) {
        Bidder = bidder;
    }

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
