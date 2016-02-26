package asmagill.routeright;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class port_forward_main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_port_forward_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
