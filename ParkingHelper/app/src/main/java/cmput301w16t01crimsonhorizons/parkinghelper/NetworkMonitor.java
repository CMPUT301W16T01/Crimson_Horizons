package cmput301w16t01crimsonhorizons.parkinghelper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by Kevin L on 3/23/2016.
 */
public class NetworkMonitor extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, final Intent intent) {
        if(checkInternet(context)) {

            OfflineIO io = new OfflineIO();
            Account user = CurrentAccount.getAccount();
            ElasticSearchCtr.GetAccount getAccount = new ElasticSearchCtr.GetAccount();
            ElasticSearchCtr.GetStall getStall = new ElasticSearchCtr.GetStall();
            String[] query = new String[2];
            query[0] = user.getEmail();
            query[1] = "Owner";
            getStall.execute(query);
            getAccount.execute(user.getEmail());
            try {
                Account account = getAccount.get();
                ArrayList<Stalls> stallsArrayList = getStall.get();

                CurrentStalls.setCurrentStalls(stallsArrayList);
                io.StoreStall(stallsArrayList,context);

                CurrentAccount.setAccount(account);
                io.StoreUser(account,context);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            Toast.makeText(context, "Network Available Do operations", Toast.LENGTH_LONG).show();
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


