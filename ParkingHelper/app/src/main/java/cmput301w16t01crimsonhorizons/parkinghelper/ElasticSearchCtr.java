package cmput301w16t01crimsonhorizons.parkinghelper;

import android.os.AsyncTask;
import android.util.Log;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.searchbox.core.Delete;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.core.Update;

/**
 * Created by Kevin L on 2/24/2016.
 * This is the main interface that uses JestDroid to access and modify data on elastic search.
 * The elastic search database we use is http://cmput301.softwareprocess.es:8080/t01/user_database
 * and http://cmput301.softwareprocess.es:8080/t01/stall_database
 */
public class ElasticSearchCtr{
    private static JestDroidClient client;

    public static class GetAccount extends AsyncTask<String, Void,Account>{
        /**
         * This is what will be excuted in a different thread
         * .@param search_string
         * @return Account object will be returned
         */
        @Override
        protected Account doInBackground(String... search_string) {
            verifyClient();
            Account account = new Account("","","");
            //start initial array list empty.
            String query = "{" +
                    "    \"query\": {" +
                    "        \"match\" :{ \"Email\":\"" + search_string[0]+ "\""+
                    "    }" +
                    "}}";
            Search search = new Search.Builder(query).addIndex("t01").addType("user_database").build();

            try {
                SearchResult execute = client.execute(search);
                if (execute.isSucceeded()){
                    account = execute.getSourceAsObject(Account.class);
                }
            } catch (IOException e) {
                Log.i("TODO", "SEARCH PROBLEMS");
            }

            return account;
        }
    }

    public static class GetStall extends AsyncTask<String, Void,ArrayList<Stalls>>{
        @Override
        /**
         * @return returns a list of stall and takes in a String[] to search
         * .@param search_string, it is a String[]. String[1] is the field and String[0] is what it
         *                       wants to match
         */
        protected ArrayList<Stalls> doInBackground(String... search_string) {
            verifyClient();
            ArrayList<Stalls> AllStall = new ArrayList<>();
            //start initial array list empty.
            String query = "{" +"\"query\": {\"bool\": {\"should\":     { \"match\": "+
                    "{ \"Status\": \"Available\" }}," +
                    "\"should\" : {\"match\":{\"Status\": \"Bidded\" }},"+
                    "\"must\" : {\"match\":{\""+search_string[1]+"\": "+"\""+search_string[0]+"\""+"}}}}}}";
            Search search = new Search.Builder(query).addIndex("t01").addType("stall_database").build();

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
    public static class GetAvailableStall extends AsyncTask<String, Void,ArrayList<Stalls>>{
        @Override
        /**
         * @return returns a list of stall and takes in a String[] to search
         * .@param search_string, it is a String[]. String[1] is the field and String[0] is what it
         *                       wants to match
         */
        protected ArrayList<Stalls> doInBackground(String... search_string) {
            verifyClient();
            ArrayList<Stalls> AllStall = new ArrayList<>();
            //start initial array list empty.
            String query = "{" +"\"query\": {\"bool\": {\"should\":     { \"match\": "+
                    "{ \""+search_string[0]+"\": \""+search_string[1]+"\" }}," +
                    "\"should\" : {\"match\":{\""+search_string[0]+"\": \""+search_string[2]+"\" }}"+ "}}}}";
            Search search = new Search.Builder(query).addIndex("t01").addType("stall_database").build();

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
    public static class GetStallBorrowing extends AsyncTask<String, Void,ArrayList<Stalls>>{
        @Override
        /**
         * @return returns a list of stall and takes in a String[] to search
         * .@param search_string, it is a String[]. String[1] is the field and String[0] is what it
         *                       wants to match
         */
        protected ArrayList<Stalls> doInBackground(String... search_string) {
            verifyClient();
            ArrayList<Stalls> AllStall = new ArrayList<>();
            //start initial array list empty.
            String query = "{" +"\"query\": {\"bool\": {\"must\":     { \"match\": "+
                    "{ \""+search_string[0]+"\": \""+search_string[1]+"\" }}"+"}}";
            Search search = new Search.Builder(query).addIndex("t01").addType("stall_database").build();

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


    public static class GetBidStall extends AsyncTask<String, Void,ArrayList<Stalls>>{
        @Override
        protected ArrayList<Stalls> doInBackground(String... search_string) {
            verifyClient();
            ArrayList<Stalls> AllStall = new ArrayList<>();
            //start initial array list empty.
            String query = "{" +"\"query\": {\"bool\": {\"must\":     { \"match\": "+
                    "{ \"Status\": \"Bidded\" }}," +
                    "\"must\": { \"match\": { \""+search_string[1]+"\": "+"\""+search_string[0]+"\""+"}}}}}";
            Search search = new Search.Builder(query).addIndex("t01").addType("stall_database").build();

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

    public static class addUser extends AsyncTask<Account, Void, Boolean>  {
        @Override
        protected Boolean doInBackground(Account... newAccount) {
            verifyClient();
            Index index = new Index.Builder(newAccount[0]).index("t01").type("user_database").build();
            try {
                DocumentResult result = client.execute(index);
                if (result.isSucceeded()) {
                    //Set the ID to newAccount that elasticsearch told me it was
                    newAccount[0].setId(result.getId());
                    return Boolean.TRUE;
                } else {
                    return Boolean.FALSE;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            /*    }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }*/
            return null;
        }


    }

    public static class deleteUser extends AsyncTask<Account, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Account... oldAccount) {
            verifyClient();

            /*verifyUserName verifyUserName = new verifyUserName();
            if(verifyUserName.doInBackground(oldAccount[0].getEmail())) {*/
                String deleteString = oldAccount[0].getEmail();

                // curl -XDELETE https://path.to.elasticsearch/group/type/$id
                // https://softwareprocess.es:9999/t01/user_database/my_user_id
                //I am not quite sure how deletion works using android notation
                Delete delete = new Delete.Builder(oldAccount[0].getId()).index("t01").type("user_database").build();
                try {
                    DocumentResult result = client.execute(delete);
                    if (result.isSucceeded()) {
                        return Boolean.TRUE;
                    } else {
                        return Boolean.FALSE;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
           // }
            return null;
        }

    }

    public static class updateUser extends AsyncTask<Account, Void, Boolean>  {


        @Override
        protected Boolean doInBackground(Account... newAccount) {
            verifyClient();

            /*verifyUserName verifyUserName = new verifyUserName();
            if (verifyUserName.doInBackground(newAccount[0].getEmail())) {*/

                Index index = new Index.Builder(newAccount[0]).index("t01").type("user_database").build();
                try {
                    DocumentResult result = client.execute(index);
                    if (result.isSucceeded()) {
                        return Boolean.TRUE;
                    } else {
                        return null;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            //}
            return Boolean.FALSE;
        }

    }


    public static class verifyUserName extends AsyncTask<String, Void, Boolean >{


        @Override
        protected Boolean doInBackground(String... userAccount) {
            verifyClient();
            String query = "{" +
                    "    \"query\": {" +
                    "        \"match\" :{ \"Email\":\"" + userAccount[0]+ "\""+
                    "    }" +
                    "}}";
            //inaccurate search, most likely looks at all properties that contain that username.
            Search search = new Search.Builder(query).addIndex("t01").addType("user_database").build();

            try {
                SearchResult execute = client.execute(search);
                if(execute.isSucceeded()){

                    Boolean returned_accounts = execute.getSourceAsObjectList(Account.class).isEmpty();
                    if(returned_accounts){
                        return Boolean.FALSE;
                    } else{
                        return Boolean.TRUE;
                    }

                } else {
                    //TODO:
                    //TODO:
                    return Boolean.FALSE;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

    }

    /**
     * This checks if an account is valid
     * .@param search_string this is the email
     * @return boolean
     */
    public static Boolean CheckAccount(String search_string){
        verifyClient();
        Boolean value = new Boolean(false);
        String query = "{" +
                "    \"query\": {" +
                "        \"match\" :{ \"Email\":\"" + search_string+ "\""+
                "    }" +
                "}}";
        Search search = new Search.Builder(query).addIndex("t01").addType("user_database").build();
        try {
            SearchResult execute = client.execute(search);
            if (execute.isSucceeded()){
                Account account = execute.getSourceAsObject(Account.class);
                String temp = account.getEmail();
                value = temp.equals(search_string);
                return value;
            } else{
                return value;
            }
        } catch (IOException e) {
            Log.i("TODO", "SEARCH PROBLEMS");
        } catch (NullPointerException e ){
            value = false;
        }
        return value;
    }

    /**
     * This class creates a stall
     */
    public static class MakeStall extends AsyncTask<Stalls, Void, Void>{
        /**
         *
         * .@param stalls stalls to be stored
         * @return nothing
         */

        @Override
        protected Void doInBackground(Stalls... stalls) {
            verifyClient();
            //Since AsyncTasks work on arrays, we need to work with arrays as well
            for (int i = 0; i < stalls.length; i++){
                Stalls stall = stalls[i];
                Index index = new Index.Builder(stall).index("t01").type("stall_database").build();
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


    public static class updateStallES extends AsyncTask<Stalls,Void,Boolean>{
        /**
         *
         * .@param stall stall with the new information
         * @return boolean depending if it is successful or not.
         */
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
                                    type("stall_database").id(stall[0].getStallID()).build());
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


    public static class DeleteStall extends AsyncTask<Stalls,Void,Boolean>{
        /**
         *
         * .@param stall stall to be deleted
         * @return boolean depending on success
         */
        @Override
        protected Boolean doInBackground(Stalls... stall) {
            verifyClient();

            try {
                DocumentResult result = client.execute(new Delete.Builder(stall[0].getStallID())
                        .index("t01")
                        .type("stall_database")
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


    public static class SearchDataBaseTask extends AsyncTask<String, Void, ArrayList<Stalls>> {
        @Override
        protected ArrayList<Stalls> doInBackground(String... params) {
            verifyClient();
            //start initial array list empty.
            ArrayList<Stalls> returnStalls = new ArrayList<Stalls>();
            String query = "{" +"\"query\": {\"bool\": {\"should\":     { \"match\": "+
                    "{ \"Status\": \"Available\" }}," +
                    "\"should\" : {\"match\":{\"Status\": \"Bidded\" }},"+
                    "\"must\": { \"match\": { \"Description\": "+"\""+params[0]+"\""+"}}}}}";
            Search search = new Search.Builder(query).addIndex("t01").addType("stall_database").build();

            try {
                SearchResult execute = client.execute(search);
                if (execute.isSucceeded()){
                   List<Stalls> stalls = execute.getSourceAsObjectList(Stalls.class);
                   returnStalls.addAll(stalls);
                }
            } catch (IOException e) {
                Log.i("TODO", "SEARCH PROBLEMS");
            }

            return returnStalls;
        }
    }

    public static class GetPendingStalls extends AsyncTask<String, Void, ArrayList<Stalls>>{
        @Override
        protected ArrayList<Stalls> doInBackground(String... search_string) {
            verifyClient();
            ArrayList<Stalls> returnedStalls = new ArrayList<Stalls>();
            List<Stalls> stalls;
            //start initial array list empty.
            String query = "{" +
                    "    \"query\": {" +
                    "        \"match\" :{ \"Bidder\":\"" + search_string[0] + "\""+
                    "    }" +
                    "}}";
            Search search = new Search.Builder(query).addIndex("t01").addType("stall_database").build();

            try {
                SearchResult execute = client.execute(search);
                if (execute.isSucceeded()){
                    stalls = execute.getSourceAsObjectList(Stalls.class);
                    returnedStalls.addAll(stalls);
                }
            } catch (IOException e) {
                Log.i("TODO", "SEARCH PROBLEMS");
            }

            return returnedStalls;
        }
    }
    //Helper function
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
