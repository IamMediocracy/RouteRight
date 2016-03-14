package asmagill.routeright;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.HttpResponse;

import java.util.ArrayList;
import java.util.List;

public class basic_layout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);





        ListView list = (ListView) findViewById(R.id.list);

        final NetworkObjectEntryAdapter netAdapter = new NetworkObjectEntryAdapter(this, R.layout.general_list_item);
        list.setAdapter(netAdapter);

        for(final NetworkObject entry : getEntries()){
            netAdapter.add(entry);
        }
    }

    private List<NetworkObject> getEntries(){

        final List<NetworkObject> entries = new ArrayList<NetworkObject>();


        // Use these values to set values for display
        // values are currently set to defaults for display purpose

        CurrentNetworkInformation info = new CurrentNetworkInformation(this);

        //More values to add, add more fields.
        String ssid_val = info.getSSID();
        String status_val = info.isConnected() ? "Connected" : "Not Connected";
        String type_val = "Wifi";
        String network_val = info.getNetworkAddress();
        String ipAdd_val = info.getIpAddress();
        String publicIp_val = info.getPublicIP();
        String gateway_val = info.getGateway();
        String subnet_val = info.getNetmask();
        String macAdd_val = info.getMacAddress();
        String dns_val = info.getDnsOne();
        String dhcp_val = info.getServerAddress();


        //Added Strings

        String frequency_val = info.getFrequency();
        String linkspeed_val = info.getLinkSpeed();

        entries.add(new NetworkObject("Status: ", status_val));
        entries.add(new NetworkObject("SSID: ", ssid_val));
        entries.add(new NetworkObject("IP Address: ", ipAdd_val));
        entries.add(new NetworkObject("Network Address: ", network_val));
        entries.add(new NetworkObject("Subnet Mask: ", subnet_val));
        entries.add(new NetworkObject("Gateway: ", gateway_val));
        entries.add(new NetworkObject("Public IP: ", publicIp_val));
        entries.add(new NetworkObject("MAC Address", macAdd_val));
        entries.add(new NetworkObject("DNS: ", dns_val));
        entries.add(new NetworkObject("DHCP: ", dhcp_val));
        entries.add(new NetworkObject("Frequency: ", frequency_val));
        entries.add(new NetworkObject("Link Speed: ", linkspeed_val));

        return entries;
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
