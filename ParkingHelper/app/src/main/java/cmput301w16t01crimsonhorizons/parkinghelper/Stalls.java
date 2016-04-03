package cmput301w16t01crimsonhorizons.parkinghelper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.StaticLayout;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import io.searchbox.annotations.JestId;

/**
 * Created by schuman on 2/23/16.
 * Edited Kevin
 * Class of what each stall would have
 * Initialize all variables.
 */
public class Stalls implements Serializable{
    private String Owner = "";
    private String Description = "";
    private String Borrower = "";
    private String Status = "Available";
    private Double[] location = {0.00,0.00};
    protected transient Bitmap thumbnail = null;
    protected String thumbnailBase64 = null;

    public String getThumbnailBase64() {
        return thumbnailBase64;
    }

    public void setThumbnail(Bitmap newThumbnail){
        if (newThumbnail != null) {
            thumbnail = newThumbnail;
            ByteArrayOutputStream byteArrayBitmapStream = new ByteArrayOutputStream();
            newThumbnail.compress(Bitmap.CompressFormat.PNG, 100, byteArrayBitmapStream);

            byte[] b = byteArrayBitmapStream.toByteArray();
            thumbnailBase64 = Base64.encodeToString(b, Base64.NO_WRAP);
        }
        else if(thumbnail != null){
            Bitmap.Config config = thumbnail.getConfig();
            thumbnail.recycle();
            thumbnail = null;
            thumbnailBase64 = null;
            thumbnail = Bitmap.createBitmap(1,1, Bitmap.Config.ARGB_8888);
            ByteArrayOutputStream byteArrayBitmapStream = new ByteArrayOutputStream();
            thumbnail.compress(Bitmap.CompressFormat.PNG, 100, byteArrayBitmapStream);

            byte[] b = byteArrayBitmapStream.toByteArray();
            //thumbnailBase64 = Base64.encodeToString(b, Base64.DEFAULT);
        }
    }

    public Bitmap getThumbnail() {
        if (thumbnail == null && thumbnailBase64 != null) {
            byte[] decodeString = Base64.decode(thumbnailBase64, Base64.DEFAULT);
            thumbnail = BitmapFactory.decodeByteArray(decodeString, 0, decodeString.length);
        }
        return thumbnail;
    }


    public Double[] getLocation() {
        return location;
    }


    public void setLocation(Double[] location) {
        this.location = location;
    }

    //// TODO: 3/10/2016 MAKE THE IMAGE STORE PROPERLY
    private String image;
        public String getBorrower() {
        return Borrower;
    }
    public void setBorrower(String borrower) {
        this.Borrower = borrower;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @JestId
    private String StallID;

    public String getOwner() {
        return Owner;
    }

    public void setOwner(String owner) {
        Owner = owner;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getStatus() {
        return Status;
    }
    public void setStatus(String status) {
        this.Status = status;
    }
    public void setStatus() {
        if (Borrower != "") {
            Status = "Borrowed";
        }
        ElasticSearchCtr.GetBid getBid = new ElasticSearchCtr.GetBid();
        getBid.execute(new String[] { "bidStallID", this.getStallID() });
        try {
            if (getBid.get().size() > 0) {
                Status = "Bidded";
            } else {
                Status = "Available";
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
    public String getStallID() {
        return StallID;
    }
    public void setStallID(String stallID) {
        StallID = stallID;
    }

    public Double getHighBidAmount() {
        ArrayList<Bid> bidResults = new ArrayList<Bid>();
        Double result = 0.00;
        ElasticSearchCtr.GetBid getBid = new ElasticSearchCtr.GetBid();
        getBid.execute(new String[] { "bidStallID", this.getStallID() });
        try {
            bidResults = getBid.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        for (Bid b : bidResults) {
            result = Math.max(result, b.BidAmount());
        }
        return result;
    }
}
