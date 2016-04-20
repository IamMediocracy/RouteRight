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
import android.widget.ProgressBar;

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
                Application_Information.bar = (ProgressBar) getActivity().findViewById(R.id.progress_bar);
                Application_Information.bar.setVisibility(View.VISIBLE);




            }
        });

        Button accept = ( Button ) getView().findViewById(R.id.accept_button);

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edtProcessName = (EditText) getActivity().findViewById(R.id.custom_service_name);
                EditText ip = (EditText) getActivity().findViewById(R.id.custom_ip_address);

                add_ports(edtProcessName.getText().toString(), ip.getText().toString());

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

    public void add_ports(String process_name, String ip){

        if(check(process_name, ip)) {
            AddPorts add = new AddPorts(Application_Information.curr_appname, process_name, ip);
            add.execute();

            Application_Information.curr_appname = null;
            Application_Information.curr_ip = "";
            Application_Information.curr_search_selected = "";

            AdapterHolder.adapter.clear();
            AdapterHolder.adapter.add(new PortObjects("Please Wait..."));

            Application_Information.enableActions = false;
            Fragment_PortMap.updateUI(false);

            getActivity().finish();

        }

    }

    public void updateUI(){
        EditText edtProcessName = (EditText) getActivity().findViewById(R.id.custom_service_name);
        edtProcessName.setText(Application_Information.curr_appname);
    }



    @Override
    public void onPause() {

        saveValues();
        super.onPause();
    }

    public void onSubmit(){
        Application_Information.curr_ip = "";
        Application_Information.curr_appname = "";
        AdapterHolder.apps_adapter = null;

    }

    public void saveValues(){

        EditText edtProcessName = (EditText) getActivity().findViewById(R.id.custom_service_name);
        EditText ip = (EditText) getActivity().findViewById(R.id.custom_ip_address);

        if(!ip.getText().toString().equals("")) {
            Application_Information.curr_ip = ip.getText().toString();
        }

        if(!edtProcessName.getText().toString().equals("")) {
            Application_Information.curr_appname = edtProcessName.getText().toString();
        }

    }

    private boolean check(String ProcessName, String ip){

        try{
            if(Application_Information.curr_appname == null || Application_Information.curr_appname.isEmpty()){
                ToastFactory.showToast(getActivity(), "No Application Selected");
                throw new Error();
            }

            if(ProcessName.trim().equals("")){
                ToastFactory.showToast(getActivity(), "Service Name is Empty");
                throw new Error();
            }



                String[] tokens = ip.split("\\.");

                if(tokens.length != 4){
                    ToastFactory.showToast(getActivity(), "Malformed IP Address");
                    throw new Error();

                }

                for(String token: tokens) {
                    int m;

                    m = Integer.parseInt(token);


                    if(m > 255 || m < 0){
                        ToastFactory.showToast(getActivity(), "IP Decimal Integer Out Of Range");
                        throw new Error();
                    }
        }



        } catch (NumberFormatException e){
            ToastFactory.showToast(getActivity(), "Malformed IP Address");
            return false;
        } catch (Error e){
            return false;
        }

        return true;
    }
}
