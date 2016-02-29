package cmput301w16t01crimsonhorizons.parkinghelper;

import java.io.Serializable;
import java.util.ArrayList;

import io.searchbox.annotations.JestId;

/**
 * Created by schuman on 2/23/16.
 */
public class Account implements Serializable{
    private String Email;
    private String CellPhone;
    private String WorkPhone;
    private ArrayList<Stalls> OwnStalls;

    @JestId
    protected String id;

    public Account(String email, String cellPhone, String workPhone, ArrayList<Stalls> ownStalls) {
        Email = email;
        CellPhone = cellPhone;
        WorkPhone = workPhone;
        OwnStalls = ownStalls;
    }

    public Account(String email, String cellPhone, String workPhone) {
        Email = email;
        CellPhone = cellPhone;
        WorkPhone = workPhone;
    }

    public String getWorkPhone() {
        return WorkPhone;

    }

    public void setWorkPhone(String workPhone) {
        WorkPhone = workPhone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getCellPhone() {
        return CellPhone;
    }

    public void setCellPhone(String cellPhone) {
        CellPhone = cellPhone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Stalls> getOwnStalls() {
        return OwnStalls;
    }

    public void setOwnStalls(ArrayList<Stalls> ownStalls) {
        OwnStalls = ownStalls;
    }

    public String toString(){
        return Email + " | " + CellPhone + " | " + WorkPhone + " | " + OwnStalls.toString();
    }
}
