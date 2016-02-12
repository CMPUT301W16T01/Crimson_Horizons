package cmput301w16t01.kliangtestcases;

import java.util.ArrayList;

/**
 * Created by kliang on 2/5/16.
 */
public class Stalls {
    private String Information;
    private String status;
    protected String owner;
    protected String borrower;
    public void editOwnStall(Stalls stall, Stalls nStall){}
    public void setInformation(String information){
        Information=information;
    }
    public String getInformation(){
        return Information;
    }
    public String getStatus(){return status;}
    public void setStatus(String s){status=s;}

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getBorrower() {
        return borrower;
    }

    public void setBorrower(String borrower) {
        this.borrower = borrower;
    }
}
