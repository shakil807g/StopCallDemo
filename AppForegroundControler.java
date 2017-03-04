package com.shakil.stop;

import android.app.ActivityManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by Shakil Karim on 3/4/17.
 */

public class AppForegroundControler {

    private List<String> mrunningtask = new ArrayList<>();
    private List<String> mhomelauncherList;
    private String mpacakageName;
    private Context mcontext;
    private Handler handler;
    private Runnable runnable;
    private static final String TAG = "AppForegroundControler";

    public AppForegroundControler(Context context) {
        mcontext = context.getApplicationContext();
        mpacakageName = context.getApplicationContext().getPackageName();
        mhomelauncherList = getLauncherActivitiesNames();

    }

    public void startTracking(){

        handler = new Handler();
        runnable = new Runnable() {

            @Override
            public void run()
            {

                if(MyApp.getInstance().isDeviceLocked())
                {

                    addTaskRunningtoList();


                    if (is_From_Home_or_isHome() )
                    {

                        Log.d(TAG, "is activity running: background ");


                        if(MyApp.getInstance().isDeviceLocked()) {

                            Intent intent = new Intent();
                            intent.setClass(mcontext, MainActivity.class);
                            intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP | FLAG_ACTIVITY_NEW_TASK);
                            PendingIntent pendingIntent = PendingIntent.getActivity(mcontext, 0, intent, 0);
                            try {
                                pendingIntent.send(mcontext, 0, intent);
                            } catch (PendingIntent.CanceledException e) {
                                e.printStackTrace();
                            }
                        }

                        handler.postDelayed(this, 4000);

                    }else {

                        handler.postDelayed(this, 1000);
                    }





                }



            }
        };

        handler.post(runnable);


    }

    private void addTaskRunningtoList() {



        ActivityManager am = (ActivityManager) mcontext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);




        if (!tasks.isEmpty())
        {
            ComponentName topActivityName = tasks.get(0).topActivity;

            //Log.d(TAG, "top Activity package name: " + topActivityName.getPackageName());


            if(topActivityName != null)
            {

                boolean issameAslast = false;

                if(mrunningtask !=null && mrunningtask.size() > 0)
                {
                    issameAslast = mrunningtask.get(mrunningtask.size() - 1).equals(topActivityName.getPackageName());

                }


                if(!issameAslast)
                    mrunningtask.add(topActivityName.getPackageName());



            }



        }




    }


    private boolean is_From_Home_or_isHome() {

        for (int i = mrunningtask.size() - 1; i >= 0; i--)
        {
            String item = mrunningtask.get(i);
            if(item.equals(mpacakageName))
                return false;
            if(mhomelauncherList.contains(item))
                return true;
        }

        return false;
    }


    private List<String> getLauncherActivitiesNames(){
        List<String> homelauncherList = new ArrayList<>();
        try {
            Intent i = new Intent(Intent.ACTION_MAIN);
            i.addCategory(Intent.CATEGORY_HOME);
            PackageManager pm = mcontext.getPackageManager();
            List<ResolveInfo> lst = pm.queryIntentActivities(i, PackageManager.MATCH_ALL);
            for (ResolveInfo resolveInfo : lst) {
                String item = resolveInfo.activityInfo.packageName;
                Log.d(TAG, "laucher name: "+item);
                if(item!=null)
                    homelauncherList.add(item);
            }
            return homelauncherList;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            Log.d(TAG, "onCreate: ");

            return null;

        }

    }

    public void stopTracking(){
        if(handler!=null && runnable!=null)
            handler.removeCallbacks(runnable);

    }



}
