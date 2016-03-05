package cmput301w16t01crimsonhorizons.parkinghelper;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import java.util.concurrent.ExecutionException;

/**
 * A simple method that is called within click listener events to see if a username has been
 * clicked on. If one has been then the user's profile page is displayed. Otherwise the method
 * just returns.
 *
 * <p><code>ListViewWithUserName</code> is designed to change intents to another user's
 * profile</p>
 * @author aaron schuman
 * on 3/2/16.
 */
public class ListViewWithUserName extends AppCompatActivity{

    ListViewWithUserName(View view, int position, long id, ListView listView, String userName){
        changeToUserProfileActivity(view, position, id, listView, userName);
    }

    public void changeToUserProfileActivity(View view, int position, long id, ListView listView, String userName){
        Intent intent = new Intent(view.getContext(), ViewProfile.class);
        String entry = listView.getItemAtPosition(position).toString();
        if(entry == userName) {
            ElasticSearchCtr.GetAccount getAccount = new ElasticSearchCtr.GetAccount();
            Account account = new Account();
            try {
                getAccount.execute(entry);
                account = getAccount.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            intent.putExtra("Email", entry);
            intent.putExtra("Work", account.getWorkPhone());
            intent.putExtra("Cell", account.getCellPhone());
            startActivity(intent);
        }
    }


}