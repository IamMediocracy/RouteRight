package asmagill.routeright;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by William on 4/11/2016.
 */
public class PortMapWizard extends Activity {

    PortWizardFragment frgmnt;
    PortManualFragment mFragment;

    boolean wizard = true;

    public boolean destroy = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.portmapwizard);

        if (findViewById(R.id.wizard) != null) {


            if (savedInstanceState != null) {
                return;
            }


           frgmnt = new PortWizardFragment();

            mFragment = new PortManualFragment();

            frgmnt.setArguments(getIntent().getExtras());


            getFragmentManager().beginTransaction()
                    .add(R.id.wizard, frgmnt).commit();
        }

        Button clear = (Button) findViewById(R.id.back_button);

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (wizard) {
                    frgmnt.saveValues();
                } else {
                    mFragment.saveValues();
                }
                finish();

            }
        });

        Button modeswitch = (Button) findViewById(R.id.switch_modes);

        modeswitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Button btn = (Button) v;


                if(wizard){
                    frgmnt.saveValues();
                    FragmentManager manager = getFragmentManager();
                    FragmentTransaction trans = manager.beginTransaction();

                    trans.remove(frgmnt);
                    trans.add(R.id.wizard, mFragment);
                    trans.commit();

                    btn.setText("Wizard");
                    TextView txt = (TextView) findViewById(R.id.descript_view);
                    txt.setText("Manual Entry");
                    wizard = false;
                } else {
                    mFragment.saveValues();
                    FragmentManager manager = getFragmentManager();
                    FragmentTransaction trans = manager.beginTransaction();

                    trans.remove(mFragment);
                    trans.add(R.id.wizard, frgmnt);
                    trans.commit();
                    btn.setText("Manual");
                    TextView txt = (TextView) findViewById(R.id.descript_view);
                    txt.setText("Port Wizard");
                            wizard = true;
                }

            }
        });

    }

}
