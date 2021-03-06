package cmput301w16t01crimsonhorizons.parkinghelper;

import java.io.Serializable;
import java.util.ArrayList;

import io.searchbox.annotations.JestId;

/**
 * Created by schuman on 2/23/16.
 * Edited:Kevin
 * This class is just what 1 account would hold. It is serializable so that we can
 * pass it along with intents.
 */
public class Account implements Serializable{
    private String Email;
    private String CellPhone;
    private String WorkPhone;
    private ArrayList<String> notifications;

    @JestId
    protected String id;

    public ArrayList<String> getNotifications() {
        return notifications;
    }

    public void setNotifications(ArrayList<String> notifications) {
        this.notifications = notifications;
    }

    public Account(String email, String cellPhone, String workPhone) {
        Email = email;
        CellPhone = cellPhone;
        WorkPhone = workPhone;
    }

    public Account() {
        new Account(null, null, null);
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


    public String toString(){
        return "{"  + "Email: " + Email + "\n" +
                "CellPhone: " + CellPhone + "\n" +
                "WorkPhone" + WorkPhone + "\n" + "}";
    }
}
