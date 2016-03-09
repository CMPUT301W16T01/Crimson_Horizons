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

/**
 * Created by Kevin L on 3/7/2016.
 */
public class ElasticSearchForTest{
    private static JestDroidClient client;
    public static class updateStallES extends AsyncTask<Stalls,Void,Boolean> {

        @Override
        protected Boolean doInBackground(Stalls... stall) {
            verifyClient();
            String status = stall[0].getStatus();
            String Description = stall[0].getDescription();
            String Owner = stall[0].getOwner();
            Double BidAmt = stall[0].getBidAmt();
            String Bidder = stall[0].getBidder();
            String doc = "{" +
                    "\"doc\": { \"Status\": " + "\""+ status + "\", " +
                    " \"Description\": " + "\""+ Description + "\", " +
                    " \"Owner\": " + "\""+ Owner + "\", " +
                    " \"BidAmt\": " + "\"" + BidAmt + "\", " +
                    " \"Bidder\": " + "\"" + Bidder + "\"" +"}}";
            try {
                DocumentResult result = client.execute(new Update.Builder(doc).index("t01").
                        type("testing_database").id(stall[0].getStallID()).build());
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
    public static class GetStall extends AsyncTask<String, Void,ArrayList<Stalls>>{
        @Override
        protected ArrayList<Stalls> doInBackground(String... search_string) {
            verifyClient();
            ArrayList<Stalls> AllStall = new ArrayList<>();
            //start initial array list empty.
            String query = "{" +
                    "    \"query\": {" +
                    "        \"match\" :{ \""+ search_string[1] +"\":" + "\""+search_string[0]+ "\""+
                    "    }" +
                    "}}";
            io.searchbox.core.Search search = new io.searchbox.core.Search.Builder(query).addIndex("t01").addType("testing_database").build();

            try {
                SearchResult execute = client.execute(search);
                if (execute.isSucceeded()){
                    List<Stalls> returned_stalls = execute.getSourceAsObjectList(Stalls.class);
                    AllStall.addAll(returned_stalls);
                }
            } catch (IOException e) {
                Log.i("TODO", "SEARCH PROBLEMS");
            }

            return AllStall;
        }
    }
    public static class DeleteStall extends AsyncTask<Stalls,Void,Boolean>{
        @Override
        protected Boolean doInBackground(Stalls... stall) {
            verifyClient();

            try {
                DocumentResult result = client.execute(new Delete.Builder(stall[0].getStallID())
                        .index("t01")
                        .type("testing_database")
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
    public static class MakeStall extends AsyncTask<Stalls, Void, Void>{

        @Override
        protected Void doInBackground(Stalls... stalls) {
            verifyClient();
            //Since AsyncTasks work on arrays, we need to work with arrays as well
            for (int i = 0; i < stalls.length; i++){
                Stalls stall = stalls[i];
                Index index = new Index.Builder(stall).index("t01").type("testing_database").build();
                try {
                    DocumentResult result = client.execute(index);
                    if (result.isSucceeded()){
                        //Set Id for tweet, can find and edit in elastic search
                        stall.setStallID(result.getId());
                    }
                    //Can also use get id,get index,get type
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            return null;
        }
    }
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
