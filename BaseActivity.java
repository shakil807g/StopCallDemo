package com.shakil.stop;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by Shakil Karim on 11/19/16.
 */

public class BaseActivity extends AppCompatActivity {


    private static final int REQUEST_CALL_PERMISSION = 110;
    protected static boolean isGrantedcall_permission = false;

    private static final int REQUEST_READ_PHONE_STATE = 111;

    private static final int REQUEST_PROCESS_OUTGOING_CALLS = 112;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        boolean hasPermissionPhoneState =
                (ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED);


        if (!hasPermissionPhoneState) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE},
                    REQUEST_CALL_PERMISSION);
        }



        boolean hasREQUEST_READ_PHONE_STATE =
                (ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED);


        if (!hasREQUEST_READ_PHONE_STATE) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE},
                    REQUEST_READ_PHONE_STATE);
        }




        boolean hasREQUEST_PROCESS_OUTGOING_CALLS =
                (ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.PROCESS_OUTGOING_CALLS) == PackageManager.PERMISSION_GRANTED);


        if (!hasREQUEST_PROCESS_OUTGOING_CALLS) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.PROCESS_OUTGOING_CALLS},
                    REQUEST_PROCESS_OUTGOING_CALLS);
        }


    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case REQUEST_CALL_PERMISSION:
            {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {


                } else
                {

                    Toast.makeText(this, "The app was not allowed to get your phone state. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
                }
            }

            break;

            case REQUEST_READ_PHONE_STATE:
            {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(this, "REQUEST_READ_PHONE_STATE GRANTED", Toast.LENGTH_SHORT).show();

                } else
                {

                    Toast.makeText(this, "REQUEST_READ_PHONE_STATE DENIED", Toast.LENGTH_SHORT).show();
                }
            }
            break;

            case REQUEST_PROCESS_OUTGOING_CALLS:
            {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(this, "REQUEST_REQUEST_PROCESS_OUTGOING_CALLS GRANTED", Toast.LENGTH_SHORT).show();

                } else
                {

                    Toast.makeText(this, "REQUEST_REQUEST_PROCESS_OUTGOING_CALLS DENIED", Toast.LENGTH_SHORT).show();
                }
            }



        }

    }





}
