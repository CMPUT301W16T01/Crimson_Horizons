package cmput301w16t01crimsonhorizons.parkinghelper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Kevin L on 2/29/2016.
 */
public class AdapterEditStall extends ArrayAdapter<Stalls>{
    private int Layout;
    public AdapterEditStall(Context context, int resource, List<Stalls> objects) {
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
            viewHolder.Name=(EditText)convertView.findViewById(R.id.StallNameEditStallV);
            viewHolder.Description = (EditText)convertView.findViewById(R.id.DescriptionEditStallV);
            viewHolder.Status = (EditText)convertView.findViewById(R.id.StatusEditStallV);
            convertView.setTag(viewHolder);
        }
        else{
            mainHolder = (EditStallViewHolder)convertView.getTag();
            mainHolder.Name.setText(stall.getOwner());
            mainHolder.Status.setText(stall.getStatus());
            mainHolder.Description.setText(stall.getDescription());
        }
        return convertView;
    }

    public class EditStallViewHolder {
        EditText Name;
        EditText Status;
        EditText Description;
    }

}