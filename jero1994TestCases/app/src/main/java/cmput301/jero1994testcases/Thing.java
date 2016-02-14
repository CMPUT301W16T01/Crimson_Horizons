package cmput301.jero1994testcases;

import java.util.ArrayList;

/**
 * Created by jero1 on 2016-02-12.
 */
public class Thing {
    private ArrayList<Bid> thingBids = new ArrayList<Bid>();
    private User owner;
    private User borrower = null;
    private boolean borrowed = false;

    public boolean IsBorrowed() { return borrowed; }
    public User Borrower() { return borrower; }

    public Thing(User owner) {
        this.owner = owner;
    }

    public ArrayList<Bid> GetBids() {
        return this.thingBids;
    }
    public User GetOwner() {
        return this.owner;
    }

    public void AddBid(Bid bid) {
        this.thingBids.add(bid);
    }
    public void RemoveBid(Bid bid) {
        this.thingBids.remove(bid);
    }
    public void ClearBids() {
        this.thingBids = new ArrayList<Bid>();
    }

    public void LendTo(User borrower) {
        this.borrower = borrower;
        this.borrowed = true;
    }
    public void Retrieve() {
        this.borrower = null;
        this.borrowed = false;
    }

    @Override
    public String toString() {
        return "Thing";
    }
}
