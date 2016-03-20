package asmagill.routeright;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.util.Log;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

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

    private int ipAddressint;

    public final String[] publicIPArray = new String[1];

    public CurrentNetworkInformation(Context mContext) {

        ConnectivityManager connManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();

        if (networkInfo.isConnected()) {

            Thread thread = new Thread(new Runnable(){
                @Override
                public void run() {
                    URL url = null;
                    try {
                        url = new URL("http://whatismyip.akamai.com");
                    } catch (MalformedURLException e) {
                        return;
                    }

                    try {

                        StringBuffer htmlCode = new StringBuffer();
                        Log.i("CurrentNetworkInfo", "Opening URL Stream");
                        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

                        String inputLine;

                        while ((inputLine = in.readLine()) != null) {
                            htmlCode.append(inputLine);
                        }

                        publicIPArray[0] = htmlCode.toString();

                    } catch (Exception e){
                        Log.i("CurrentNetworkInfo", "Get number ip");
                        e.printStackTrace();
                        publicIPArray[0] = "Failed to Get content";

                    }
                }
            });

            thread.start();

            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            WifiManager wifi = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);

            DhcpInfo dhcp = wifi.getDhcpInfo();
            WifiInfo wifiInfo = wifi.getConnectionInfo();

            isConnected = true;
            dnsOne = AddressFormatter.integerToInetAddress(dhcp.dns1, false);
            dnsTwo = AddressFormatter.integerToInetAddress(dhcp.dns2, false);
            gateway = AddressFormatter.integerToInetAddress(dhcp.gateway, false);
            ipAddress = intToIp(dhcp.ipAddress);
            leaseLength = String.valueOf(dhcp.leaseDuration);
            netmask = AddressFormatter.integerToInetAddress(dhcp.netmask, false);
            serverAddress = AddressFormatter.integerToInetAddress(dhcp.serverAddress, false);
            ipAddressint = dhcp.ipAddress;
            SSID = wifiInfo.getSSID();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                frequency = String.valueOf(wifiInfo.getFrequency()) + " MHz";
            } else {
                frequency = "Android Version too old.";
            }

            linkSpeed = String.valueOf(wifiInfo.getLinkSpeed()) + " Mbps";
            macAddress = wifiInfo.getMacAddress();

            Log.i("CurrentNetworkInfo", "Get number ip" + intToIp(ipAddressint));

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
            publicIPArray[0] = "...";
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

    public String getPublicIP() { return publicIPArray[0]; }

    public String getNetworkAddress() {

        int octet = 0;

        String [] subnet = netmask.split("\\.");
        String [] ipaddresssplit = ipAddress.split("\\.");



        for(String token: subnet){
            if(!token.contains("255")){
                break;
            }
            octet++;
        }

        int[] netbits = getBits(Integer.parseInt(subnet[octet]));

        int[] ipbits = getBits(Integer.parseInt(ipaddresssplit[octet]));

        int borrowed = 0;
        for(int num: netbits){
            if(num == 1){
                borrowed++;
            }
        }

        for(int i = borrowed; i < 8; i++){
            ipbits[i] = 0;
        }

        for(int i = octet + 1; i < 4; i++){
            ipaddresssplit[i] = "0";
        }

        String rebuild = "";

        for(int i = 0; i < 4; i++){
            if(i == octet){
                rebuild = rebuild + getNumberFromBits(ipbits);
            } else {
                rebuild = rebuild + ipaddresssplit[i];
            }

            if(i != 3){
                rebuild = rebuild + ".";
            }

        }

        return rebuild;
    }

    private int[] getBits(int num){
        int[] bits = new int[8];

        if(num >= 128){
            bits[0] = 1;
            num -= 128;
        } else {
            bits[0] = 0;
        }

        if(num >= 64){
            bits[1] = 1;
            num -= 64;
        } else {
            bits[1] = 0;
        }

        if(num >= 32){
            bits[2] = 1;
            num -= 32;
        } else {
            bits[2] = 0;
        }

        if(num >= 16){
            bits[3] = 1;
            num -= 16;
        } else {
            bits[3] = 0;
        }

        if(num >= 8){
            bits[4] = 1;
            num -= 8;
        } else {
            bits[4] = 0;
        }

        if(num >= 4){
            bits[5] = 1;
            num -= 4;
        } else {
            bits[5] = 0;
        }

        if(num >= 2){
            bits[6] = 1;
            num -= 2;
        } else {
            bits[6] = 0;
        }

        if(num >= 1){
            bits[7] = 1;
            num -= 1;
        } else {
            bits[7] = 0;
        }

        return bits;
    }

    private int getNumberFromBits(int[] bits){

        int result = 0;

        if(bits[0] == 1){
            result += 128;
        }

        if(bits[1] == 1){
            result += 64;
        }

        if(bits[2] == 1){
            result += 32;
        }

        if(bits[3] == 1){
            result += 16;
        }

        if(bits[4] == 1){
            result += 8;
        }

        if(bits[5] == 1){
            result += 4;
        }

        if(bits[6] == 1){
            result += 2;
        }

        if(bits[7] == 1){
            result += 1;
        }

        return result;
    }

    private String scrapeIpAddress(String in){

        int start = in.indexOf("<body>");
        int end = in.indexOf("</body>");

        if (start != -1) {
            return in.substring(start + "<title>".length(), end);
        }

        return "Not Obtainable";
    }

    public String intToIp(int addr) {
        return  ((addr & 0xFF) + "." +
                ((addr >>>= 8) & 0xFF) + "." +
                ((addr >>>= 8) & 0xFF) + "." +
                ((addr >>>= 8) & 0xFF));
    }
}
