package cmput301w16t01.kliangtestcases;

/**
 * Created by Kevin L on 2/11/2016.
 */
public class Profile {
    private String Username;
    private String Email;
    private String Workphone;
    private String Cellphone;

    public String getUsername() {
        return Username;
    }

    public String getEmail() {
        return Email;
    }

    public String getWorkphone() {
        return Workphone;
    }

    public String getCellphone() {
        return Cellphone;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setWorkphone(String workphone) {
        Workphone = workphone;
    }

    public void setCellphone(String cellphone) {
        Cellphone = cellphone;
    }

    public String ShowInformation(){
        String Information="Username: " + Username + "\n" +
                            "Email: " + Email + "\n" +
                            "Cell Phone: " + Cellphone + "\n" +
                            "Work Phone: " + Workphone + "\n";
        return Information;
    }
}
