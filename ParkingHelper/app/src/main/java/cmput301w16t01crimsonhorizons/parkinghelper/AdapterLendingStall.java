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
 * Created by Kevin L on 2/29/2016.
 *
 * This is the adapter needed to display the list of stalls with the proper format
 * For Account activity
 * @see CustomLstAdapter This is similar.
 */
public class AdapterLendingStall extends ArrayAdapter<Stalls>{
    private int Layout;
    public AdapterLendingStall(Context context, int resource, List<Stalls> objects) {
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
            viewHolder.Name=(TextView)convertView.findViewById(R.id.StallNameEditStallV);
            viewHolder.Description = (TextView)convertView.findViewById(R.id.DescriptionEditStallV);
            viewHolder.Status = (TextView)convertView.findViewById(R.id.StatusEditStallV);
            viewHolder.Picture = (ImageView)convertView.findViewById(R.id.PictureEditStallV);
            viewHolder.Name.setText(stall.getBorrower());
            viewHolder.Status.setText(stall.getStatus());
            viewHolder.Description.setText(stall.getDescription());
            viewHolder.Picture.setImageBitmap(stall.getThumbnail());
            convertView.setTag(viewHolder);
        }
        else{
            mainHolder = (EditStallViewHolder)convertView.getTag();
            mainHolder.Name.setText(stall.getBorrower());
            mainHolder.Status.setText(stall.getStatus());
            mainHolder.Description.setText(stall.getDescription());
        }
        return convertView;
    }

    public class EditStallViewHolder {
        TextView Name;
        TextView Status;
        TextView Description;
        ImageView Picture;
    }
}
