package cmput301w16t01crimsonhorizons.parkinghelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by schuman on 2/29/16.
 */
public class NotificationsActivity extends AppCompatActivity{
    ListView NotificationLv = (ListView)findViewById(R.id.NotificationsListView);
    private NotificationAdapter myAdapter;
    private ArrayList<NotificationObject> notifications = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_notifications);
        myAdapter = new NotificationAdapter(this, R.layout.notification_view, notifications);
        NotificationLv.setAdapter(myAdapter);
        NotificationElasticSearch.GetNotifications getNotifications = new NotificationElasticSearch.GetNotifications();
        String[] query = new String[2];
        query[1]="Owner";
        query[0]=CurrentAccount.getAccount().getEmail();
        getNotifications.execute(query);
        try {
            notifications.clear();
            notifications=getNotifications.get();
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
}
