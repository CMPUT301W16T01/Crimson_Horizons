package cmput301w16t01crimsonhorizons.parkinghelper;

import android.os.AsyncTask;
import android.util.Log;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.searchbox.core.*;
import io.searchbox.core.Search;

/**
 * Created by Kevin L on 3/25/2016
 * Elastic search much like ElasticSearchCtr, but for notifications only
 * @see ElasticSearchCtr
 */
public class NotificationElasticSearch {
    private static JestDroidClient client;

    /**
     * Returns a list of notificaions for this user
     */
    public static class GetNotifications extends AsyncTask<String, Void,ArrayList<NotificationObject>>{
        @Override

        protected ArrayList<NotificationObject> doInBackground(String... search_string) {
            verifyClient();
            ArrayList<NotificationObject> Notifications = new ArrayList<>();
            //start initial array list empty.
            String query = "{" +"\"query\":{\"match\":{\""+search_string[1]+"\": "+"\""+search_string[0]+"\""+"}}}";
            io.searchbox.core.Search search = new Search.Builder(query).addIndex("t01").addType("notification_database").build();

            try {
                SearchResult execute = client.execute(search);
                if (execute.isSucceeded()){
                    List<NotificationObject> returned_notifications = execute.getSourceAsObjectList(NotificationObject.class);
                    Notifications.addAll(returned_notifications);
                }
            } catch (IOException e) {
                Log.i("TODO", "SEARCH PROBLEMS");
            }

            return Notifications;
        }
    }

    /**
     * add a notification
     */
    public static class AddNotification extends AsyncTask<NotificationObject, Void, Void>{


        @Override
        protected Void doInBackground(NotificationObject... notifications) {
            verifyClient();
            //Since AsyncTasks work on arrays, we need to work with arrays as well
            for (int i = 0; i < notifications.length; i++){
                NotificationObject notification = notifications[i];
                Index index = new Index.Builder(notification).index("t01").type("notification_database").build();
                try {
                    DocumentResult result = client.execute(index);
                    if (result.isSucceeded()){
                        //Set Id for tweet, can find and edit in elastic search
                        notification.setStallID(result.getId());
                    }
                    //Can also use get id,get index,get type
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            return null;
        }
    }

    /**
     * delete a notification
     */
    public static class DeleteNotification extends AsyncTask<NotificationObject,Void,Boolean>{
        @Override
        protected Boolean doInBackground(NotificationObject... notificationObjects) {
            verifyClient();
            try {
                DocumentResult result = client.execute(new Delete.Builder(notificationObjects[0].getStallID())
                        .index("t01")
                        .type("notification_database")
                        .build());
                if (result.isSucceeded()){
                    return true;
                } else {
                    return false;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }
    }

    /**
     * helper function
     */
    public static void verifyClient(){
        //verify that "client" exists and if it does not make it.
        //This had to be done the other functions anyway. Just make a helper function.

        if (client == null) {
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder("http://cmput301.softwareprocess.es:8080");
            DroidClientConfig config = builder.build();
            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }
}
