package cmput301w16t01crimsonhorizons.parkinghelper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Kevin L on 2/15/2016.
 *
 * Idea obtained from https://www.youtube.com/watch?v=ZEEYYvVwJGY
 * This is created so that we can have a listview with buttons.
 */
public class CustomLstAdapter extends ArrayAdapter<String> {
    private int layout;
    public CustomLstAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        layout = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder mainHolder = null;
        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(layout,parent,false);
            MyViewHolder viewHolder = new MyViewHolder();
            viewHolder.eachStallInfo=(TextView)convertView.findViewById(R.id.EachStallWithBids);
            viewHolder.Accept = (Button)convertView.findViewById(R.id.AcceptBtn);
            // TODO: 2/15/2016 Make the button do its job
            viewHolder.Accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "Accepted!!",Toast.LENGTH_SHORT).show();
                }
            });
            viewHolder.Decline = (Button)convertView.findViewById(R.id.DeclineBtn);
            viewHolder.Decline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(),"Declined!!",Toast.LENGTH_SHORT).show();
                }
            });
            convertView.setTag(viewHolder);
        }
        else{
            mainHolder = (MyViewHolder)convertView.getTag();
            mainHolder.eachStallInfo.setText(getItem(position));
        }
        return convertView;
    }
    public class MyViewHolder {
        TextView eachStallInfo;
        Button Accept;
        Button Decline;
    }
}
