package cmput301w16t01crimsonhorizons.parkinghelper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Kevin L on 2/29/2016.
 *
 * This is the adapter needed to display the list of stalls with the proper format
 * For Account activity
 * @see CustomLstAdapter This is similar.
 */
public class AdapterYourBids extends ArrayAdapter<Stalls>{
    private int Layout;
    public AdapterYourBids(Context context, int resource, List<Stalls> objects) {
        super(context, resource, objects);
        Layout=resource;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        EditStallViewHolder mainHolder = null;
        Stalls stall = getItem(position);
        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(Layout,parent,false);
            EditStallViewHolder viewHolder = new EditStallViewHolder();
            viewHolder.Owner=(TextView)convertView.findViewById(R.id.StallNameEditStallV);
            viewHolder.Description = (TextView)convertView.findViewById(R.id.DescriptionEditStallV);
            viewHolder.BidAmt = (TextView)convertView.findViewById(R.id.BidAmt);
            viewHolder.Name = (TextView)convertView.findViewById(R.id.Username);
            viewHolder.Picture = (ImageView)convertView.findViewById(R.id.PictureEditStallV);

            if (stall.getBidder().equals(CurrentAccount.getAccount().getEmail())) {
                viewHolder.Owner.setText(stall.getOwner());
                viewHolder.BidAmt.setText(stall.getBidAmt().toString());
                viewHolder.Description.setText(stall.getDescription());
                viewHolder.Name.setText(CurrentAccount.getAccount().getEmail());
                try {
                    viewHolder.Picture.setImageBitmap(stall.getThumbnail());
                }catch (NullPointerException e){};
            } else {
                ArrayList<String> all = new ArrayList<String>(Arrays.asList(stall.getLstBidders().split(",")));
                int i = 0;
                while (i < all.size()) {
                    if (all.get(i).split(" ")[0].equals(CurrentAccount.getAccount().getEmail())){
                        viewHolder.Owner.setText(stall.getOwner());
                        viewHolder.BidAmt.setText(all.get(i).split(" ")[1]);
                        viewHolder.Description.setText(stall.getDescription());
                        viewHolder.Name.setText(CurrentAccount.getAccount().getEmail());
                        viewHolder.Picture.setImageBitmap(stall.getThumbnail());
                        break;
                    } else {
                        i=i+1;
                    }
                }
            }
            convertView.setTag(viewHolder);
        }
        else{
            mainHolder = (EditStallViewHolder)convertView.getTag();
            mainHolder.Owner.setText(stall.getOwner());
            mainHolder.BidAmt.setText(stall.getBidAmt().toString());
            mainHolder.Description.setText(stall.getDescription());
            mainHolder.Name.setText(CurrentAccount.getAccount().getEmail());
            mainHolder.Picture.setImageBitmap(stall.getThumbnail());
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
