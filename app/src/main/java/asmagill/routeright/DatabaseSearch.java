package asmagill.routeright;

import android.os.AsyncTask;

import java.util.ArrayList;

/**
 * Created by William on 4/12/2016.
 */
public class DatabaseSearch extends AsyncTask<Void, Void, Void> {

    private String searchString;

    public DatabaseSearch(String searchstring){
        this.searchString = searchString;
    }

    @Override
    protected Void doInBackground(Void... params) {
        return null;
    }


}
