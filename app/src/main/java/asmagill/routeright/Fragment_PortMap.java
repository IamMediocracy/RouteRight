package asmagill.routeright;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.fourthline.cling.android.AndroidUpnpService;
import org.fourthline.cling.android.AndroidUpnpServiceImpl;
import org.fourthline.cling.android.FixedAndroidLogHandler;
import org.fourthline.cling.model.meta.Device;
import org.fourthline.cling.model.meta.LocalDevice;
import org.fourthline.cling.model.meta.RemoteDevice;
import org.fourthline.cling.registry.DefaultRegistryListener;
import org.fourthline.cling.registry.Registry;

/**
 * Created by William on 3/17/2016.
 */
public class Fragment_PortMap extends ListFragment{


    static Button add;
    static Button edit;
    static Button delete;



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        add = (Button) getView().findViewById(R.id.portmap_add);
        edit = (Button) getView().findViewById(R.id.portmap_edit);
        delete = (Button) getView().findViewById(R.id.portmap_delete);


        org.seamless.util.logging.LoggingUtil.resetRootHandler(new FixedAndroidLogHandler());

        if(AdapterHolder.adapter.isEmpty()){
            AdapterHolder.adapter.add(new PortObjects("Please Wait..."));
        }

        setListAdapter(AdapterHolder.adapter);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Fragment_PortMap_Delete.class);
                getActivity().startActivity(intent);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PortMapWizard.class);
                getActivity().startActivity(intent);
            }
        });



        updateUI(Application_Information.enableActions);


    }


        @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.portmapping_main, container, false);
    }

    public static void updateUI(Boolean enable){
        if(add == null){

        } else {

            if(enable){
                add.setEnabled(true);
                delete.setEnabled(true);
                edit.setEnabled(true);
            } else {
                add.setEnabled(false);
                delete.setEnabled(false);
                edit.setEnabled(false);
            }

        }
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
