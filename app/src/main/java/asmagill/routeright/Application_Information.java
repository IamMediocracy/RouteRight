package asmagill.routeright;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.ProgressBar;

/**
 * Created by William on 4/15/2016.
 */
public class Application_Information {

    //Used for Port Display
    public static boolean enableActions;

    //Used For Port Mapping Wizard
    public static String curr_search_selected;
    public static String curr_ip;
    public static String curr_appname;
    public static ProgressBar bar;


    //Used For Manual Port Mapping
    public static String man_service_name;
    public static String man_ip;
    public static String tcp_port;
    public static String udp_port;

    public static boolean isNetworkAvailable(Context ctxt) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) ctxt.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
