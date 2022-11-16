package com.echosierra.flashlight;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button light_on_btn;
    Button light_off_btn;
    CameraManager cameraManager;
    String cameraID;
    boolean turnon=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        light_on_btn=(Button) findViewById(R.id.light_ON);
        light_off_btn=(Button) findViewById(R.id.light_OFF);

        boolean FlashAvailableOrNot=getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT);

        if(!FlashAvailableOrNot){
            showNoFlashError();
        }

        cameraManager=(CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try{
            cameraID=cameraManager.getCameraIdList() [0];
        }
        catch (CameraAccessException e){
            e.printStackTrace();
        }
        light_on_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turnon=true;
                switchFlashLight(turnon);
                Toast.makeText(getApplicationContext(),"Flashlight is ON",Toast.LENGTH_SHORT).show();
            }
        });
        light_off_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turnon=false;
                switchFlashLight(turnon);
                Toast.makeText(getApplicationContext(),"Flashlight is OFF",Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void showNoFlashError(){
        AlertDialog alert=new AlertDialog.Builder(this).create();
        alert.setTitle("Uh oh!");
        alert.setMessage("It looks like flash is not available in this device :(");
        alert.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        alert.show();
    }
    public void switchFlashLight(boolean status){
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                cameraManager.setTorchMode(cameraID,status);
            }
        }
        catch (CameraAccessException e){
            e.printStackTrace();
        }
    }
}