package asmagill.routeright;

import android.os.AsyncTask;

import net.sbbi.upnp.impls.InternetGatewayDevice;
import net.sbbi.upnp.messages.ActionResponse;
import net.sbbi.upnp.messages.UPNPResponseException;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;

/**
 * Created by William on 4/6/2016.
 */
public class NetworkingStuuf extends AsyncTask<Void, Void, ArrayList<PortObjects>>{

    public PortObjectsAdapter adapter;

    boolean pass = true;

    public NetworkingStuuf (PortObjectsAdapter adapter){
        this.adapter = adapter;
    }

    @Override
    protected ArrayList<PortObjects> doInBackground(Void... params) {

        ArrayList<PortObjects> str = null;
        try {

            str = new ArrayList<PortObjects>();

            InternetGatewayDevice[] respEx = InternetGatewayDevice.getDevices(5000);



            if (respEx != null) {
                for (int i = 0; i < respEx.length; ++i) {
                    InternetGatewayDevice testIGD = respEx[i];
                    System.out.println("\tFound device " + testIGD.getIGDRootDevice().getModelName());
                    System.out.println("External IP address: " + testIGD.getExternalIPAddress());
                    Integer natTableSize = testIGD.getNatTableSize();
                    System.out.println("NAT table size is " + natTableSize);

                    for (int portNum = 0; portNum < natTableSize.intValue(); ++portNum) {
                        ActionResponse localHostIP = testIGD.getGenericPortMappingEntry(portNum);
                        System.out.println(portNum + 1 + ".\t" + localHostIP);
                        String response = localHostIP.toString();

                        String portEx = response.substring(response.indexOf("NewExternalPort=") + 16, response.indexOf("\n", response.indexOf("NewExternalPort=") + 16));
                        String portIn = response.substring(response.indexOf("NewInternalPort=") + 16, response.indexOf("\n", response.indexOf("NewInternalPort=") + 16));
                        String protocol = response.substring(response.indexOf("NewProtocol=") + 12, response.indexOf("\n", response.indexOf("NewProtocol=") + 12));
                        String ip_address = response.substring(response.indexOf("NewInternalClient=") + 18, response.indexOf("\n", response.indexOf("NewInternalClient=") + 18));
                        String portmapdescription = response.substring(response.indexOf("NewPortMappingDescription=") + 26, response.indexOf("\n", response.indexOf("NewPortMappingDescription=") + 26));

                        if(portmapdescription.indexOf("TCP")> -1){
                            portmapdescription = portmapdescription.substring(0, portmapdescription.indexOf("TCP"));
                        }

                        if(portmapdescription.indexOf("UDP")> -1){
                            portmapdescription = portmapdescription.substring(0, portmapdescription.indexOf("UDP"));
                        }

                        if(portmapdescription.indexOf("(")> -1){
                            portmapdescription = portmapdescription.substring(0, portmapdescription.indexOf("("));
                        }

                        boolean isFound = false;
                        int position = 0;
                        for(PortObjects obj : str){
                            if(obj.getService_name().equals(portmapdescription)){
                                isFound = true;
                                break;
                            }
                            position++;
                        }

                        if(isFound){
                            PortObjects old = str.get(position);
                            if(portEx.equals(portIn)){
                                if(protocol.equals("TCP")){
                                    old.AddTCP(portIn);
                                } else {
                                    old.AddUDP(portIn);
                                }
                            } else {
                                if(protocol.equals("TCP")){
                                    old.AddTCP("Ex: " + portEx + "-In:" + portIn);
                                } else {
                                    old.AddUDP("Ex: " + portEx + "-In:" + portIn);
                                }
                            }
                        } else {

                            PortObjects newObject = new PortObjects(portmapdescription);

                            if(portEx.equals(portIn)){
                                if(protocol.equals("TCP")){
                                    newObject.AddTCP(portIn);
                                } else {
                                    newObject.AddUDP(portIn);
                                }
                            } else {
                                if(protocol.equals("TCP")){
                                    newObject.AddTCP("Ex: " + portEx + "-In:" + portIn);
                                } else {
                                    newObject.AddUDP("Ex: " + portEx + "-In:" + portIn);
                                }
                            }

                            newObject.setAddress(ip_address);

                            str.add(newObject);

                        }

                    }

                    short var13 = 9090;
                    System.out.println("\nTrying to map dummy port " + var13 + "...");
                    String var14 = InetAddress.getLocalHost().getHostAddress();
                    boolean mapped = testIGD.addPortMapping("Some mapping description", (String) null, var13, var13, var14, 0, "TCP");
                    if (mapped) {
                        System.out.println("Port " + var13 + " mapped to " + var14);
                        System.out.println("Current mappings count is " + testIGD.getNatMappingsCount());
                        ActionResponse resp = testIGD.getSpecificPortMappingEntry((String) null, var13, "TCP");
                        if (resp != null) {
                            System.out.println("Port " + var13 + " mapping confirmation received from device");
                        }

                        System.out.println("Delete dummy port mapping...");
                        boolean unmapped = testIGD.deletePortMapping((String) null, var13, "TCP");
                        if (unmapped) {
                            System.out.println("Port " + var13 + " unmapped");
                        }
                    }
                }

                System.out.println("\nDone.");
            } else {
                str.add(new PortObjects("Could not find router with UPNP Enabled."));
                pass = false;

                System.out.println("Unable to find IGD on your network");
            }
        } catch (IOException var11) {
            pass = false;

        } catch (UPNPResponseException var12) {
            System.err.println("UPNP device unhappy " + var12.getDetailErrorCode() + " " + var12.getDetailErrorDescription());
            str.add(new PortObjects("Response Error."));
            pass = false;

        }

        return str;
    }

    @Override
    protected void onPostExecute(ArrayList<PortObjects> str){
        Application_Information.enableActions = pass;
        Fragment_PortMap.updateUI(pass);

        adapter.clear();
        adapter.addAll(str);
        adapter.notifyDataSetChanged();
    }


}
