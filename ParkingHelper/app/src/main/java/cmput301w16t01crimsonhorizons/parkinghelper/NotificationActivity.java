package cmput301w16t01crimsonhorizons.parkinghelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * This is a adapter for displaying notification. Currently it displays Bidder, BidAmt and date
 * they made bid.
 */
public class NotificationActivity extends AppCompatActivity {
    private NotificationAdapter myAdapter;
    private ArrayList<NotificationObject> notifications = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_notification);
        ListView NotificationLv = (ListView)findViewById(R.id.NotificationsListView);
        myAdapter = new NotificationAdapter(this, R.layout.notification_view, notifications);
        NotificationLv.setAdapter(myAdapter);
        NotificationElasticSearch.GetNotifications getNotifications = new NotificationElasticSearch.GetNotifications();
        String[] query = new String[2];
        query[1] = "Owner";
        query[0] = CurrentAccount.getAccount().getEmail();
        getNotifications.execute(query);
        try {
            ArrayList<NotificationObject>temp = new ArrayList<>();
            temp = getNotifications.get();
            notifications.clear();
            notifications.addAll(temp);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        myAdapter.notifyDataSetChanged();

    }
    public void adapterClickUserName(View view){
        ClickUserName clickUserName = new ClickUserName();
        Intent newIntent = clickUserName.clickUserName(this, view, new ElasticSearchCtr.GetAccount());
        startActivity(newIntent);
    }
    public void update(View view){
        finish();
        startActivity(getIntent());
    }
}
