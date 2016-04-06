package asmagill.routeright;

import java.util.ArrayList;

/**
 * Created by William on 4/6/2016.
 */
public class PortObjects {

    private String service_name;

    private String address;

    private ArrayList<String> TCP = new ArrayList<String>();

    private ArrayList<String> UDP = new ArrayList<String>();

    public PortObjects( String name){
        service_name = name;
    }

    public void AddTCP(String str){
        TCP.add(str);
    }

    public void AddUDP(String str){
        UDP.add(str);
    }

    public String getService_name(){
        return service_name;
    }

    public ArrayList<String> getTCP(){
        return TCP;
    }

    public ArrayList<String> getUDP(){
        return UDP;
    }

    public String getAddress(){
        return address;
    }

    public void setAddress(String str){
        address = str;
    }

    public String getTCPString(){

        StringBuilder sb = new StringBuilder("");

        if (TCP.size() == 0){
            return "";
        }

        for(String str: TCP){
            sb.append(str + ", ");
        }

        return sb.substring(0, sb.length() - 2);
    }

    public String getUDPString(){

        StringBuilder sb = new StringBuilder("");

        if (UDP.size() == 0){
            return "";
        }

        for(String str: UDP){
            sb.append(str + ", ");
        }

        return sb.substring(0, sb.length() - 2);
    }

}
