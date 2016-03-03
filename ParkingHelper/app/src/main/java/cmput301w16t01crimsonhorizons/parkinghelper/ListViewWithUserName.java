package cmput301w16t01crimsonhorizons.parkinghelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

/**
 * Created by schuman on 3/2/16.
 */
public class ListViewWithUserName extends AppCompatActivity{

    ListViewWithUserName(View view, int position, long id, ListView listView, String userName){
        changeToUserProfileActivity(view, position, id, listView, userName);
    }

    public void changeToUserProfileActivity(View view, int position, long id, ListView listView, String userName){
        Intent intent = new Intent(view.getContext(), ViewProfile.class);
        String entry = listView.getItemAtPosition(position).toString();
        if(entry == userName) {
            ElasticSearchCtr.GetAccount search = new ElasticSearchCtr.GetAccount();
            Account account = search.doInBackground(entry);
            intent.putExtra("Email", entry);
            intent.putExtra("Work", account.getWorkPhone());
            intent.putExtra("Cell", account.getCellPhone());
            startActivity(intent);
        }
    }


}