package cmput301w16t01crimsonhorizons.parkinghelper;

/**
 * Created by schuman on 3/1/16.
 * Singleton that keeps track of the user's account and all the information.
 */
public class CurrentAccount extends Account{
    private static Account account = null;

    protected CurrentAccount() {
        super();
    }


    public static void setAccount(Account newAccount) {
        account = newAccount;
    }
    public static Account getAccount(){
        if(account == null){
            return new Account();
        }
        return account;
    }
}
