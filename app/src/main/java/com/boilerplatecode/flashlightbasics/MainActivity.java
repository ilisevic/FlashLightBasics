package com.boilerplatecode.flashlightbasics;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.ToggleButton;


public class MainActivity extends AppCompatActivity {
    private ToggleButton toggleButton;
    private CameraManager mCameraManager;
    private  String mCameraId;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boolean isFlashAvailable = getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        if (!isFlashAvailable)
        {
            showNoFlashError();


        }

        mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);


        try {
            mCameraId = mCameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
            Log.e("Nema Flash", "Nema fleš");
        }

        toggleButton = findViewById(R.id.btn_toggle);


        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (1==1) {
                    switchFlashligtOn(isChecked);
                }
            }
        });



    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void switchFlashligtOn(boolean isChecked) {

        try {
            mCameraManager.setTorchMode(mCameraId,isChecked);
        } catch (CameraAccessException e) {
            Log.e("Nema Flash", "Nema fleš");
            e.printStackTrace();
        }
        catch (Exception e)
        {

            Log.e("Nema Flash Exception", "Nema fleš Exception" );
            e.printStackTrace();
        }
    }

    private void showNoFlashError() {

        AlertDialog.Builder alert;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            alert = new AlertDialog.Builder(this).setTitle("Nije podržan flash")
            .setMessage("Nije podržan flash")
            .setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO intent koji se pokreće
                }
            })
            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
        }

    }


}
