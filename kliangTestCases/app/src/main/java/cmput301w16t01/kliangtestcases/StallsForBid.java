package cmput301w16t01.kliangtestcases;

/**
 * Created by kliang on 2/9/16.
 */
public class StallsForBid extends Stalls{
    public StallsForBid(){}
    private Boolean Availabe=Boolean.TRUE;
    private Double bidAmt;

    public Boolean getAvailabe(){
        return Availabe;
    }
    public void bid(Double amount){
        this.bidAmt=amount;
    }
    public Double getbidAmt(){
        return this.bidAmt;
    }
}
