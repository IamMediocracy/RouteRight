package asmagill.routeright;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class basic_layout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Use these values to set values for display
        // values are currently set to defaults for display purpose

    CurrentNetworkInformation info = new CurrentNetworkInformation(this);

        String ssid_val = info.getSSID();
        String status_val = "";
        String type_val = "";
        String network_val = info.getNetworkAddress();
        String ipAdd_val = "";
        String publicIp_val = "";
        String gateway_val = "";
        String subnet_val = "";
        String macAdd_val = "";
        String dns_val = "";
        String dhcp_val = "";

    }

    public void sendBasic(View view) {
        Intent intent = new Intent(this, basic_layout.class);
        startActivity(intent);
    }

    public void sendSearch(View view) {
        Intent intent = new Intent(this, search.class);
        startActivity(intent);
    }

    public void sendPortFor(View view) {
        Intent intent = new Intent(this, port_forward_main.class);
        startActivity(intent);
    }

}
