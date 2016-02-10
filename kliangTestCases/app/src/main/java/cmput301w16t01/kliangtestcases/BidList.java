package cmput301w16t01.kliangtestcases;

import java.util.ArrayList;

/**
 * Created by kliang on 2/9/16.
 */
public class BidList extends StallList{
    private ArrayList<StallsForBid>AvailableStalls = new ArrayList<>();

    public ArrayList<StallsForBid> getAvailableStalls(){
        return AvailableStalls;
    }
}
