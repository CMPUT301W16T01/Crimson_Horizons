package cmput301w16t01crimsonhorizons.parkinghelper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Kevin L on 2/29/2016.
 *
 * This is the adapter needed to display the list of stalls with the proper format
 * For Account activity
 * @see CustomLstAdapter This is similar.
 */
public class AdapterYourBids extends ArrayAdapter<Bid>{
    private int Layout;
    public AdapterYourBids(Context context, int resource, List<Bid> objects) {
        super(context, resource, objects);
        Layout=resource;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        EditStallViewHolder mainHolder = null;
        Bid bid = getItem(position);
        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(Layout,parent,false);
            EditStallViewHolder viewHolder = new EditStallViewHolder();
            viewHolder.Owner=(TextView)convertView.findViewById(R.id.StallNameEditStallV);
            viewHolder.Description = (TextView)convertView.findViewById(R.id.DescriptionEditStallV);
            viewHolder.BidAmt = (TextView)convertView.findViewById(R.id.BidAmt);
            viewHolder.Name = (TextView)convertView.findViewById(R.id.Username);
            viewHolder.Picture = (ImageView)convertView.findViewById(R.id.PictureEditStallV);
            viewHolder.BidAmt.setText(bid.BidAmount().toString());
            viewHolder.Name.setText(CurrentAccount.getAccount().getEmail());
            ElasticSearchCtr.GetStall getStall = new ElasticSearchCtr.GetStall();
            String[] query = new String[2];
            query[1]="_id";
            query[0]=bid.BidStallID();
            getStall.execute(query);
            try {
                ArrayList<Stalls>StallAry = getStall.get();
                if (StallAry.size() != 0) {
                    Stalls stall = StallAry.get(0);
                    viewHolder.Owner.setText(stall.getOwner().toString());
                    viewHolder.Description.setText(stall.getDescription().toString());
                    try {
                        viewHolder.Picture.setImageBitmap(stall.getThumbnail());
                    }catch (NullPointerException e){};
                } else {
                    Toast.makeText(getContext(),"cannot find the stall",Toast.LENGTH_SHORT).show();
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            convertView.setTag(viewHolder);
        }
        else{
            mainHolder = (EditStallViewHolder)convertView.getTag();
            mainHolder.BidAmt.setText(bid.BidAmount().toString());
            mainHolder.Name.setText(CurrentAccount.getAccount().getEmail());
            ElasticSearchCtr.GetStall getStall = new ElasticSearchCtr.GetStall();
            String[] query = new String[2];
            query[1]="_id";
            query[0]=bid.BidStallID();
            getStall.execute(query);
            try {
                ArrayList<Stalls>StallAry = getStall.get();
                if (StallAry.size() != 0) {
                    Stalls stall = StallAry.get(0);
                    mainHolder.Owner.setText(stall.getOwner());
                    mainHolder.Description.setText(stall.getDescription());
                    try {
                        mainHolder.Picture.setImageBitmap(stall.getThumbnail());
                    }catch (NullPointerException e){};
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        return convertView;
    }

    public class EditStallViewHolder {
        TextView Name;
        TextView BidAmt;
        TextView Description;
        TextView Owner;
        ImageView Picture;
    }

}
