package cmput301w16t01.kliangtestcases;

import java.util.ArrayList;

/**
 * Created by kliang on 2/5/16.
 */
public class StallList {
    ArrayList<Stalls> OwnStalls= new ArrayList<>();

    public int size(){
        return OwnStalls.size();
    }

    public void editOwnStall(Stalls stall1, Stalls stall2){}

    public void addStall(Stalls stall){
        OwnStalls.add(stall);
    }

    public void deleteStalls(int Index){
        OwnStalls.remove(Index);

    }
    public Stalls get(int Index){
        return OwnStalls.get(Index);
    }
    public void clearLst(){
        OwnStalls.clear();
    }
    public ArrayList<Stalls> getLst(){
        return OwnStalls;
    }
}
