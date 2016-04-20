package asmagill.routeright;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ArrayAdapter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by William on 4/12/2016.
 */
public class DatabaseSearch extends AsyncTask<Void, Void, ArrayList<String>> {

    private String searchString;

    public ArrayAdapter<String> adapter;

    private static final String tag = "aaa";

    public DatabaseSearch(String searchString, ArrayAdapter<String> adapter){

        this.searchString = searchString;
        this.adapter = adapter;
    }

    @Override
    protected ArrayList<String> doInBackground(Void... params) {

        ArrayList<String> lst = new ArrayList<String>();

        System.out.println(searchString);

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair(tag, searchString));

        try {

            DefaultHttpClient httpclient = new DefaultHttpClient();

            HttpPost httppost = new HttpPost(
                    "http://comp305pos.ddns.net/dbsearch.php");

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            HttpResponse response = httpclient.execute(httppost);

            HttpEntity entity = response.getEntity();

            InputStream is = entity.getContent();

            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            is.close();

            String result=sb.toString();

            System.out.print(result);

            JSONArray jArray = new JSONArray(result);

            for(int i=0;i<jArray.length();i++){
                JSONObject json_data = jArray.getJSONObject(i);
                lst.add(json_data.getString("app_name"));


            }
            return lst;
        } catch (ClientProtocolException e){
            System.out.println("Client Protocol Exception");
        } catch (IOException e) {
            System.out.println("IO Exception");
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Error e){
            System.out.println("Random Error");
        }

        lst.add("Error");

        return lst;
    }

    @Override
    protected void onPostExecute(ArrayList<String> str){
        adapter.clear();
        adapter.addAll(str);
        adapter.notifyDataSetChanged();
        Application_Information.bar.setVisibility(View.INVISIBLE);
    }

}
