package asmagill.routeright;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by William on 3/17/2016.
 */
public class MainActivity extends AppCompatActivity {

    private FragmentTabHost mTabHost;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getTitle());
            getSupportActionBar().setDisplayUseLogoEnabled(true);
            getSupportActionBar().setIcon(R.mipmap.ic_launcher_real);
        }

        AdapterHolder.adapter = new PortObjectsAdapter(this,
                R.layout.service_list_item);


        if(Application_Information.isNetworkAvailable(this)) {
            NetworkingStuuf m = new NetworkingStuuf(AdapterHolder.adapter);
            m.execute();
        } else {
            AdapterHolder.adapter.clear();
            AdapterHolder.adapter.add(new PortObjects("Not Connected To A Network"));
        }
        Application_Information.enableActions = false;

        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

        mTabHost.addTab(
                mTabHost.newTabSpec("tab1").setIndicator("Info", null),
                Fragment_Information.class, null);
        mTabHost.addTab(
                mTabHost.newTabSpec("tab2").setIndicator("Port Map"),
                Fragment_PortMap.class, null);
        mTabHost.addTab(
                mTabHost.newTabSpec("tab3").setIndicator("Tools", null),
                Fragment_Tools.class, null);
        mTabHost.addTab(
                mTabHost.newTabSpec("tab4").setIndicator("Search", null),
                Fragment_Search.class, null);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
