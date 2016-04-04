package cmput301w16t01crimsonhorizons.parkinghelper;

import io.searchbox.annotations.JestId;

/**
 * Created by jero1994 on 2016-04-02.
 * What a review would consist
 */
public class Review {
    private String User;
    private String StallID;
    private String text;

    public Review(String User, String StallID, String text) {
        this.User = User;
        this.StallID = StallID;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public String getStallID() {
        return StallID;
    }

    public String getUser() {
        return User;
    }

    @JestId
    private String reviewID;

    public String getReviewID() {
        return reviewID;
    }
    public void setReviewID(String reviewID) {
        this.reviewID = reviewID;
    }
}
