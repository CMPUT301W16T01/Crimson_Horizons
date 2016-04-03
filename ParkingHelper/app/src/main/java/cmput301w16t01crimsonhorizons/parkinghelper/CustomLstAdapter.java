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
import java.util.concurrent.ExecutionException;

/**
 * Created by Kevin L on 2/15/2016.
 *
 * Idea obtained from https://www.youtube.com/watch?v=ZEEYYvVwJGY
 * This is created so that we can have a listview with buttons.
 * This is for accept/decline bids
 * @see AdapterEditStall
 */
public class CustomLstAdapter extends ArrayAdapter<Bid> {
    private int layout;
    //Todo set what it takes in.
    public CustomLstAdapter(Context context, int resource, List<Bid> objects) {
        super(context, resource, objects);
        layout = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder mainHolder = null;
        final Bid bidsForStall1 = getItem(position);
        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(layout,parent,false);
            MyViewHolder viewHolder = new MyViewHolder();
            viewHolder.Bidder =(TextView)convertView.findViewById(R.id.BidderBFS);
            viewHolder.BidAmt = (TextView)convertView.findViewById(R.id.BidAmtBFS);
            viewHolder.BidPic = (ImageView)convertView.findViewById(R.id.BidPicture);

            viewHolder.Bidder.setText(bidsForStall1.Bidder());
            viewHolder.BidAmt.setText(bidsForStall1.BidAmount().toString());
            ElasticSearchCtr.GetStall getStall = new ElasticSearchCtr.GetStall();
            getStall.execute(new String[]{"_id", bidsForStall1.BidStallID()});
            try {
                Stalls stall = getStall.get().get(0);
                viewHolder.BidPic.setImageBitmap(stall.getThumbnail());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

            viewHolder.Accept = (Button)convertView.findViewById(R.id.AcceptBtn);
            viewHolder.Accept.setTag(position);
            // TODO: 2/15/2016 Make the button do its job
            viewHolder.Accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BidsForStall bfs = new BidsForStall();
                    bfs.acceptBid(bidsForStall1);
                    Toast.makeText(getContext(), "Accepted!!",Toast.LENGTH_SHORT).show();
                }
            });
            viewHolder.Decline = (Button)convertView.findViewById(R.id.DeclineBtn);
            viewHolder.Decline.setTag(position);
            viewHolder.Decline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BidsForStall bfs = new BidsForStall();
                    bfs.declineBid(bidsForStall1);
                    Toast.makeText(getContext(), "Declined!!", Toast.LENGTH_SHORT).show();
                }
            });
            convertView.setTag(viewHolder);
        }
        else{
            //TODO SET THE STUFF FOR RIGHT DISPLAY
            mainHolder = (MyViewHolder)convertView.getTag();
            mainHolder.Bidder.setText(bidsForStall1.Bidder());
            mainHolder.BidAmt.setText(bidsForStall1.BidAmount().toString());
            ElasticSearchCtr.GetStall getStall = new ElasticSearchCtr.GetStall();
            getStall.execute(new String[]{"_id", bidsForStall1.BidStallID()});
            try {
                Stalls stall = getStall.get().get(0);
                mainHolder.BidPic.setImageBitmap(stall.getThumbnail());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        return convertView;
    }
    public class MyViewHolder {
        TextView Bidder;
        TextView BidAmt;
        Button Accept;
        Button Decline;
        ImageView BidPic;
    }
}
