package asmagill.routeright;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.app.Activity;
import android.os.Build;
import android.support.v4.content.res.TypedArrayUtils;

import org.eclipse.jetty.util.ArrayUtil;

import java.math.BigInteger;
import java.net.InetAddress;
import java.util.Formatter;

/**
 * Created by william on 3/8/16.
 */
public class CurrentNetworkInformation {



    private String dnsOne;
    private String dnsTwo;
    private String gateway;
    private String ipAddress;
    private String leaseLength;
    private String netmask;
    private String serverAddress;
    private boolean isConnected;

    private String SSID;
    private String frequency;
    private String linkSpeed;
    private String macAddress;

    public CurrentNetworkInformation(Context mContext) {

        ConnectivityManager connManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();

        if (networkInfo.isConnected()) {

            WifiManager wifi = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);

            DhcpInfo dhcp = wifi.getDhcpInfo();
            WifiInfo wifiInfo = wifi.getConnectionInfo();

            isConnected = true;

            dnsOne = String.valueOf(dhcp.dns1);
            dnsTwo = String.valueOf(dhcp.dns2);
            gateway = String.valueOf(dhcp.gateway);
            ipAddress = String.valueOf(dhcp.ipAddress);
            leaseLength = String.valueOf(dhcp.leaseDuration);
            netmask = String.valueOf(dhcp.netmask);
            serverAddress = String.valueOf(dhcp.serverAddress);

            SSID = String.valueOf(wifiInfo.getSSID());

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                frequency = String.valueOf(wifiInfo.getFrequency());
            } else {
                frequency = "Android Version too old.";
            }

            linkSpeed = String.valueOf(wifiInfo.getLinkSpeed());
            macAddress = String.valueOf(wifiInfo.getMacAddress());


        } else {

            isConnected = false;

            dnsOne = "...";
            dnsTwo = "...";
            gateway = "...";
            ipAddress = "...";
            leaseLength = "...";
            netmask = "...";
            serverAddress = "...";
            frequency = "...";
            SSID = "...";
            linkSpeed = "...";
            macAddress = "...";
        }

    }



    public String getDnsOne() {
        return dnsOne;
    }

    public String getDnsTwo() {
        return dnsTwo;
    }

    public String getGateway() {
        return gateway;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getLeaseLength() {
        return leaseLength;
    }

    public String getNetmask() {
        return netmask;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public boolean isConnected(){
        return isConnected;
    }

    public String getFrequency() {
        return frequency;
    }

    public String getLinkSpeed() {
        return linkSpeed;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public String getSSID() {
        return SSID;
    }
}
