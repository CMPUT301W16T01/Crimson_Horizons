package cmput301w16t01crimsonhorizons.parkinghelper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Kevin L on 3/25/2016.
 */
public class NotificationAdapter extends ArrayAdapter<NotificationObject>{
    private int Layout;
    public NotificationAdapter(Context context, int resource, List<NotificationObject> objects) {
        super(context, resource, objects);
        Layout=resource;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NotificationHolder mainHolder = null;
        final NotificationObject notification = getItem(position);
        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(Layout,parent,false);
            final NotificationHolder viewHolder = new NotificationHolder();
            viewHolder.Bidder=(TextView)convertView.findViewById(R.id.NotificationBidder);
            viewHolder.Date = (TextView)convertView.findViewById(R.id.NotificationDate);
            viewHolder.BidAmt = (TextView)convertView.findViewById(R.id.NotificationBidAmt);
            viewHolder.Delete = (Button)convertView.findViewById(R.id.DeleteNotificationBtn);
            viewHolder.Picture = (ImageView)convertView.findViewById(R.id.NotificationPicture);
            viewHolder.Delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NotificationElasticSearch.DeleteNotification deleteNotification = new NotificationElasticSearch.DeleteNotification();
                    deleteNotification.execute(notification);
                    Toast.makeText(getContext(), "Deleted!!", Toast.LENGTH_SHORT).show();
                }
            });
            viewHolder.Bidder.setText(notification.getBidder());
            viewHolder.Date.setText(notification.getDate());
            viewHolder.BidAmt.setText(notification.getBidAmt());
            //viewHolder.Picture.setImageBitmap(notification.getOnotification.getStallID());
            convertView.setTag(viewHolder);
        }
        else{
            mainHolder = (NotificationHolder)convertView.getTag();
            mainHolder.Bidder.setText(notification.getBidder());
            mainHolder.BidAmt.setText(notification.getBidAmt());
            mainHolder.Date.setText(notification.getDate());
        }
        return convertView;
    }

    public class NotificationHolder {
        TextView Bidder;
        TextView BidAmt;
        TextView Date;
        Button Delete;
        ImageView Picture;
    }

}
