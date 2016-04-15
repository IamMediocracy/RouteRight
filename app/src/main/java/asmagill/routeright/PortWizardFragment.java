package asmagill.routeright;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

/**
 * Created by William on 4/11/2016.
 */
public class PortWizardFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.portwizard_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if( AdapterHolder.apps_adapter == null){
            AdapterHolder.apps_adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1);

        }

        ListView list = (ListView) getView().findViewById(R.id.app_list);

        list.setAdapter(AdapterHolder.apps_adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String curr = (String) AdapterHolder.apps_adapter.getItem(position);
                Application_Information.curr_search_selected = curr;
                Application_Information.curr_appname = curr;

                updateUI();
            }
        });

        Button init = (Button) getView().findViewById(R.id.initiate_search);

        init.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edt = (EditText) getView().findViewById(R.id.searchbox);



                runSearch(edt.getText().toString());



            }
        });


        EditText edtProcessName = (EditText) getActivity().findViewById(R.id.custom_service_name);
        EditText ip = (EditText) getActivity().findViewById(R.id.custom_ip_address);

        if(Application_Information.curr_appname != null) {
            edtProcessName.setText(Application_Information.curr_appname);
        }

        if(Application_Information.curr_ip != null) {
            ip.setText(Application_Information.curr_ip);
        }

    }

    public void runSearch(String str){

        DatabaseSearch m= new DatabaseSearch(str, AdapterHolder.apps_adapter);
        m.execute();

    }

    public void updateUI(){
        EditText edtProcessName = (EditText) getActivity().findViewById(R.id.custom_service_name);
        edtProcessName.setText(Application_Information.curr_appname);
    }



    @Override
    public void onPause() {
        EditText edtProcessName = (EditText) getActivity().findViewById(R.id.custom_service_name);
        EditText ip = (EditText) getActivity().findViewById(R.id.custom_ip_address);

        if(!ip.getText().toString().equals("")) {
            Application_Information.curr_ip = ip.getText().toString();
        }

        if(!edtProcessName.getText().toString().equals("")) {
            Application_Information.curr_appname = edtProcessName.getText().toString();
        }
        super.onPause();
    }

    public void onSubmit(){
        Application_Information.curr_ip = "";
        Application_Information.curr_appname = "";
        AdapterHolder.apps_adapter = null;

    }
}
