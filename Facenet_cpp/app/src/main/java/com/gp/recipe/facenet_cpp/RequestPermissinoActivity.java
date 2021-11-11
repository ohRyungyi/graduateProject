package com.gp.recipe.facenet_cpp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

public class RequestPermissinoActivity extends AppCompatActivity {

    private String TAG = "TEST";

    private int REQUEST_CAMERA = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_permissino);

        if (ActivityCompat.checkSelfPermission( this , Manifest.permission.CAMERA ) == PackageManager.PERMISSION_GRANTED ) {
            Log.d( TAG , "Camera permission is granted" );
        }
        else {
            ActivityCompat.requestPermissions( this , new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA );
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @org.jetbrains.annotations.NotNull String[] permissions, @NonNull @org.jetbrains.annotations.NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if ( requestCode == REQUEST_CAMERA ) {
            if ( grantResults[0] == PackageManager.PERMISSION_GRANTED ) {
                Log.d( TAG , "Camera permission is granted" );
            }
            else {
                AlertDialog dialog = new AlertDialog.Builder().
            }
        }
    }
}