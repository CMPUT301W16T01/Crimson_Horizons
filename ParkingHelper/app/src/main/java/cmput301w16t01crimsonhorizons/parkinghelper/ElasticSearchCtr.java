package cmput301w16t01crimsonhorizons.parkinghelper;

import android.os.AsyncTask;
import android.util.Log;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.io.IOException;
import java.lang.reflect.Array;
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

    //Tries to add user and returns TRUE if one was added
    //If something goes horribly wrong it returns null
    public static Boolean addUser(Account newAccount)  {
        verifyClient();

        if(!verifyUserName(newAccount)) {

            Index index = new Index.Builder(newAccount).index("t01").type("user_database").build();
            try {
                DocumentResult result = client.execute(index);
                if (result.isSucceeded()) {
                    //Set the ID to newAccount that elasticsearch told me it was
                    newAccount.setId(result.getId());
                    return Boolean.TRUE;
                } else {
                    return Boolean.FALSE;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static Boolean verifyUserName(Account userAccount){
        String search_string = userAccount.getEmail();
        String query = "{\"fields\":[\"Email\",\"CellPhone\",\"WorkPhone\"],\"query\":{\"match\":{\"Email\"," +
                search_string + "}}}";
        //inaccurate search, most likely looks at all properties that contain that username.
        Search search = new Search.Builder(query).addIndex("t01").addType("user_database").build();

        try {
            SearchResult execute = client.execute(search);
            if(execute.isSucceeded()){

                List<Account> returned_accounts = execute.getSourceAsObjectList(Account.class);
                if(returned_accounts.isEmpty()){
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
    public static class GetAccount extends AsyncTask<String, Void,Account>{
        @Override
        protected Account doInBackground(String... search_string) {
            verifyClient();
            Account account = null;
            //start initial array list empty.
            String query = "{" +
                    "    \"query\": {" +
                    "        \"match\" :{ \"Email\":\"" + search_string+ "\""+
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
            }
            return value;
        }

    public static class MakeDatabase extends AsyncTask<Account, Void, Void>{

        @Override
        protected Void doInBackground(Account... accounts) {
            verifyClient();
            //Since AsyncTasks work on arrays, we need to work with arrays as well
            for (int i = 0; i < accounts.length; i++){
                Account account = accounts[i];
                Index index = new Index.Builder(account).index("t01").type("user_database").build();
                try {
                    DocumentResult result = client.execute(index);
                    if (result.isSucceeded()){
                        //Set Id for tweet, can find and edit in elastic search
                        account.setId(result.getId());
                    }
                    //Can also use get id,get index,get type
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            return null;
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
