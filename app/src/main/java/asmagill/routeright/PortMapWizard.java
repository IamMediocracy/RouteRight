package asmagill.routeright;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by William on 4/11/2016.
 */
public class PortMapWizard extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.portmapwizard);

        if (findViewById(R.id.wizard) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
           PortWizardFragment frgmnt = new PortWizardFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            frgmnt.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getFragmentManager().beginTransaction()
                    .add(R.id.wizard, frgmnt).commit();
        }


    }

}
