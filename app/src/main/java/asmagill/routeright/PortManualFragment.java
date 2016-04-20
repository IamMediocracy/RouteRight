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
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by William on 4/11/2016.
 */
public class PortManualFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.portmanual_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if( AdapterHolder.tcp_ports == null){
            AdapterHolder.tcp_ports = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1);

        }

        if( AdapterHolder.udp_ports == null){
            AdapterHolder.udp_ports = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1);

        }

        ListView tcp_list = (ListView) getView().findViewById(R.id.tcp_ports);

        tcp_list.setAdapter(AdapterHolder.tcp_ports);

        tcp_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                //updateUI();
            }
        });

        ListView udp_list = (ListView) getView().findViewById(R.id.udp_ports);

        udp_list.setAdapter(AdapterHolder.udp_ports);

        udp_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                //updateUI();
            }
        });


        Button add_tcp = (Button) getView().findViewById(R.id.accept_tcp_button);

        add_tcp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edt = (EditText) getView().findViewById(R.id.tcp_port_edt);
                String getstr = edt.getText().toString().trim();

                try{
                    AdapterHolder.tcp_ports.add(Integer.parseInt(getstr) + "");
                    edt.setText("");
                } catch (NumberFormatException e) {
                    ToastFactory.showToast(getActivity(), "TCP value not valid");
                }
            }
        });

        Button add_udp = (Button) getView().findViewById(R.id.accept_udp_button);

        add_udp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edt = (EditText) getView().findViewById(R.id.udp_port_edt);
                String getstr = edt.getText().toString().trim();

                try{
                    AdapterHolder.udp_ports.add(Integer.parseInt(getstr) + "");
                    edt.setText("");
                } catch (NumberFormatException e) {
                    ToastFactory.showToast(getActivity(), "UDP value not valid");
                }
            }
        });
        Button accept = ( Button ) getView().findViewById(R.id.manual_accept_button);

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                add_ports();

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

    public void add_ports(){

        EditText edtProcessName = (EditText) getActivity().findViewById(R.id.manual_service_name);
        EditText ip = (EditText) getActivity().findViewById(R.id.manual_custom_ip_address);
        EditText tcp_edt = (EditText) getActivity().findViewById(R.id.tcp_port_edt);
        EditText udp_edt = (EditText) getActivity().findViewById(R.id.udp_port_edt);

        if(check(edtProcessName.getText().toString(), ip.getText().toString())) {

            String ref = Application_Information.curr_appname;

            ArrayList<String> tcp = new ArrayList<>();
            ArrayList<String> udp = new ArrayList<>();

            for (int i = 0; i < AdapterHolder.tcp_ports.getCount(); i++) {
                tcp.add(AdapterHolder.tcp_ports.getItem(i));
            }

            for (int i = 0; i < AdapterHolder.udp_ports.getCount(); i++) {
                tcp.add(AdapterHolder.udp_ports.getItem(i));
            }

            AddPorts add = new AddPorts(edtProcessName.getText().toString(), edtProcessName.getText().toString(), ip.getText().toString(), tcp, udp);
            add.execute();


            AdapterHolder.udp_ports.clear();
            AdapterHolder.tcp_ports.clear();
            AdapterHolder.adapter.add(new PortObjects("Please Wait..."));

            getActivity().finish();
        }
    }

    private boolean check(String proc_name, String ip) {

        try{
            if(proc_name.trim().equals("")){
                ToastFactory.showToast(getActivity(),"Service Name Empty");
                throw new Error();
            }


            String[] tokens = ip.split("\\.");

            if(tokens.length != 4){
                ToastFactory.showToast(getActivity(), "Malformed IP Address");
                throw new Error();

            }

            for(String token: tokens) {
                int m;
                m = Integer.parseInt(token.trim());

                if(m > 255 || m < 0){
                    ToastFactory.showToast(getActivity(), "IP Decimal Integer Out Of Range");
                    throw new Error();
                }
            }

            if(AdapterHolder.tcp_ports.getCount() == 0 && AdapterHolder.udp_ports.getCount() == 0){
                ToastFactory.showToast(getActivity(), "No Ports were Specified");
                throw new Error();
            }


        } catch (NumberFormatException e){
            ToastFactory.showToast(getActivity(), "Malformed IP Address");
            return false;
        } catch (Error e){
            return false;
        }

        return true;
    }

    @Override
    public void onPause() {

        saveValues();
        super.onPause();
    }

    public void onSubmit(){
        Application_Information.man_ip = "";
        Application_Information.man_service_name = "";
        AdapterHolder.tcp_ports = null;
        AdapterHolder.udp_ports = null;

    }

    public void saveValues(){

        EditText edtProcessName = (EditText) getActivity().findViewById(R.id.manual_service_name);
        EditText ip = (EditText) getActivity().findViewById(R.id.manual_custom_ip_address);
        EditText tcp_edt = (EditText) getActivity().findViewById(R.id.tcp_port_edt);
        EditText udp_edt = (EditText) getActivity().findViewById(R.id.udp_port_edt);

        if(!ip.getText().toString().equals("")) {
            Application_Information.man_ip = ip.getText().toString();
        }

        if(!edtProcessName.getText().toString().equals("")) {
            Application_Information.man_service_name = edtProcessName.getText().toString();
        }

        if(!tcp_edt.getText().toString().equals("")) {
            Application_Information.tcp_port = tcp_edt.getText().toString();
        }

        if(!udp_edt.getText().toString().equals("")) {
            Application_Information.udp_port = udp_edt.getText().toString();
        }

    }
}
