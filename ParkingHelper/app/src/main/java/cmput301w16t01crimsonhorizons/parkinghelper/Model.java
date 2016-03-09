package cmput301w16t01crimsonhorizons.parkinghelper;

import android.view.*;

import java.util.ArrayList;

/**
 * Created by Kevin L on 3/5/2016.
 * Generic model class.
 */
public class Model<V extends ViewInterface> {
    private static ArrayList<ViewInterface> views;

    public Model(){
        if (views==null) {
            views = new ArrayList<>();
        }
    }

    public void addView(V view) {
        if (!views.contains(view)) {
            views.add(view);
        }
    }

    public void deleteView(V view) {
        views.remove(view);
    }

    public void notifyViews() {
        for (ViewInterface view : views) {
            view.updateView(this);
        }
    }

}
