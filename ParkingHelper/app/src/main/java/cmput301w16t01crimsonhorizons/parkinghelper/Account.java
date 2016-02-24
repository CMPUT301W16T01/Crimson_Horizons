package cmput301w16t01crimsonhorizons.parkinghelper;

/**
 * Created by schuman on 2/23/16.
 */
public class Account {
    private String Email;
    private String Username;
    private String CellPhone;
    private String WorkPhone;

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

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getCellPhone() {
        return CellPhone;
    }

    public void setCellPhone(String cellPhone) {
        CellPhone = cellPhone;
    }

}
