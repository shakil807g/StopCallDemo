package com.shakil.stop;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Shakil Karim on 3/4/17.
 */

public class OutCallReceiver extends BroadcastReceiver {

    private static final String TAG = "OutCallReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL))
        {
            // Outgoing call


            if(MyApp.getInstance().isDeviceLocked())
            {


                if (!Utils.KillCall(context)) { // Using the method defined earlier
                    Log.d(TAG, "PhoneStateReceiver **Unable to kill outgoing call");
                }

                Utils.disconnectCall();

                setResultData(null);



            }

        }
    }


}
