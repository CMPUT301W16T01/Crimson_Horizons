package cmput301w16t01crimsonhorizons.parkinghelper;

/**
 * Created by schuman on 3/1/16.
 */
public class CurrentAccount extends Account{
    private static Account account = null;

    public CurrentAccount() {
        super("","","");
        account=null;
    }


    public static void setAccount(Account newAccount) {
        if (account == null) {
            account = newAccount;
        }
    }
    public static Account getAccount(){
        if (account == null){
           account = new CurrentAccount();
        }
        return account;
    }
}
