package asmagill.routeright;

import android.content.Context;
import android.os.Build;
import android.widget.Toast;

/**
 * Created by William on 4/18/2016.
 */
public class ToastFactory {

    public static Toast toast;

    public static void showToast(Context context, String text){



        if (toast != null)
            toast.cancel();

        boolean condition = Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB;
        if ((toast == null && condition) || !condition)
            toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
        if ((toast != null && condition))
            toast.setText(text);
        toast.show();


    }

}
