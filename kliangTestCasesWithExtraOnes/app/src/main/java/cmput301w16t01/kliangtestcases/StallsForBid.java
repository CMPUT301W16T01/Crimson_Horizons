package cmput301w16t01.kliangtestcases;

/**
 * Created by kliang on 2/9/16.
 */
public class StallsForBid extends Stalls{
    public StallsForBid(){}
    private Boolean Availabe=Boolean.TRUE;
    private Double bidAmt;
    private String Description;
    private String Bidder;
    public Boolean getAvailabe(){
        return Availabe;
    }
    public void bid(Double amount){
        this.bidAmt=amount;
    }
    public Double getbidAmt(){
        return this.bidAmt;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getBidder() {
        return Bidder;
    }

    public void setBidder(String bidder) {
        Bidder = bidder;
    }
}
