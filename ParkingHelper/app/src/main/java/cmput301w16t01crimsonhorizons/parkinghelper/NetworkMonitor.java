package cmput301w16t01crimsonhorizons.parkinghelper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by Kevin L on 3/23/2016.
 */

public class NetworkMonitor extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, final Intent intent) {
        OfflineIO io = new OfflineIO();
        ArrayList<Stalls> updates_add = io.LoadStallsToAdd(context);
        ArrayList<Stalls> updates_edit = io.LoadStallsUpdate(context);
        if(checkInternet(context)) {
            OfflineIO io_update = new OfflineIO();
            while(updates_add.size()>0) {
                ElasticSearchCtr.MakeStall makeStall = new ElasticSearchCtr.MakeStall();
                makeStall.execute(updates_add.get(0));
                try {
                    makeStall.get();
                    ArrayList<Stalls> temp = CurrentStalls.getCurrentStalls();
                    temp.add(updates_add.get(0));
                    io_update.StoreStall(temp, context);
                    CurrentStalls.setCurrentStalls(temp);
                    updates_add.remove(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
            io.deleteAddFile(context);
            while (updates_edit.size()>0){
                ElasticSearchCtr.updateStallES updateStallES = new ElasticSearchCtr.updateStallES();
                updateStallES.execute(updates_edit.get(0));
                Boolean check = false;
                try {
                    check = updateStallES.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                if(check){
                    ArrayList<Stalls>temp = new ArrayList<>();
                    ElasticSearchCtr.GetStall getStall = new ElasticSearchCtr.GetStall();
                    String[]query = new String[2];
                    query[0] = CurrentAccount.getAccount().getEmail();
                    query[1] = "Owner";
                    try {
                        getStall.execute(query);
                        temp = getStall.get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    io_update.StoreStall(temp,context);
                    CurrentStalls.setCurrentStalls(temp);
                    updates_edit.remove(0);
                } else {
                    Toast.makeText(context,"problem with updates_add",Toast.LENGTH_SHORT).show();
                }
            }
            io.deleteUpdateFile(context);
            Toast.makeText(context, "Network Available, pushing changes", Toast.LENGTH_LONG).show();
        }
    }
    boolean checkInternet(Context context) {
        ServiceManager serviceManager = new ServiceManager(context);
        if (serviceManager.isNetworkAvailable()) {
            return true;
        } else {
            return false;
        }
    }
    public class ServiceManager extends ContextWrapper {

        public ServiceManager(Context base) {
            super(base);
        }

        public boolean isNetworkAvailable() {
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                return true;
            }
            return false;
        }

    }
}


