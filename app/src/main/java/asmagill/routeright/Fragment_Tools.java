package asmagill.routeright;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by William on 3/17/2016.
 */
public class Fragment_Tools extends ListFragment {

    String tools[] = { "Ping" };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tools_layout, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedinstancestate) {
        super.onActivityCreated(savedinstancestate);

        setListAdapter(new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, tools));


    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {
        // TODO Auto-generated method stub
        super.onListItemClick(l, v, position, id);

        if (position == 0) {
            Intent intent = new Intent(getContext(), Ping.class);
            startActivity(intent);

    }

}


}