package asmagill.routeright;

import android.os.AsyncTask;

import net.sbbi.upnp.impls.InternetGatewayDevice;
import net.sbbi.upnp.messages.UPNPResponseException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by William on 4/15/2016.
 */
public class AddPorts extends AsyncTask<Void, Void, Void> {



    private final String Protocol_String = "ccc";

    private final String Application_String = "bbb";

    private String appname;

    private String service_name;

    private String dest_ip;

    private ArrayList<String> udp = new ArrayList<String>();
    private ArrayList<String> tcp = new ArrayList<String>();

    boolean manual = false;

    public AddPorts(String appname, String service_name, String dest_ip){
        this.appname = appname;
        this.service_name = service_name;
        this.dest_ip = dest_ip;
    }

    public AddPorts(String appname, String service_name, String dest_ip, ArrayList<String> tcp, ArrayList<String> udp){
        this.appname = appname;
        this.service_name = service_name;
        this.dest_ip = dest_ip;
        this.tcp = tcp;
        this.udp = udp;
        manual = true;
    }

    @Override
    protected Void doInBackground(Void... params) {

         if(!manual){

             List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
             nameValuePairs.add(new BasicNameValuePair(Protocol_String, "T"));
             nameValuePairs.add(new BasicNameValuePair(Application_String, appname));
             try {

                 DefaultHttpClient httpclient = new DefaultHttpClient();

                 HttpPost httppost = new HttpPost(
                         "http://comp305pos.ddns.net/dbaddports.php");

                 httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                 HttpResponse response = httpclient.execute(httppost);

                 HttpEntity entity = response.getEntity();

                 InputStream is = entity.getContent();

                 BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                 String ans = "";
                 String line = null;
                 while ((line = reader.readLine()) != null) {
                     ans += line;
                 }
                 is.close();



                 System.out.print(ans + " : Port String");

                 String[] port_array;

                 if(ans.contains("|")) {
                     port_array  =ans.split("[|]");
                 } else {
                     port_array = new String[1];
                     port_array[0] = ans;
                 }

                 for(String t:port_array){
                     System.out.println(t + ": array print");
                 }


                 for(int i=0;i<port_array.length;i++){

                     String chk = port_array[i];
                     if(chk.contains("-")){
                         String[] nums = chk.split("-");

                         int beginning = Integer.parseInt(nums[0].trim());
                         int end = Integer . parseInt(nums[1].trim());

                         for(int k = beginning; k <= end; k++){
                             tcp.add(k + "");
                         }
                     } else {
                         tcp.add(chk);
                     }


                 }



             } catch (ClientProtocolException e){
                 System.out.println("Client Protocol Exception");
             } catch (IOException e) {
                 System.out.println("IO Exception");
             }  catch (Error e){
                 System.out.println("Random Error");
             }

             nameValuePairs.clear();
             nameValuePairs.add(new BasicNameValuePair(Protocol_String, "U"));
             nameValuePairs.add(new BasicNameValuePair(Application_String, appname));

             try {

                 DefaultHttpClient httpclient = new DefaultHttpClient();

                 HttpPost httppost = new HttpPost(
                         "http://comp305pos.ddns.net/dbaddports.php");

                 httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                 HttpResponse response = httpclient.execute(httppost);

                 HttpEntity entity = response.getEntity();



                 InputStream is = entity.getContent();

                 BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                 String ans = "";
                 String line = null;
                 while ((line = reader.readLine()) != null) {
                     ans += line;
                 }
                 is.close();



                 System.out.print(ans + " : Port String");

                 String[] port_array;

                 if(ans.contains("|")) {
                   port_array  =ans.split("[|]");
                 } else {
                     port_array = new String[1];
                     port_array[0] = ans;
                 }



                 for(int i=0;i<port_array.length;i++){

                     String chk = port_array[i];
                     if(chk.contains("-")){
                         String[] nums = chk.split("-");

                         int beginning = Integer.parseInt(nums[0].trim());
                         int end = Integer.parseInt(nums[1].trim());

                         for(int k = beginning; k <= end; k++){
                             udp.add(k + "");
                         }
                     } else {
                         udp.add(chk);
                     }


                 }



             } catch (ClientProtocolException e){
                 System.out.println("Client Protocol Exception");
             } catch (IOException e) {
                 System.out.println("IO Exception");
             }  catch (Error e){
                 System.out.println("Random Error");
             }


         }

        try {

            InternetGatewayDevice[] respEx = InternetGatewayDevice.getDevices(5000);

            if (respEx != null) {
                InternetGatewayDevice testIGD = respEx[0];

                for (String port : tcp) {
                    System.out.println(port);
                    int port_num = Integer.parseInt(port.trim());
                    boolean mapped = testIGD.addPortMapping(service_name, (String) null, port_num, port_num, dest_ip, 0, "TCP");
                }

                for (String port : udp) {
                    int port_num = Integer.parseInt(port.trim());
                    boolean mapped = testIGD.addPortMapping(service_name, (String) null, port_num, port_num, dest_ip, 0, "UDP");
                }

            }
        } catch (IOException e){

        } catch (UPNPResponseException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {

        AdapterHolder.Update_adapter();

    }
}
