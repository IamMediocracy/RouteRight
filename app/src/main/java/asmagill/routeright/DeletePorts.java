package asmagill.routeright;

import android.os.AsyncTask;

import net.sbbi.upnp.impls.InternetGatewayDevice;
import net.sbbi.upnp.messages.UPNPResponseException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by William on 4/20/2016.
 */
public class DeletePorts extends AsyncTask<Void, Void, Void> {

    PortObjects service;

    public DeletePorts(PortObjects service){
        this.service = service;
    }

    @Override
    protected Void doInBackground(Void... params) {



        try {
            InternetGatewayDevice[] respEx = InternetGatewayDevice.getDevices(5000);



            if (respEx != null) {
                for (int i = 0; i < respEx.length; ++i) {
                    InternetGatewayDevice testIGD = respEx[i];

                    for(String port:service.getTCP()){
                        testIGD.deletePortMapping((String) null, Integer.parseInt(port.trim()), "TCP");
                    }

                    for(String port:service.getUDP()){
                        testIGD.deletePortMapping((String) null, Integer.parseInt(port.trim()), "UDP");
                    }


                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (UPNPResponseException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        Application_Information.service = null;
        AdapterHolder.Update_adapter();

        super.onPostExecute(aVoid);
    }
}
