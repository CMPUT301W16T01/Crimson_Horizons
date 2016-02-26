package cmput301w16t01crimsonhorizons.parkinghelper;

import android.os.AsyncTask;
import android.util.Log;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * Created by Kevin L on 2/24/2016.
 */
public class ElasticSearchCtr {
    private static JestDroidClient client;

    //TODO: A function that gets tweets
    //static function works with class. so just class.method()
    public static class GetUserName extends AsyncTask<String, Void,ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(String... search_string) {
            verifyClient();
            //start initial array lsit empty.
            ArrayList<String> UserName = new ArrayList<String>();
            //Searches the email address, will be the first element.
            Search search = new Search.Builder(search_string[0]).
                    addIndex("T01").
                    addType("User_Database").build();

            try {
                SearchResult execute = client.execute(search);
                if (execute.isSucceeded()){
                    //return list of things
                    ArrayList<String> returned_UserName = (ArrayList<String>)execute.getSourceAsObjectList(String.class);
                    UserName.addAll(returned_UserName);
                    return UserName;
                }
            } catch (IOException e) {
                    Log.i("TODO", "SEARCH PROBLEMS");
            }
            return UserName;
        }
    }
    //Helper function
    public static void verifyClient(){
        //verify that "client" exists and if it does not make it.
        //This had to be done the other functions anyway. Just make a helper function.
        if (client == null ){
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder("http://cmput301.softwareprocess.es:8080");
            DroidClientConfig config = builder.build();
            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient)factory.getObject();
        }
    }
}
