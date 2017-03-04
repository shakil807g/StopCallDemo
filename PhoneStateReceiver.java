package com.shakil.stop;

/**
 * Created by Shakil Karim on 11/20/16.
 */

import  java.lang.reflect.Method;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.util.Log;

public class PhoneStateReceiver extends BroadcastReceiver {

    public static String TAG="PhoneStateReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {


      try {


          if (intent.getAction().equals(TelephonyManager.ACTION_PHONE_STATE_CHANGED))
          {


                String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);

                 Log.d(TAG,"PhoneStateReceiver**Call State=" + state);


                  if (state.equals(TelephonyManager.EXTRA_STATE_RINGING))
                  {


                      if(MyApp.getInstance().isDeviceLocked())
                      {

                          if (!Utils.KillCall(context)) { // Using the method defined earlier
                              Log.d(TAG, "PhoneStateReceiver **Unable to kill incoming call");
                          }
                          Utils.disconnectCall();

                      }


                  }


        }





        }catch (Exception e){
            e.printStackTrace();
        }



    }



}
