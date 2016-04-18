package asmagill.routeright;

import android.widget.ArrayAdapter;

/**
 * Created by William on 4/6/2016.
 */
public class AdapterHolder {

    //Used in Main Activity
    public static PortObjectsAdapter adapter;

    //Used in the Port Map Wizard
    public static ArrayAdapter apps_adapter;

    //Used in PortManualFragment\
    public static ArrayAdapter<String> tcp_ports;
    public static ArrayAdapter<String> udp_ports;


    public static void Update_adapter(){
        NetworkingStuuf m= new NetworkingStuuf(AdapterHolder.adapter);
        m.execute();
    }
}
