package asmagill.routeright;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by william on 3/26/16.
 */
public class Ping extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ping_layout);

        Button back = (Button) findViewById(R.id.ping_back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }


    private static final String TAG = "Ping.java";

    public static String pingError = null;


    /**
     * Does not work in Android emulator and also delay by '1' second if host not pingable
     * In the Android emulator only ping to 127.0.0.1 works
     *
     * @return
     * @throws IOException
     * @throws InterruptedException
     * @ param String host in dotted IP address format
     */

//    public void ping(String host) throws IOException, InterruptedException {
//        StringBuffer echo = new StringBuffer();
//        Runtime runtime = Runtime.getRuntime();
//        Log.v(TAG, "About to ping using runtime.exec");
//        Process proc = runtime.exec("ping -c 5 " + host);
//        proc.waitFor();
//        int exit = proc.exitValue();
//        if (exit == 0) {
//            InputStreamReader reader = new InputStreamReader(proc.getInputStream());
//            BufferedReader buffer = new BufferedReader(reader);
//            String line = "";
//            while ((line = buffer.readLine()) != null) {
//                echo.append(line + "\n");
//            }
//            getPingStats(echo.toString());
//        } else if (exit == 1) {
//            pingError = "failed, exit = 1";
//        } else {
//            pingError = "error, exit = 2";
//        }
//    }

    // thread of "ping" process
    public Runnable ping = new Runnable() {
        StringBuffer echo = new StringBuffer();
        public void run() {
            Runtime runtime = Runtime.getRuntime();
            try {
                EditText et = (EditText) findViewById(R.id.ping_address);
                String host = et.getText().toString();
//                long a = System.currentTimeMillis() % 1000;
                Process proc = runtime.exec("/system/bin/ping -c 4 " + host);
//                int exitValue = proc.waitFor();
//                pingTime = System.currentTimeMillis() % 1000 - a;
//                int exit = proc.exitValue();
//                if (exit == 0) {

                    InputStreamReader reader = new InputStreamReader(proc.getInputStream());
                    BufferedReader buffer = new BufferedReader(reader);
                    String line = "";
                    while ((line = buffer.readLine()) != null) {
                        echo.append(line + "\n");
                    }
                    Log.v(TAG, echo.toString());

//                    getPingStats(echo.toString());
//                } else if (exit == 1) {
//                    pingError = "failed, exit = 1";
//                } else {
//                    pingError = "error, exit = 2";
//                }
                proc.destroy();
            } catch (IOException e) {
                e.printStackTrace();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
            }

            // update the ping result - we need to call this on the UI thread
            // because it updates UI elements (TextView)
            runOnUiThread(new Runnable() {
                public void run() {
                    TextView output = (TextView) findViewById(R.id.ping_results);
                    output.append(echo.toString());
                    output.append("\n\n");
                    echo.delete(0,echo.length());
                }
            });

        }
    };

    /**
     * getPingStats interprets the text result of a Linux ping command
     * <p/>
     * Set pingError on error and return null
     * <p/>
     * http://en.wikipedia.org/wiki/Ping
     * <p/>
     * PING 127.0.0.1 (127.0.0.1) 56(84) bytes of data.
     * 64 bytes from 127.0.0.1: icmp_seq=1 ttl=64 time=0.251 ms
     * 64 bytes from 127.0.0.1: icmp_seq=2 ttl=64 time=0.294 ms
     * 64 bytes from 127.0.0.1: icmp_seq=3 ttl=64 time=0.295 ms
     * 64 bytes from 127.0.0.1: icmp_seq=4 ttl=64 time=0.300 ms
     * <p/>
     * --- 127.0.0.1 ping statistics ---
     * 4 packets transmitted, 4 received, 0% packet loss, time 0ms
     * rtt min/avg/max/mdev = 0.251/0.285/0.300/0.019 ms
     * <p/>
     * PING 192.168.0.2 (192.168.0.2) 56(84) bytes of data.
     * <p/>
     * --- 192.168.0.2 ping statistics ---
     * 1 packets transmitted, 0 received, 100% packet loss, time 0ms
     * <p/>
     * # ping 321321.
     * ping: unknown host 321321.
     * <p/>
     * 1. Check if output contains 0% packet loss : Branch to success -> Get stats
     * 2. Check if output contains 100% packet loss : Branch to fail -> No stats
     * 3. Check if output contains 25% packet loss : Branch to partial success -> Get stats
     * 4. Check if output contains "unknown host"
     *
     * @param s
     */
    public void getPingStats(String s) {
//        TextView success = (TextView) findViewById(R.id.ping_success);
//        TextView failed = (TextView) findViewById(R.id.ping_failed);
//        TextView time = (TextView) findViewById(R.id.ping_time);

        Log.v(TAG, s);

        int recieved = s.indexOf("Received = "); // +11
        int lost = s.indexOf("Lost = "); // +7
        int average = s.indexOf("Average = "); // +10

//        String afterEqual = s.substring(s.indexOf("="), s.length()).trim();
//        String afterFirstSlash = afterEqual.substring(afterEqual.indexOf('/') + 1, afterEqual.length()).trim();
//        String strAvgRtt = afterFirstSlash.substring(0, afterFirstSlash.indexOf('/'));
//        Double avgRtt = Double.valueOf(strAvgRtt);
//        Log.v(TAG,s);

//        success.setText(s.substring(recieved + 11, 12));
//        failed.setText(s.substring(lost + 7, 8));
//        time.setText(s.substring(average + 10, 11));

        // Use the .setText(r) here

    }

    public void pinger(View view) throws IOException, InterruptedException {
        // The ping button was selected
        new Thread(ping).start();

    }

}
