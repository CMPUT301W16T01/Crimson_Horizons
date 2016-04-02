package cmput301w16t01crimsonhorizons.parkinghelper;

/**
 * Created by schuman on 4/1/16.
 */
public class BidsForStallsObject {
    public BidsForStallsObject(String newText, Stalls stall) {
        setStalls(stall);
        setString(newText);
    }

    public Stalls getStalls() {
        return stalls;
    }

    public void setStalls(Stalls stalls) {
        this.stalls = stalls;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    private Stalls stalls;
    private String string;
}
