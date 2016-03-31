package cmput301w16t01crimsonhorizons.parkinghelper;

/**
 * Created by Kevin L on 3/11/2016.
 * commands for login
 */
public class LoginCommands extends Commands {
    String Email;
    public LoginCommands(String email){
        this.Email = email;
    }
    @Override
    public Boolean execute() {
        return null;
    }

    @Override
    public void unexecute() {

    }

    @Override
    public boolean isReversible() {
        return false;
    }
    public Boolean CheckCommand(){
        return ElasticSearchCtr.CheckAccount(Email);
    }
}
