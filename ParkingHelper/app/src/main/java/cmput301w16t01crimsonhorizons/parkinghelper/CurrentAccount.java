package cmput301w16t01crimsonhorizons.parkinghelper;

/**
 * Created by schuman on 3/1/16.
 */
public class CurrentAccount extends Account{
    private static CurrentAccount account = null;

    public CurrentAccount(){


        if(account==null) {
            // TODO: Fetch the account information from elastic search and use it to build an Account object
            // if this is called without a parameter throw an exception
            //account = (CurrentAccount) newAccount;
            //new CurrentAccount();
        }
    }

    public void setAccount(Account newAccount) {
        if (account == null) {
            account = (CurrentAccount) newAccount;
        }
    }
}
