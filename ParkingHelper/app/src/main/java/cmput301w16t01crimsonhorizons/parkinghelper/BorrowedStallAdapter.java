package cmput301w16t01crimsonhorizons.parkinghelper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Kevin L on 3/12/2016.
 * This is adapter for a stall the user is borrowing
 */
public class BorrowedStallAdapter extends ArrayAdapter<Stalls> {
    private int Layout;
    public BorrowedStallAdapter(Context context, int resource, List<Stalls> objects) {
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
            viewHolder.Owner=(TextView)convertView.findViewById(R.id.OwnerBorrowedStall);
            viewHolder.Description = (TextView)convertView.findViewById(R.id.DescriptionBorrowedStall);
            viewHolder.Picture = (ImageView)convertView.findViewById(R.id.PicBorrowedStall);
            viewHolder.Owner.setText(stall.getOwner());
            viewHolder.Description.setText(stall.getDescription());
            viewHolder.Picture.setImageBitmap(stall.getThumbnail());
            convertView.setTag(viewHolder);
        }
        else{
            mainHolder = (EditStallViewHolder)convertView.getTag();
            mainHolder.Owner.setText(stall.getOwner());
            mainHolder.Description.setText(stall.getDescription());
            mainHolder.Picture.setImageBitmap(stall.getThumbnail());
        }
        return convertView;
    }

    public class EditStallViewHolder {
        TextView Owner;
        TextView Description;
        ImageView Picture;
    }

}
