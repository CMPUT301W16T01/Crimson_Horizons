package cmput301w16t01crimsonhorizons.parkinghelper;

import java.io.Serializable;

import io.searchbox.annotations.JestId;

/**
 * Created by schuman on 2/23/16.
 */
public class Account implements Serializable{
    private String Email;
    private String CellPhone;
    private String WorkPhone;

    @JestId
    protected String id;

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
