package asmagill.routeright;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by William on 4/11/2016.
 */
public class PortMapWizard extends Activity {

    PortWizardFragment frgmnt;

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


            frgmnt.setArguments(getIntent().getExtras());


            getFragmentManager().beginTransaction()
                    .add(R.id.wizard, frgmnt).commit();
        }

        Button clear = (Button) findViewById(R.id.back_button);

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frgmnt.saveValues();
                finish();

            }
        });

    }

}
