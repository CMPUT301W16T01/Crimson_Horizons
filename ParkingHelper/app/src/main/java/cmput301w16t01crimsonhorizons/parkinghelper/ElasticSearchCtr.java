package cmput301w16t01crimsonhorizons.parkinghelper;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.searchbox.client.JestResult;
import io.searchbox.core.Delete;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.core.SearchScroll;
import io.searchbox.core.Update;
import io.searchbox.params.Parameters;

/**
 * Created by Kevin L on 2/24/2016.
 * This is the main interface that uses JestDroid to access and modify data on elastic search.
 * The elastic search database we use is http://cmput301.softwareprocess.es:8080/t01/user_database
 * and http://cmput301.softwareprocess.es:8080/t01/stall_database
 */
public class ElasticSearchCtr{
    private static JestDroidClient client;
    /**
     * This retrieve account by email
     * .@param search_string
     * @return Account object will be returned
     */
    public static class GetAccount extends AsyncTask<String, Void,Account>{

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
    /**
     * @return returns a list of stall and takes in a String[] to search
     * .@param search_string, it is a String[]. String[1] is the field and String[0] is what it
     *                       wants to match
     */
    public static class GetStall extends AsyncTask<String, Void,ArrayList<Stalls>>{
        @Override

        protected ArrayList<Stalls> doInBackground(String... search_string) {
            verifyClient();
            ArrayList<Stalls> AllStall = new ArrayList<>();
            //start initial array list empty.
            String query = "{" +"\"query\": {\"bool\": {\"should\":     { \"match\": "+
                    "{ \"Status\": \"Available\" }}," +
                    "\"should\" : {\"match\":{\"Status\": \"Bidded\" }},"+
                    "\"must\" : {\"match\":{\""+search_string[1]+"\": "+"\""+search_string[0]+"\""+"}}}}}";
            Search search = new Search.Builder(query).addIndex("t01").addType("stall_database").setParameter(Parameters.SIZE, 10)
                    .setParameter(Parameters.SCROLL, "1m")
                    .build();
            try {
                JestResult execute = client.execute(search);
                String scrollId = execute.getJsonObject().get("_scroll_id").getAsString();
                if (execute.isSucceeded()) {
                    List<Stalls> returned_stalls = execute.getSourceAsObjectList(Stalls.class);
                    AllStall.addAll(returned_stalls);
                }
                while (true) {

                    SearchScroll scroll = new SearchScroll.Builder(scrollId, "1m")
                            .setParameter(Parameters.SIZE, 10).build();

                    execute = client.execute(scroll);
                    if (execute.isSucceeded()) {
                        List<Stalls> returned_stalls = execute.getSourceAsObjectList(Stalls.class);
                        if (returned_stalls.size()==0||returned_stalls==null){
                            break;
                        } else {
                            AllStall.addAll(returned_stalls);
                            scrollId = execute.getJsonObject().getAsJsonPrimitive("_scroll_id").getAsString();
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return AllStall;
        }
    }

    /**
     * Returns a list of stalls matching 2 fields.
     * Here it match status with bidded or available
     */
    public static class GetAvailableStall extends AsyncTask<String, Void,ArrayList<Stalls>> {
        @Override
        protected ArrayList<Stalls> doInBackground(String... search_string) {
            verifyClient();
            ArrayList<Stalls> AllStall = new ArrayList<>();
            //start initial array list empty.
            String query = "{" + "\"query\": {\"bool\": {\"should\":     { \"match\": " +
                    "{ \"" + search_string[0] + "\": \"" + search_string[1] + "\" }}," +
                    "\"should\" : {\"match\":{\"" + search_string[0] + "\": \"" + search_string[2] + "\" }}" + "}}}}";
            Search search = new Search.Builder(query).addIndex("t01").addType("stall_database").setParameter(Parameters.SIZE, 10)
                    .setParameter(Parameters.SCROLL, "1m")
                    .build();
            try {
                JestResult execute = client.execute(search);
                String scrollId = execute.getJsonObject().get("_scroll_id").getAsString();
                if (execute.isSucceeded()) {
                    List<Stalls> returned_stalls = execute.getSourceAsObjectList(Stalls.class);
                    AllStall.addAll(returned_stalls);
                }
                while (true) {

                    SearchScroll scroll = new SearchScroll.Builder(scrollId, "1m")
                            .setParameter(Parameters.SIZE, 10).build();

                    execute = client.execute(scroll);
                    if (execute.isSucceeded()) {
                        List<Stalls> returned_stalls = execute.getSourceAsObjectList(Stalls.class);
                        if (returned_stalls.size()==0||returned_stalls==null){
                            break;
                        } else {
                            AllStall.addAll(returned_stalls);
                            scrollId = execute.getJsonObject().getAsJsonPrimitive("_scroll_id").getAsString();
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return AllStall;
        }
    }

    /**
     * Returns stalls that matches Borrower with email
     */
    public static class GetStallBorrowing extends AsyncTask<String, Void,ArrayList<Stalls>>{
        @Override
        protected ArrayList<Stalls> doInBackground(String... search_string) {
            verifyClient();
            ArrayList<Stalls> AllStall = new ArrayList<>();
            //start initial array list empty.
            String query = "{" +"\"query\": { \"match\": "+
                    "{ \""+search_string[0]+"\": \""+search_string[1]+"\" }}}";

            Search search = new Search.Builder(query).addIndex("t01").addType("stall_database").setParameter(Parameters.SIZE, 10)
                    .setParameter(Parameters.SCROLL, "1m")
                    .build();
            try {
                JestResult execute = client.execute(search);
                String scrollId = execute.getJsonObject().get("_scroll_id").getAsString();
                if (execute.isSucceeded()) {
                    List<Stalls> returned_stalls = execute.getSourceAsObjectList(Stalls.class);
                    AllStall.addAll(returned_stalls);
                }
                while (true) {

                    SearchScroll scroll = new SearchScroll.Builder(scrollId, "1m")
                            .setParameter(Parameters.SIZE, 10).build();

                    execute = client.execute(scroll);
                    if (execute.isSucceeded()) {
                        List<Stalls> returned_stalls = execute.getSourceAsObjectList(Stalls.class);
                        if (returned_stalls.size()==0||returned_stalls==null){
                            break;
                        } else {
                            AllStall.addAll(returned_stalls);
                            scrollId = execute.getJsonObject().getAsJsonPrimitive("_scroll_id").getAsString();
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return AllStall;
/*            try {
                SearchResult execute = client.execute(search);
                if (execute.isSucceeded()){
                    List<Stalls> returned_stalls = execute.getSourceAsObjectList(Stalls.class);
                    AllStall.addAll(returned_stalls);
                }
            } catch (IOException e) {
                Log.i("TODO", "SEARCH PROBLEMS");
            }

            return AllStall;*/
        }
    }

    /**
     * returns stalls that is bidded that matches owner with email
     */
    public static class GetBidStall extends AsyncTask<String, Void,ArrayList<Stalls>>{
        @Override
        protected ArrayList<Stalls> doInBackground(String... search_string) {
            verifyClient();
            ArrayList<Stalls> AllStall = new ArrayList<>();
            //start initial array list empty.
            String query = "{" +"\"query\": {\"bool\": {\"must\":     { \"match\": "+
                    "{ \"Status\": \"Bidded\" }}," +
                    "\"must\": { \"match\": { \""+search_string[1]+"\": "+"\""+search_string[0]+"\""+"}}}}}";
            Search search = new Search.Builder(query).addIndex("t01").addType("stall_database").setParameter(Parameters.SIZE, 10)
                    .setParameter(Parameters.SCROLL, "1m")
                    .build();
            try {
                JestResult execute = client.execute(search);
                String scrollId = execute.getJsonObject().get("_scroll_id").getAsString();
                if (execute.isSucceeded()) {
                    List<Stalls> returned_stalls = execute.getSourceAsObjectList(Stalls.class);
                    AllStall.addAll(returned_stalls);
                }
                while (true) {

                    SearchScroll scroll = new SearchScroll.Builder(scrollId, "1m")
                            .setParameter(Parameters.SIZE, 10).build();

                    execute = client.execute(scroll);
                    if (execute.isSucceeded()) {
                        List<Stalls> returned_stalls = execute.getSourceAsObjectList(Stalls.class);
                        if (returned_stalls.size()==0||returned_stalls==null){
                            break;
                        } else {
                            AllStall.addAll(returned_stalls);
                            scrollId = execute.getJsonObject().getAsJsonPrimitive("_scroll_id").getAsString();
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return AllStall;
            /*Search search = new Search.Builder(query).addIndex("t01").addType("stall_database").build();

            try {
                SearchResult execute = client.execute(search);
                if (execute.isSucceeded()){
                    List<Stalls> returned_stalls = execute.getSourceAsObjectList(Stalls.class);
                    AllStall.addAll(returned_stalls);
                }
            } catch (IOException e) {
                Log.i("TODO", "SEARCH PROBLEMS");
            }

            return AllStall;*/
        }
    }

    /**
     * This returns stalls that has status borrowed and Owner with email
     * Stalls that the user has lended out.
     */
    public static class GetLendedStall extends AsyncTask<String, Void,ArrayList<Stalls>>{
        @Override
        protected ArrayList<Stalls> doInBackground(String... search_string) {
            verifyClient();
            ArrayList<Stalls> AllStall = new ArrayList<>();
            //start initial array list empty.
            String query = "{" +"\"query\": {\"bool\": {\"must\":     { \"match\": "+
                    "{ \"Status\": \"Borrowed\" }}," +
                    "\"must\": { \"match\": { \""+search_string[1]+"\": "+"\""+search_string[0]+"\""+"}}}}}";
            Search search = new Search.Builder(query).addIndex("t01").addType("stall_database").setParameter(Parameters.SIZE, 10)
                    .setParameter(Parameters.SCROLL, "1m")
                    .build();
            try {
                JestResult execute = client.execute(search);
                String scrollId = execute.getJsonObject().get("_scroll_id").getAsString();
                if (execute.isSucceeded()) {
                    List<Stalls> returned_stalls = execute.getSourceAsObjectList(Stalls.class);
                    AllStall.addAll(returned_stalls);
                }
                while (true) {

                    SearchScroll scroll = new SearchScroll.Builder(scrollId, "1m")
                            .setParameter(Parameters.SIZE, 10).build();

                    execute = client.execute(scroll);
                    if (execute.isSucceeded()) {
                        List<Stalls> returned_stalls = execute.getSourceAsObjectList(Stalls.class);
                        if (returned_stalls.size()==0||returned_stalls==null){
                            break;
                        } else {
                            AllStall.addAll(returned_stalls);
                            scrollId = execute.getJsonObject().getAsJsonPrimitive("_scroll_id").getAsString();
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return AllStall;
            /*Search search = new Search.Builder(query).addIndex("t01").addType("stall_database").build();

            try {
                SearchResult execute = client.execute(search);
                if (execute.isSucceeded()){
                    List<Stalls> returned_stalls = execute.getSourceAsObjectList(Stalls.class);
                    AllStall.addAll(returned_stalls);
                }
            } catch (IOException e) {
                Log.i("TODO", "SEARCH PROBLEMS");
            }

            return AllStall;*/
        }
    }

    /**
     * creates a user and add to elastic search
     */
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

    /**
     * delete a user from elastic search
     */
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

    /**
     * update account information
     */
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

    /**
     * verify user name using AsyncTask.
     */
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
     * Verify account not usin AsyncTask, used in LoginActivity, as it calls this in a method
     * that extends asynctask. Everything is same as verifyUserName
     * @see cmput301w16t01crimsonhorizons.parkinghelper.ElasticSearchCtr.verifyUserName
     * .@param search_string this is the email
     * @return boolean
     */
    public static Boolean CheckAccount(String search_string){
        verifyClient();
        Boolean value = false;
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

    /**
     *
     * .@param stall stall with the new information
     * @return boolean depending if it is successful or not.
     */
    public static class updateStallES extends AsyncTask<Stalls,Void,Boolean>{

        @Override
        protected Boolean doInBackground(Stalls... stall) {
            verifyClient();
            String status = stall[0].getStatus();
            String Description = stall[0].getDescription();
            String Owner = stall[0].getOwner();
            String Borrower = stall[0].getBorrower();
            Double[] location = stall[0].getLocation();
            Bitmap thumbnail = stall[0].getThumbnail();
            String thumbnailBase64 = stall[0].getThumbnailBase64();
            String doc="";
            try {
                doc = "{" +
                        "\"doc\": { \"Status\": " + "\"" + status + "\", " +
                        " \"Description\": " + "\"" + Description + "\", " +
                        " \"Owner\": " + "\"" + Owner + "\", " +
                        " \"location\": [" + location[0].toString() + "," +
                        location[1].toString() + "]," +
                        " \"Borrower\": " + "\"" + Borrower + "\"" + "," +
                        " \"thumbnail\": " + "\"" + thumbnail.toString() + "\"" + "," +
                        " \"thumbnailBase64\": " + "\"" + thumbnailBase64 + "\"" + "}" +
                        "}";
            }catch(NullPointerException e ){
                doc = "{" +
                        "\"doc\": { \"Status\": " + "\"" + status + "\", " +
                        " \"Description\": " + "\"" + Description + "\", " +
                        " \"Owner\": " + "\"" + Owner + "\", " +
                        " \"location\": [" + location[0].toString() + "," +
                        location[1].toString() + "]," +
                        " \"Borrower\": " + "\"" + Borrower + "\"" + "," +
                        " \"thumbnail\": " + "\"" + "" + "\"" + "," +
                        " \"thumbnailBase64\": " + "\"" + "" + "\"" + "}" +
                        "}";
            }
            try {
                DocumentResult result = client.execute(new Update.Builder(doc).index("t01").
                        type("stall_database").id(stall[0].getStallID()).build());
                return result.isSucceeded();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    /**
     *
     * .@param bids bids to be stored
     * @return nothing
     */
    public static class MakeBid extends AsyncTask<Bid,Void,Void>{

        @Override
        protected Void doInBackground(Bid... bids) {
            verifyClient();
            //Since AsyncTasks work on arrays, we need to work with arrays as well
            for (int i = 0; i < bids.length; i++){
                Bid bid = bids[i];
                Index index = new Index.Builder(bid).index("t01").type("bid_database").build();
                try {
                    DocumentResult result = client.execute(index);
                    if (result.isSucceeded()){
                        //Set Id for tweet, can find and edit in elastic search
                        bid.setBidID(result.getId());
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
     *
     * .@param bid bid with the new information
     * @return boolean depending if it is successful or not.
     */
    public static class UpdateBid extends AsyncTask<Bid,Void,Boolean>{

        @Override
        protected Boolean doInBackground(Bid... bid) {
            verifyClient();
            String bidder = bid[0].Bidder();
            Double bidAmount = bid[0].BidAmount();
            String stall = bid[0].BidStallID();
            String doc="";
            doc = "{" +
                    "\"doc\": { \"bidder\": " + "\"" + bidder + "\", " +
                    " \"bidAmount\": " + "\"" + bidAmount.toString() + "\", " +
                    " \"bidStallID\": " + "\"" + stall + "\", " +
                    "}";
            try {
                DocumentResult result = client.execute(new Update.Builder(doc).index("t01").
                        type("bid_database").id(bid[0].getBidID()).build());
                return result.isSucceeded();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
    }
    /**
     * @return returns a list of bids
     * .@param search_strings, it is an array of arrays of Strings. Each array is
     * two Strings long, 0 = key, 1 = value, and specifies a search criterion
     */
    public static class GetBid extends AsyncTask<String[], Void,ArrayList<Bid>>{
        @Override

        protected ArrayList<Bid> doInBackground(String[]... search_strings) {
            verifyClient();
            ArrayList<Bid> BidsResult = new ArrayList<>();
            //start initial array list empty.
            String query = "{" +"\"query\": {\"bool\": {";
            for (int i = 0; i < search_strings.length; i++) {
                if (i != 0) { query += ", "; }
                query += "\"must\" : {\"match\":{" +
                        "\""+search_strings[i][0]+"\": "+"\""+search_strings[i][1]+"\""+"}}";
            }
            query += "}}}";
            Search search = new Search.Builder(query).addIndex("t01").addType("bid_database").build();

            try {
                SearchResult execute = client.execute(search);
                if (execute.isSucceeded()){
                    List<Bid> returned_bids = execute.getSourceAsObjectList(Bid.class);
                    BidsResult.addAll(returned_bids);
                }
            } catch (IOException e) {
                Log.i("TODO", "SEARCH PROBLEMS");
            }

            return BidsResult;
        }}
    /**
     *
     * .@param bid bid to be deleted
     * @return boolean depending on success
     */
    public static class DeleteBid extends AsyncTask<Bid,Void,Boolean> {

        @Override
        protected Boolean doInBackground(Bid... bid) {
            verifyClient();
            try {
                DocumentResult result = client.execute(new Delete.Builder(bid[0].getBidID())
                        .index("t01")
                        .type("bid_database")
                        .build());
                if (result.isSucceeded()) {
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
     *
     * .@param stall stall to be deleted
     * @return boolean depending on success
     */
    public static class DeleteStall extends AsyncTask<Stalls,Void,Boolean>{

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

    /**
     *
     * .@param reviews reviews to be stored
     * @return nothing
     */
    public static class MakeReview extends AsyncTask<Review,Void,Void>{

        @Override
        protected Void doInBackground(Review... reviews) {
            verifyClient();
            //Since AsyncTasks work on arrays, we need to work with arrays as well
            for (int i = 0; i < reviews.length; i++){
                Review review = reviews[i];
                Index index = new Index.Builder(review).index("t01").type("review_database").build();
                try {
                    DocumentResult result = client.execute(index);
                    if (result.isSucceeded()){
                        //Set Id for tweet, can find and edit in elastic search
                        review.setReviewID(result.getId());
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
     * @return returns a list of reviews
     * .@param search_strings, it is an array of arrays of Strings. Each array is
     * two Strings long, 0 = key, 1 = value, and specifies a search criterion
     */
    public static class GetReview extends AsyncTask<String[], Void,ArrayList<Review>>{
        @Override

        protected ArrayList<Review> doInBackground(String[]... search_strings) {
            verifyClient();
            ArrayList<Review> ReviewsResult = new ArrayList<>();
            //start initial array list empty.
            String query = "{" +"\"query\": {\"bool\": {";
            for (int i = 0; i < search_strings.length; i++) {
                if (i != 0) { query += ", "; }
                query += "\"must\" : {\"match\":{" +
                        "\""+search_strings[i][0]+"\": "+"\""+search_strings[i][1]+"\""+"}}";
            }
            query += "}}}";
            Search search = new Search.Builder(query).addIndex("t01").addType("review_database").build();

            try {
                SearchResult execute = client.execute(search);
                if (execute.isSucceeded()){
                    List<Review> returned_reviews = execute.getSourceAsObjectList(Review.class);
                    ReviewsResult.addAll(returned_reviews);
                }
            } catch (IOException e) {
                Log.i("TODO", "SEARCH PROBLEMS");
            }

            return ReviewsResult;
        }}
    /**
     *
     * .@param review review to be deleted
     * @return boolean depending on success
     */
    public static class DeleteReview extends AsyncTask<Review,Void,Boolean> {

        @Override
        protected Boolean doInBackground(Review... review) {
            verifyClient();
            try {
                DocumentResult result = client.execute(new Delete.Builder(review[0].getReviewID())
                        .index("t01")
                        .type("bid_database")
                        .build());
                if (result.isSucceeded()) {
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
     *
     * .@param params a string of keywords found in the description of the parking stall the
     *               user is looking for
     * @return returnStalls a list of stall objects with at least one of the words, form
     *          the params argument in their description
     */
    public static class SearchDataBaseTask extends AsyncTask<String, Void, ArrayList<Stalls>> {

        @Override
        protected ArrayList<Stalls> doInBackground(String... params) {
            verifyClient();
            //start initial array list empty.
            ArrayList<Stalls> returnStalls = new ArrayList<Stalls>();
            String query = "{" +"\"query\": {\"bool\": {\"should\":     { \"match\": "+
                    "{ \"Status\": \"Available\" }}," +
                    "\"should\" : {\"match\":{\"Status\": \"Bidded\" }},"+
                    "\"must_not\" : {\"match\":{\"Status\": \"Borrowed\" }},"+
                    "\"must\": { \"match\": { \"Description\": "+"\""+params[0]+"\""+"}}}}}";
            Search search = new Search.Builder(query).addIndex("t01").addType("stall_database").setParameter(Parameters.SIZE, 10)
                    .setParameter(Parameters.SCROLL, "1m")
                    .build();
            try {
                JestResult execute = client.execute(search);
                String scrollId = execute.getJsonObject().get("_scroll_id").getAsString();
                if (execute.isSucceeded()) {
                    List<Stalls> another1 = execute.getSourceAsObjectList(Stalls.class);
                    returnStalls.addAll(another1);
                }
                while (true) {

                    SearchScroll scroll = new SearchScroll.Builder(scrollId, "1m")
                            .setParameter(Parameters.SIZE, 10).build();

                    execute = client.execute(scroll);
                    if (execute.isSucceeded()) {
                        List<Stalls> another1 = execute.getSourceAsObjectList(Stalls.class);
                        if (another1.size()==0||another1==null){
                            break;
                        } else {
                            returnStalls.addAll(another1);
                            scrollId = execute.getJsonObject().getAsJsonPrimitive("_scroll_id").getAsString();
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return returnStalls;
            /*Search search = new Search.Builder(query).addIndex("t01").addType("stall_database").build();

            try {
                SearchResult execute = client.execute(search);
                if (execute.isSucceeded()) {
                    List<String> stalls = execute.getSourceAsStringList();
                    List<Stalls> another1 = execute.getSourceAsObjectList(Stalls.class);
                    String temp = "hi";
                    returnStalls.addAll(another1);

                }
            } catch (IOException e) {
                Log.i("TODO", "SEARCH PROBLEMS");
            }

            return returnStalls;*/
        }
    }

    /**
     * Helper function for Jest Droid
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
