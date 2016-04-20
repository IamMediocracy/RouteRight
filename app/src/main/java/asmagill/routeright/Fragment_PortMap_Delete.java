package asmagill.routeright;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by William on 4/11/2016.
 */
public class Fragment_PortMap_Delete extends Activity {

    ArrayAdapter<String> delete_list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.portinfo_delete);

        delete_list = new ArrayAdapter<String>(getApplicationContext(), R.layout.delete_adapter_layout);


        for(int i = 0; i < AdapterHolder.adapter.getCount(); i++){
            PortObjects service = AdapterHolder.adapter.getItem(i);

            delete_list.add(service.getService_name());
        }



        ListView lst = (ListView) findViewById(R.id.delete_list);
        lst.setAdapter(delete_list);

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                PortObjects service = AdapterHolder.adapter.getItem(position);
                Application_Information.service = service;
                
                TextView txt = (TextView) findViewById(R.id.delete_selected);
                txt.setText("Selected: " + service.getService_name());

                Application_Information.delete_select = "Selected: " + service.getService_name();

                Button submit = (Button) findViewById(R.id.delete_button);
                submit.setEnabled(true);

            }
        });

        if(Application_Information.delete_select != null){
            TextView txt = (TextView) findViewById(R.id.delete_selected);
            txt.setText(Application_Information.delete_select);
            Button submit = (Button) findViewById(R.id.delete_button);
            submit.setEnabled(true);
        }

        Button submit = (Button) findViewById(R.id.delete_button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete_ports(Application_Information.service);
            }
        });

        Button back = (Button) findViewById(R.id.delete_back_button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void delete_ports(PortObjects service) {

        DeletePorts delete = new DeletePorts(service);
        delete.execute();

        Application_Information.delete_select = null;


        AdapterHolder.adapter.clear();
        AdapterHolder.adapter.add(new PortObjects("Please Wait..."));

        Fragment_PortMap.updateUI(false);

        finish();
    }

}
