package asmagill.routeright;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by William on 3/17/2016.
 */
public class Fragment_Information extends Fragment{




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.content_basic_layout, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedinstancestate) {
        super.onActivityCreated(savedinstancestate);

        ListView list = (ListView) getView().findViewById(R.id.list);

        final NetworkObjectEntryAdapter netAdapter = new NetworkObjectEntryAdapter(getActivity(), R.layout.general_list_item);
        list.setAdapter(netAdapter);

        for(final NetworkObject entry : getEntries()){
            netAdapter.add(entry);
        }
    }

    private List<NetworkObject> getEntries(){

        final List<NetworkObject> entries = new ArrayList<NetworkObject>();


        // Use these values to set values for display
        // values are currently set to defaults for display purpose

        String ssid_val;
        String status_val;
        String type_val;
        String network_val;
        String ipAdd_val;
        String publicIp_val;
        String gateway_val;
        String subnet_val;
        String macAdd_val;
        String dns_val;
        String dhcp_val;
        String frequency_val;
        String linkspeed_val;
        String lease_length;

        if(Application_Information.isNetworkAvailable(getContext())) {

            CurrentNetworkInformation info = new CurrentNetworkInformation(getContext());
            //More values to add, add more fields.
            ssid_val = info.getSSID();
            status_val = info.isConnected() ? "Connected" : "Not Connected";
            type_val = "Wifi";
            network_val = info.getNetworkAddress();
            ipAdd_val = info.getIpAddress();
            publicIp_val = info.getPublicIP();
            gateway_val = info.getGateway();
            subnet_val = info.getNetmask();
            macAdd_val = info.getMacAddress();
            dns_val = info.getDnsOne();
            dhcp_val = info.getServerAddress();


            //Added Strings
            frequency_val = info.getFrequency();
            linkspeed_val = info.getLinkSpeed();
            lease_length = info.getLeaseLength();

        } else {
            ssid_val = "...";
            status_val = "Not Connected";
            type_val = "...";
            network_val = "...";
            ipAdd_val = "...";
            publicIp_val = "...";
            gateway_val = "...";
            subnet_val = "...";
            macAdd_val = "...";
            dns_val = "...";
            dhcp_val = "...";

            frequency_val = "...";
            linkspeed_val = "...";
            lease_length = "...";
        }

        //Adds network objects to ListView
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
        entries.add(new NetworkObject("Lease Length: ", lease_length));

        return entries;
    }

}
