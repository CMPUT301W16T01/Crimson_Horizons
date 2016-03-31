package cmput301w16t01crimsonhorizons.parkinghelper;

import android.content.Context;

import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kevin L on 2/15/2016.
 *
 * Idea obtained from https://www.youtube.com/watch?v=ZEEYYvVwJGY
 * This is created so that we can have a listview with buttons.
 * This is for accept/decline bids
 * @see AdapterEditStall
 */
public class CustomLstAdapter extends ArrayAdapter<String> {
    private int layout;
    //Todo set what it takes in.
    public CustomLstAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        layout = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder mainHolder = null;
        String s1 = getItem(position);
        String[] splitted = s1.split(" ");
        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(layout,parent,false);
            final MyViewHolder viewHolder = new MyViewHolder();
            viewHolder.Bidder =(TextView)convertView.findViewById(R.id.BidderBFS);
            viewHolder.BidAmt = (TextView)convertView.findViewById(R.id.BidAmtBFS);

            viewHolder.Bidder.setText(splitted[0]);
            viewHolder.BidAmt.setText(splitted[1]);

            viewHolder.Accept = (Button)convertView.findViewById(R.id.AcceptBtn);
            viewHolder.Accept.setTag(position);
            // TODO: 2/15/2016 Make the button do its job
            viewHolder.Accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String both = viewHolder.Bidder + " " + viewHolder.BidAmt.toString();
                    BidsForStall bfs = new BidsForStall();
                    bfs.acceptBid(both);
                    Toast.makeText(getContext(), "Accepted!!",Toast.LENGTH_SHORT).show();
                }
            });
            viewHolder.Decline = (Button)convertView.findViewById(R.id.DeclineBtn);
            viewHolder.Decline.setTag(position);
            viewHolder.Decline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String both = viewHolder.Bidder + " " + viewHolder.BidAmt.toString();
                    BidsForStall bfs = new BidsForStall();
                    bfs.declineBid(both);
                    Toast.makeText(getContext(), "Declined!!", Toast.LENGTH_SHORT).show();
                }
            });
            convertView.setTag(viewHolder);
        }
        else{
            //TODO SET THE STUFF FOR RIGHT DISPLAY
            mainHolder = (MyViewHolder)convertView.getTag();
            mainHolder.Bidder.setText(splitted[0]);
            mainHolder.BidAmt.setText(splitted[1]);
        }
        return convertView;
    }
    public class MyViewHolder {
        TextView Bidder;
        TextView BidAmt;
        Button Accept;
        Button Decline;
    }
}
