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
 */
public class OfflineIO {
    // It sets the constant for the file name that it will always write to.

    private static final String FILENAME ="database";
    public OfflineIO(){};
    // This just deletes the file
    public void delete(Context context){
        context.deleteFile(FILENAME);
    }

    // This returns an array of strings of each entries.
    public ArrayList<String> LoadFromFile(Context context){
        ArrayList<String> AllEntries= new ArrayList<String>();
        ArrayList<String> temp = new ArrayList<String>();
        Gson gson = new Gson();
        try {
            FileInputStream fis = context.openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Type ListType = new TypeToken<ArrayList<String>>(){}.getType();
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
    // This write to the file using Gson and Json.
    // It takes in the array as input and the context, as it needs it to write.
    public void CurrentUser(String email,Context context){
        Gson gson = new Gson();
        try{
            // It completely rewrites the file.
            this.delete(context);
            FileOutputStream fos = context.openFileOutput(FILENAME,Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            gson.toJson(email,out);
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
}
