package cmput301w16t01crimsonhorizons.parkinghelper;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by kliang on 3/22/16.
 * IO for offline data
 */
public class OfflineIO {
    // It sets the constant for the file name that it will always write to.

    //keeps track of the user that is last logged in
    private static final String USER_FILE ="user_file";
    //Keeps track of the stalls the user owns
    private static final String STALL_FILE ="stall_file";
    //Keeps track of any adding command used and stalls that is to be added
    private static final String STALL_ADD = "stall_add_file";
    //keeps track of any update done and the stalls for update
    private static final String STALL_UPDATE = "stall_update_file";
    public OfflineIO(){};

    /**
     * This loads the user name of last login
     * @param context
     * @return
     */
    public Account LoadUser(Context context){
        Account AllEntries= null;
        Gson gson = new Gson();
        try {
            FileInputStream fis = context.openFileInput(USER_FILE);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Type ListType = new TypeToken<Account>(){}.getType();
            AllEntries=gson.fromJson(in,ListType);
            in.close();
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return AllEntries;
    }


    /**
     * This write to the file using Gson and Json.
     * It takes in the array as input and the context, as it needs it to write.
     * It stores the current user name
     * @param account
     * @param context
     */
    public void StoreUser(Account account,Context context){
        Gson gson = new Gson();
        try{
            // It completely rewrites the file.
            this.deleteUserFile(context);
            FileOutputStream fos = context.openFileOutput(USER_FILE,Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            gson.toJson(account, out);
            out.flush();
            out.close();
            fos.close();
        }catch(FileNotFoundException e){
            e.printStackTrace();
            throw new RuntimeException("Error in SaveToFile");

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error IOexception");
        }
    }

    /**
     * Returns the stalls this user owns
     * @param context
     * @return
     */
    public ArrayList<Stalls> LoadStalls(Context context){
        ArrayList<Stalls> AllEntries= new ArrayList<Stalls>();
        ArrayList<Stalls> temp = new ArrayList<Stalls>();
        Gson gson = new Gson();
        try {
            FileInputStream fis = context.openFileInput(STALL_FILE);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Type ListType = new TypeToken<ArrayList<Stalls>>(){}.getType();
            temp=gson.fromJson(in,ListType);
            in.close();
            fis.close();
            // This is to ensure whatever gson returns from the file, it is not null.
            //If it is, then it means the file was empty. In that case, it will return the
            // array AllEntries.
            if (temp==null){
                return AllEntries;
            }
            // If the array is not empty it copies over temp onto AllEntries and returns that in the
            // end.
            else {
                AllEntries.addAll(temp);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return AllEntries;
    }

    /**
     * stores the stalls this user currently owns.
     * @param stalls
     * @param context
     */
    public void StoreStall(ArrayList<Stalls> stalls,Context context){
        Gson gson = new Gson();
        try{
            // It completely rewrites the file.
            this.deleteStallFile(context);
            FileOutputStream fos = context.openFileOutput(STALL_FILE,Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            gson.toJson(stalls, out);
            out.flush();
            out.close();
            fos.close();
        }catch(FileNotFoundException e){
            e.printStackTrace();
            throw new RuntimeException("Error in SaveToFile");

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error IOexception");
        }
    }

    /**
     * stores the stalls that the use rwants to add when offline
     * @param stalls
     * @param context
     */
    public void StoreStallsToAdd(ArrayList<Stalls> stalls,Context context){
        Gson gson = new Gson();
        try{
            // It completely rewrites the file.
            this.deleteAddFile(context);
            FileOutputStream fos = context.openFileOutput(STALL_ADD,Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            gson.toJson(stalls, out);
            out.flush();
            out.close();
            fos.close();
        }catch(FileNotFoundException e){
            e.printStackTrace();
            throw new RuntimeException("Error in SaveToFile");

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error IOexception");
        }
    }

    /**
     * stores the stalls the user wants to update when offline
     * @param stalls
     * @param context
     */
    public void StoreStallsToUpdate(ArrayList<Stalls> stalls,Context context){
        Gson gson = new Gson();
        try{
            // It completely rewrites the file.
            this.deleteUpdateFile(context);
            FileOutputStream fos = context.openFileOutput(STALL_UPDATE,Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            gson.toJson(stalls, out);
            out.flush();
            out.close();
            fos.close();
        }catch(FileNotFoundException e){
            e.printStackTrace();
            throw new RuntimeException("Error in SaveToFile");

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error IOexception");
        }
    }

    /**
     * loads the stalls the user wants to add when offline
     * @param context
     * @return
     */
    public ArrayList<Stalls> LoadStallsToAdd(Context context){
        ArrayList<Stalls> AllEntries= new ArrayList<Stalls>();
        ArrayList<Stalls> temp = new ArrayList<Stalls>();
        Gson gson = new Gson();
        try {
            FileInputStream fis = context.openFileInput(STALL_ADD);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Type ListType = new TypeToken<ArrayList<Stalls>>(){}.getType();
            temp=gson.fromJson(in,ListType);
            in.close();
            fis.close();
            // This is to ensure whatever gson returns from the file, it is not null.
            //If it is, then it means the file was empty. In that case, it will return the
            // array AllEntries.
            if (temp==null){
                return AllEntries;
            }
            // If the array is not empty it copies over temp onto AllEntries and returns that in the
            // end.
            else {
                AllEntries.addAll(temp);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return AllEntries;
    }

    /**
     * loads the stalls the user wants to update when offline
     * @param context
     * @return
     */
    public ArrayList<Stalls> LoadStallsUpdate(Context context){
        ArrayList<Stalls> AllEntries= new ArrayList<Stalls>();
        ArrayList<Stalls> temp = new ArrayList<Stalls>();
        Gson gson = new Gson();
        try {
            FileInputStream fis = context.openFileInput(STALL_UPDATE);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Type ListType = new TypeToken<ArrayList<Stalls>>(){}.getType();
            temp=gson.fromJson(in,ListType);
            in.close();
            fis.close();
            // This is to ensure whatever gson returns from the file, it is not null.
            //If it is, then it means the file was empty. In that case, it will return the
            // array AllEntries.
            if (temp==null){
                return AllEntries;
            }
            // If the array is not empty it copies over temp onto AllEntries and returns that in the
            // end.
            else {
                AllEntries.addAll(temp);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return AllEntries;
    }

    /**
     * delete commands for the file
     * @param context
     */
    public void deleteStallFile(Context context){
        context.deleteFile(STALL_FILE);
    }


    public void deleteUserFile(Context context){
        context.deleteFile(USER_FILE);
    }
    public void deleteUpdateFile(Context context){
        context.deleteFile(STALL_UPDATE);
    }
    public void deleteAddFile(Context context){
        context.deleteFile(STALL_ADD);
    }
}
